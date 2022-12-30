/**
 *   This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright Peter Güttinger, SkriptLang team and contributors
 */
package ch.njol.skript.lang;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptConfig;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.command.Argument;
import ch.njol.skript.command.Commands;
import ch.njol.skript.command.ScriptCommand;
import ch.njol.skript.command.ScriptCommandEvent;
import ch.njol.skript.expressions.ExprParse;
import ch.njol.skript.lang.function.ExprFunctionCall;
import ch.njol.skript.lang.function.FunctionReference;
import ch.njol.skript.lang.function.Functions;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleLiteral;
import ch.njol.skript.localization.Language;
import ch.njol.skript.localization.Message;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.log.LogEntry;
import ch.njol.skript.log.ParseLogHandler;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.patterns.PatternCompiler;
import ch.njol.skript.patterns.SkriptPattern;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.MarkedForRemoval;
import ch.njol.skript.util.Utils;
import ch.njol.util.Kleenean;
import ch.njol.util.NonNullPair;
import ch.njol.util.StringUtils;
import ch.njol.util.coll.CollectionUtils;
import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.primitives.Booleans;
import org.eclipse.jdt.annotation.Nullable;
import org.skriptlang.skript.lang.script.Script;
import org.skriptlang.skript.lang.script.ScriptWarning;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ch.njol.skript.patterns.elements.PatternElement.CheckContext;

/**
 * Used for parsing Skript's custom patterns.
 * <p>
 * Note: All parse methods print one error at most xor any amount of warnings and lower level log messages.
 * If the given string doesn't match any pattern then nothing is printed.
 * <p>
 * Marked as beta since this will probably become an interface later.
 * Addons are free to use this, but must adapt when the time comes.
 */
@Beta
public class SkriptParser {
	
	public static final int PARSE_EXPRESSIONS = 1;
	public static final int PARSE_LITERALS = 2;
	public static final int ALL_FLAGS = PARSE_EXPRESSIONS | PARSE_LITERALS;
	public static final String WILDCARD_PATTERN = "[^\"]*?(?:\"[^\"]*?\"[^\"]*?)*?";
	
	private static final Pattern VARIABLE_PATTERN = Pattern.compile(
			"((the )?var(iable)? )?\\{.+}", Pattern.CASE_INSENSITIVE);
	
	private static final Message m_quotes_error = new Message("skript.quotes error");
	private static final Message m_brackets_error = new Message("skript.brackets error");
	
	@Deprecated
	@MarkedForRemoval
	public final ParseContext context;
	
	private final String input;
	private final int flags;
	
	public SkriptParser(String input) {
		this(input, ALL_FLAGS);
	}
	
	public SkriptParser(String input, int flags) {
		this(input, flags, ParseContext.DEFAULT);
	}
	
	/**
	 * Constructs a new SkriptParser object that can be used to parse the given expression.
	 * <p>
	 * I SkriptParser can be re-used indefinitely for the given expression, but to parse a new expression a new SkriptParser has to be created.
	 * 
	 * @param input The expression to parse
	 * @param flags Some parse flags ({@link #PARSE_EXPRESSIONS}, {@link #PARSE_LITERALS})
	 * @param context The parse context
	 */
	public SkriptParser(String input, int flags, ParseContext context) {
		assert (flags & ALL_FLAGS) != 0;
		this.input = input.trim();
		this.flags = flags;
		this.context = context;
	}
	
	public SkriptParser(SkriptParser other, String input) {
		this(input, other.flags, other.context);
	}
	
	public static final class ParseResult {
		public final Expression<?>[] exprs;
		public final List<MatchResult> regexes = new ArrayList<>(1);
		public String expr;
		// Defaults to 0. Any marks encountered in the pattern will be XORed with the existing value,
		// in particular if only one mark is encountered this value will be set to that mark.
		public int mark = 0;
		public List<String> tags = new ArrayList<>();
		
		public ParseResult(final SkriptParser parser, final String pattern) {
			expr = parser.input;
			exprs = new Expression<?>[countUnescaped(pattern, '%') / 2];
		}

		public ParseResult(String expr, Expression<?>[] expressions) {
			this.expr = expr;
			this.exprs = expressions;
		}

		public boolean hasTag(String tag) {
			return tags.contains(tag);
		}
	}
	
	@Nullable
	public static <T extends SyntaxElement> T parse(String input, Queue<? extends SyntaxElementInfo<? extends T>> source,
													@Nullable String defaultError) {
		
		return parse(input, source, SkriptParser.PARSE_LITERALS, ParseContext.DEFAULT, defaultError);
	}
	
	@Nullable
	public static <T extends SyntaxElement> T parse(String input, Queue<? extends SyntaxElementInfo<? extends T>> source,
														  int flags, ParseContext parseContext, @Nullable String defaultError) {
		
		input = input.trim();
		if (input.isEmpty()) {
			Skript.error(defaultError);
			return null;
		}

		ParseLogHandler log = SkriptLogger.startParseLogHandler();
		try {
			T element = new SkriptParser(input, flags, parseContext).parse(source);
			if (element != null) {
				log.printLog();
				return element;
			}
			log.printError(defaultError);
			return null;
		} finally {
			log.stop();
		}
	}
	
	@Nullable
	private <T extends SyntaxElement> T parse(Queue<? extends SyntaxElementInfo<? extends T>> source) {
		try (ParseLogHandler log = SkriptLogger.startParseLogHandler()) {
			Multimap<SyntaxElementInfo<? extends T>, NonNullPair<SkriptPattern, CheckContext>> possibilities = MultimapBuilder.hashKeys()
				.arrayListValues()
				.build();
			
			for (SyntaxElementInfo<? extends T> info : source) {
				List<NonNullPair<SkriptPattern, CheckContext>> values = Arrays.stream(info.patterns).map(pattern -> {
					SkriptPattern skriptPattern = PatternCompiler.compile(pattern);
					CheckContext checkContext = skriptPattern.check(input);
					
					if (checkContext == null)
						return null;
					
					return new NonNullPair<>(skriptPattern, checkContext);
				}).filter(Objects::nonNull).collect(Collectors.toList());
				
				if (values.size() > 0)
					possibilities.putAll(info, values);
			}
			
			for (SyntaxElementInfo<? extends T> info : possibilities.keys()) {
				int patternIndex = -1;
				for (NonNullPair<SkriptPattern, CheckContext> pair : possibilities.get(info)) {
					patternIndex++;
					
					SkriptPattern pattern = pair.getFirst();
					CheckContext context = pair.getSecond();
					ParseResult result = pattern.visit(input, flags, context, ParseContext.DEFAULT).toParseResult();
					
					log.clear();
					try {
						T instance = info.getElementClass().newInstance();
						if (instance.init(result.exprs, patternIndex, ParserInstance.get().getHasDelayBefore(), result)) {
							log.printLog();
							return instance;
						}
					} catch (InstantiationException | IllegalAccessException e) {
						assert false;
					}
				}
			}
			
			log.printError();
			return null;
		}
	}
	
	/**
	 * Prints errors
	 */
	@Nullable
	private static <T> Variable<T> parseVariable(final String expr, final Class<? extends T>[] returnTypes) {
		if (VARIABLE_PATTERN.matcher(expr).matches()) {
			String variableName = "" + expr.substring(expr.indexOf('{') + 1, expr.lastIndexOf('}'));
			boolean inExpression = false;
			int variableDepth = 0;
			for (char c : variableName.toCharArray()) {
				if (c == '%' && variableDepth == 0)
					inExpression = !inExpression;
				if (inExpression) {
					if (c == '{') {
						variableDepth++;
					} else if (c == '}')
						variableDepth--;
				}

				if (!inExpression && (c == '{' || c == '}'))
					return null;
			}
			return Variable.newInstance(variableName, returnTypes);
		}
		return null;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Nullable
	private <T> Expression<? extends T> parseSingleExpr(boolean allowUnparsedLiteral, @Nullable LogEntry error,
	                                                    Class<? extends T>... types) {
		
		assert types.length > 0;
		assert types.length == 1 || !CollectionUtils.contains(types, Object.class);
		if (input.isEmpty())
			return null;
		if (context != ParseContext.COMMAND && input.startsWith("(") && input.endsWith(")") &&
			next(input, 0, context) == input.length()) return new SkriptParser(this,
				input.substring(1, input.length() - 1)).parseSingleExpr(allowUnparsedLiteral, error, types);
		
		try (ParseLogHandler log = SkriptLogger.startParseLogHandler()) {
			if (context == ParseContext.DEFAULT || context == ParseContext.EVENT) {
				final Variable<? extends T> var = parseVariable(input, types);
				if (var != null) {
					if ((flags & PARSE_EXPRESSIONS) == 0) {
						Skript.error("Variables cannot be used here.");
						log.printError();
						return null;
					}
					log.printLog();
					return var;
				} else if (log.hasError()) {
					log.printError();
					return null;
				}
				final FunctionReference<T> fr = parseFunction(types);
				if (fr != null) {
					log.printLog();
					return new ExprFunctionCall(fr);
				} else if (log.hasError()) {
					log.printError();
					return null;
				}
			}
			
			log.clear();
			
			if ((flags & PARSE_EXPRESSIONS) != 0) {
				final Expression<?> e;
				if (input.startsWith("\"") && input.length() != 1 && nextQuote(input, 1) == input.length() - 1) {
					e = VariableString.newInstance("" + input.substring(1, input.length() - 1));
				} else {
					e = parse(input, new ArrayDeque<>(ImmutableList.copyOf(Skript.getExpressions(types))), null);
				}
				if (e != null) { // Expression/VariableString parsing success
					for (final Class<? extends T> t : types) {
						// Check return type against everything that expression accepts
						if (t.isAssignableFrom(e.getReturnType())) {
							log.printLog();
							return (Expression<? extends T>) e;
						}
					}
					
					// No directly same type found
					Class<T>[] objTypes = (Class<T>[]) types; // Java generics... ?
					final Expression<? extends T> r = e.getConvertedExpression(objTypes);
					if (r != null) {
						log.printLog();
						return r;
					}
					// Print errors, if we couldn't get the correct type
					log.printError(e.toString(null, false) + " " + Language.get("is") + " " + notOfType(types), ErrorQuality.NOT_AN_EXPRESSION);
					return null;
				}
				log.clear();
			}
			
			if ((flags & PARSE_LITERALS) == 0) {
				log.printError();
				return null;
			}
			
			if (types[0] == Object.class) {
				// Do check if a literal with this name actually exists before returning an UnparsedLiteral
				if (!allowUnparsedLiteral || Classes.parseSimple(input, Object.class, context) == null) {
					log.printError();
					return null;
				}
				log.clear();
				final LogEntry e = log.getError();
				return (Literal<? extends T>) new UnparsedLiteral(input, e != null && (error == null || e.quality > error.quality) ? e : error);
			}
			
			for (final Class<? extends T> c : types) {
				log.clear();
				assert c != null;
				final T t = Classes.parse(input, c, context);
				if (t != null) {
					log.printLog();
					return new SimpleLiteral<>(t, false);
				}
			}
			
			log.printError();
			return null;
		}
	}
	
	@Nullable
	private Expression<?> parseSingleExpr(final boolean allowUnparsedLiteral, @Nullable final LogEntry error, final ExprInfo vi) {
		if (input.isEmpty()) // Empty expressions return nothing, obviously
			return null;
		
		// Command special parsing
		if (context != ParseContext.COMMAND && input.startsWith("(") && input.endsWith(")") && next(input, 0, context) == input.length())
			return new SkriptParser(this, "" + input.substring(1, input.length() - 1)).parseSingleExpr(allowUnparsedLiteral, error, vi);
		final ParseLogHandler log = SkriptLogger.startParseLogHandler();
		try {
			// Construct types array which contains all potential classes
			final Class<?>[] types = new Class[vi.classes.length]; // This may contain nulls!
			boolean hasSingular = false;
			boolean hasPlural = false;
			
			// Another array for all potential types, but this time without any nulls
			// (indexes do not align with other data in ExprInfo)
			final Class<?>[] nonNullTypes = new Class[vi.classes.length];
			
			int nonNullIndex = 0;
			for (int i = 0; i < types.length; i++) {
				if ((flags & vi.flagMask) == 0) { // Flag mask invalidates this, skip it
					continue;
				}
				
				// Plural/singular checks
				// TODO move them elsewhere, this method needs to be as fast as possible
				if (vi.isPlural[i])
					hasPlural = true;
				else
					hasSingular = true;
				
				// Actually put class to types[i]
				types[i] = vi.classes[i].getC();
				
				// Handle nonNullTypes data fill
				nonNullTypes[nonNullIndex] = types[i];
				nonNullIndex++;
			}
			
			boolean onlyPlural = false;
			boolean onlySingular = false;
			if (hasSingular && !hasPlural)
				onlySingular = true;
			else if (!hasSingular && hasPlural)
				onlyPlural = true;
			
			if (context == ParseContext.DEFAULT || context == ParseContext.EVENT) {
				// Attempt to parse variable first
				if (onlySingular || onlyPlural) { // No mixed plurals/singulars possible
					final Variable<?> var = parseVariable(input, nonNullTypes);
					if (var != null) { // Parsing succeeded, we have a variable
						// If variables cannot be used here, it is now allowed
						if ((flags & PARSE_EXPRESSIONS) == 0) {
							Skript.error("Variables cannot be used here.");
							log.printError();
							return null;
						}
						
						// Plural/singular sanity check
						if (hasSingular && !var.isSingle()) {
							Skript.error("'" + input + "' can only accept a single value of any type, not more", ErrorQuality.SEMANTIC_ERROR);
							return null;
						}
						
						log.printLog();
						return var;
					} else if (log.hasError()) {
						log.printError();
						return null;
					}
				} else { // Mixed plurals/singulars
					final Variable<?> var = parseVariable(input, types);
					if (var != null) { // Parsing succeeded, we have a variable
						// If variables cannot be used here, it is now allowed
						if ((flags & PARSE_EXPRESSIONS) == 0) {
							Skript.error("Variables cannot be used here.");
							log.printError();
							return null;
						}
						
						// Plural/singular sanity check
						//
						// It's (currently?) not possible to detect this at parse time when there are multiple
						// acceptable types and only some of them are single, since variables, global especially,
						// can hold any possible type, and the type used can only be 100% known at runtime
						//
						// TODO:
						// despite of that, we should probably implement a runtime check for this somewhere
						// before executing the syntax element (perhaps even exceptionally with a console warning,
						// otherwise users may have some hard time debugging the plurality issues) - currently an
						// improper use in a script would result in an exception
						if (((vi.classes.length == 1 && !vi.isPlural[0]) || Booleans.contains(vi.isPlural, true))
								&& !var.isSingle()) {
							Skript.error("'" + input + "' can only accept a single "
									+ Classes.toString(Stream.of(vi.classes).map(ci -> ci.getName().toString()).toArray(), false)
									+ ", not more", ErrorQuality.SEMANTIC_ERROR);
							return null;
						}
						
						log.printLog();
						return var;
					} else if (log.hasError()) {
						log.printError();
						return null;
					}
				}
				
				// If it wasn't variable, do same for function call
				final FunctionReference<?> fr = parseFunction(types);
				if (fr != null) {
					log.printLog();
					return new ExprFunctionCall<>(fr);
				} else if (log.hasError()) {
					log.printError();
					return null;
				}
			}
			log.clear();
			if ((flags & PARSE_EXPRESSIONS) != 0) {
				final Expression<?> e;
				if (input.startsWith("\"") && input.length() != 1 && nextQuote(input, 1) == input.length() - 1) {
					e = VariableString.newInstance("" + input.substring(1, input.length() - 1));
				} else {
					e = parse(input, new ArrayDeque<>(ImmutableList.copyOf(Skript.getExpressions(types))), null);
				}
				if (e != null) { // Expression/VariableString parsing success
					Class<?> returnType = e.getReturnType(); // Sometimes getReturnType does non-trivial costly operations
					for (int i = 0; i < types.length; i++) {
						final Class<?> t = types[i];
						if (t == null) // Ignore invalid (null) types
							continue;
						
						// Check return type against everything that expression accepts
						if (t.isAssignableFrom(returnType)) {
							if (!vi.isPlural[i] && !e.isSingle()) { // Wrong number of arguments
								if (context == ParseContext.COMMAND) {
									Skript.error(Commands.m_too_many_arguments.toString(vi.classes[i].getName().getIndefiniteArticle(), vi.classes[i].getName().toString()), ErrorQuality.SEMANTIC_ERROR);
									return null;
								} else {
									Skript.error("'" + input + "' can only accept a single " + vi.classes[i].getName() + ", not more", ErrorQuality.SEMANTIC_ERROR);
									return null;
								}
							}
							
							log.printLog();
							return e;
						}
					}
					
					if (onlySingular && !e.isSingle()) {
						Skript.error("'" + input + "' can only accept singular expressions, not plural", ErrorQuality.SEMANTIC_ERROR);
						return null;
					}
					
					// No directly same type found
					Expression<?> r = e.getConvertedExpression((Class<Object>[]) types);
					if (r != null) {
						log.printLog();
						return r;
					}

					// Print errors, if we couldn't get the correct type
					log.printError(e.toString(null, false) + " " + Language.get("is") + " " + notOfType(types), ErrorQuality.NOT_AN_EXPRESSION);
					return null;
				}
				log.clear();
			}
			if ((flags & PARSE_LITERALS) == 0) {
				log.printError();
				return null;
			}
			if (vi.classes[0].getC() == Object.class) {
				// Do check if a literal with this name actually exists before returning an UnparsedLiteral
				if (!allowUnparsedLiteral || Classes.parseSimple(input, Object.class, context) == null) {
					log.printError();
					return null;
				}
				log.clear();
				final LogEntry e = log.getError();
				return new UnparsedLiteral(input, e != null && (error == null || e.quality > error.quality) ? e : error);
			}
			for (final ClassInfo<?> ci : vi.classes) {
				log.clear();
				final Object t = Classes.parse(input, ci.getC(), context);
				if (t != null) {
					log.printLog();
					return new SimpleLiteral<>(t, false, new UnparsedLiteral(input));
				}
			}
			log.printError();
			return null;
		} finally {
			log.stop();
		}
	}
	
	/**
	 * Matches ',', 'and', 'or', etc. as well as surrounding whitespace.
	 * <p>
	 * group 1 is null for ',', otherwise it's one of and/or/nor (not necessarily lowercase).
	 */
	@SuppressWarnings("null")
	public static final Pattern listSplitPattern = Pattern.compile("\\s*,?\\s+(and|n?or)\\s+|\\s*,\\s*", Pattern.CASE_INSENSITIVE);
	
	private static final String MULTIPLE_AND_OR = "List has multiple 'and' or 'or', will default to 'and'. Use brackets if you want to define multiple lists.";
	private static final String MISSING_AND_OR = "List is missing 'and' or 'or', defaulting to 'and'";
	
	private boolean suppressMissingAndOrWarnings = SkriptConfig.disableMissingAndOrWarnings.value();
	
	private SkriptParser suppressMissingAndOrWarnings() {
		suppressMissingAndOrWarnings = true;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Nullable
	public final <T> Expression<? extends T> parseExpression(final Class<? extends T>... types) {
		if (input.length() == 0)
			return null;
		
		assert types.length > 0 && (types.length == 1 || !CollectionUtils.contains(types, Object.class));
		
		final boolean isObject = types.length == 1 && types[0] == Object.class;
		final ParseLogHandler log = SkriptLogger.startParseLogHandler();
		try {
			final Expression<? extends T> r = parseSingleExpr(true, null, types);
			if (r != null) {
				log.printLog();
				return r;
			}
			log.clear();
			
			final List<Expression<? extends T>> ts = new ArrayList<>();
			Kleenean and = Kleenean.UNKNOWN;
			boolean isLiteralList = true;
			
			final List<int[]> pieces = new ArrayList<>();
			{
				final Matcher m = listSplitPattern.matcher(input);
				int i = 0, j = 0;
				for (; i >= 0 && i <= input.length(); i = next(input, i, context)) {
					if (i == input.length() || m.region(i, input.length()).lookingAt()) {
						pieces.add(new int[] {j, i});
						if (i == input.length())
							break;
						j = i = m.end();
					}
				}
				if (i != input.length()) {
					assert i == -1 && context != ParseContext.COMMAND : i + "; " + input;
					log.printError("Invalid brackets/variables/text in '" + input + "'", ErrorQuality.NOT_AN_EXPRESSION);
					return null;
				}
			}
			
			if (pieces.size() == 1) { // not a list of expressions, and a single one has failed to parse above
				if (input.startsWith("(") && input.endsWith(")") && next(input, 0, context) == input.length()) {
					log.clear();
					return new SkriptParser(this, "" + input.substring(1, input.length() - 1)).parseExpression(types);
				}
				if (isObject && (flags & PARSE_LITERALS) != 0) { // single expression - can return an UnparsedLiteral now
					log.clear();
					return (Expression<? extends T>) new UnparsedLiteral(input, log.getError());
				}
				// results in useless errors most of the time
//				log.printError("'" + expr + "' " + Language.get("is") + " " + notOfType(types), ErrorQuality.NOT_AN_EXPRESSION);
				log.printError();
				return null;
			}
			
			outer: for (int b = 0; b < pieces.size();) {
				for (int a = pieces.size() - b; a >= 1; a--) {
					if (b == 0 && a == pieces.size()) // i.e. the whole expression - already tried to parse above
						continue;
					final int x = pieces.get(b)[0], y = pieces.get(b + a - 1)[1];
					final String subExpr = "" + input.substring(x, y).trim();
					assert subExpr.length() < input.length() : subExpr;
					
					final Expression<? extends T> t;
					
					if (subExpr.startsWith("(") && subExpr.endsWith(")") && next(subExpr, 0, context) == subExpr.length())
						t = new SkriptParser(this, subExpr).parseExpression(types); // only parse as possible expression list if its surrounded by brackets
					else
						t = new SkriptParser(this, subExpr).parseSingleExpr(a == 1, log.getError(), types); // otherwise parse as a single expression only
					if (t != null) {
						isLiteralList &= t instanceof Literal;
						ts.add(t);
						if (b != 0) {
							final String d = input.substring(pieces.get(b - 1)[1], x).trim();
							if (!d.equals(",")) {
								if (and.isUnknown()) {
									and = Kleenean.get(!d.equalsIgnoreCase("or")); // nor is and
								} else {
									if (and != Kleenean.get(!d.equalsIgnoreCase("or"))) {
										Skript.warning(MULTIPLE_AND_OR + " List: " + input);
										and = Kleenean.TRUE;
									}
								}
							}
						}
						b += a;
						continue outer;
					}
				}
				log.printError();
				return null;
			}

			log.printLog(false);
			
			if (ts.size() == 1)
				return ts.get(0);
			
			if (and.isUnknown() && !suppressMissingAndOrWarnings) {
				ParserInstance parser = ParserInstance.get();
				Script currentScript = parser.isActive() ? parser.getCurrentScript() : null;
				if (currentScript == null || !currentScript.suppressesWarning(ScriptWarning.MISSING_CONJUNCTION))
					Skript.warning(MISSING_AND_OR + ": " + input);
			}
			
			final Class<? extends T>[] exprRetTypes = new Class[ts.size()];
			for (int i = 0; i < ts.size(); i++)
				exprRetTypes[i] = ts.get(i).getReturnType();
			
			if (isLiteralList) {
				final Literal<T>[] ls = ts.toArray(new Literal[ts.size()]);
				assert ls != null;
				return new LiteralList<>(ls, (Class<T>) Utils.getSuperType(exprRetTypes), !and.isFalse());
			} else {
				final Expression<T>[] es = ts.toArray(new Expression[ts.size()]);
				assert es != null;
				return new ExpressionList<>(es, (Class<T>) Utils.getSuperType(exprRetTypes), !and.isFalse());
			}
		} finally {
			log.stop();
		}
	}
	
	@Nullable
	public final Expression<?> parseExpression(final ExprInfo vi) {
		if (input.length() == 0)
			return null;
		
		final boolean isObject = vi.classes.length == 1 && vi.classes[0].getC() == Object.class;
		final ParseLogHandler log = SkriptLogger.startParseLogHandler();
		try {
			// Attempt to parse a single expression
			final Expression<?> r = parseSingleExpr(true, null, vi);
			if (r != null) {
				log.printLog();
				return r;
			}
			log.clear();
			
			final List<Expression<?>> ts = new ArrayList<>();
			Kleenean and = Kleenean.UNKNOWN;
			boolean isLiteralList = true;
			
			final List<int[]> pieces = new ArrayList<>();
			{
				final Matcher m = listSplitPattern.matcher(input);
				int i = 0, j = 0;
				for (; i >= 0 && i <= input.length(); i = next(input, i, context)) {
					if (i == input.length() || m.region(i, input.length()).lookingAt()) {
						pieces.add(new int[] {j, i});
						if (i == input.length())
							break;
						j = i = m.end();
					}
				}
				if (i != input.length()) {
					assert i == -1 && context != ParseContext.COMMAND : i + "; " + input;
					log.printError("Invalid brackets/variables/text in '" + input + "'", ErrorQuality.NOT_AN_EXPRESSION);
					return null;
				}
			}
			
			if (pieces.size() == 1) { // not a list of expressions, and a single one has failed to parse above
				if (input.startsWith("(") && input.endsWith(")") && next(input, 0, context) == input.length()) {
					log.clear();
					return new SkriptParser(this, "" + input.substring(1, input.length() - 1)).parseExpression(vi);
				}
				if (isObject && (flags & PARSE_LITERALS) != 0) { // single expression - can return an UnparsedLiteral now
					log.clear();
					return new UnparsedLiteral(input, log.getError());
				}
				// results in useless errors most of the time
//				log.printError("'" + expr + "' " + Language.get("is") + " " + notOfType(types), ErrorQuality.NOT_AN_EXPRESSION);
				log.printError();
				return null;
			}
			
			outer: for (int b = 0; b < pieces.size();) {
				for (int a = pieces.size() - b; a >= 1; a--) {
					if (b == 0 && a == pieces.size()) // i.e. the whole expression - already tried to parse above
						continue;
					final int x = pieces.get(b)[0], y = pieces.get(b + a - 1)[1];
					final String subExpr = "" + input.substring(x, y).trim();
					assert subExpr.length() < input.length() : subExpr;
					
					final Expression<?> t;
					
					if (subExpr.startsWith("(") && subExpr.endsWith(")") && next(subExpr, 0, context) == subExpr.length())
						t = new SkriptParser(this, subExpr).parseExpression(vi); // only parse as possible expression list if its surrounded by brackets
					else
						t = new SkriptParser(this, subExpr).parseSingleExpr(a == 1, log.getError(), vi); // otherwise parse as a single expression only
					if (t != null) {
						isLiteralList &= t instanceof Literal;
						ts.add(t);
						if (b != 0) {
							final String d = input.substring(pieces.get(b - 1)[1], x).trim();
							if (!d.equals(",")) {
								if (and.isUnknown()) {
									and = Kleenean.get(!d.equalsIgnoreCase("or")); // nor is and
								} else {
									if (and != Kleenean.get(!d.equalsIgnoreCase("or"))) {
										Skript.warning(MULTIPLE_AND_OR + " List: " + input);
										and = Kleenean.TRUE;
									}
								}
							}
						}
						b += a;
						continue outer;
					}
				}
				log.printError();
				return null;
			}
			
			// Check if multiple values are accepted
			// If not, only 'or' lists are allowed
			// (both 'and' and potentially 'and' lists will not be accepted)
			if (vi.isPlural[0] == false && !and.isFalse()) {
				// List cannot be used in place of a single value here
				log.printError();
				return null;
			}

			log.printLog(false);
			
			if (ts.size() == 1) {
				return ts.get(0);
			}
			
			if (and.isUnknown() && !suppressMissingAndOrWarnings) {
				ParserInstance parser = ParserInstance.get();
				Script currentScript = parser.isActive() ? parser.getCurrentScript() : null;
				if (currentScript == null || !currentScript.suppressesWarning(ScriptWarning.MISSING_CONJUNCTION))
					Skript.warning(MISSING_AND_OR + ": " + input);
			}
			
			final Class<?>[] exprRetTypes = new Class[ts.size()];
			for (int i = 0; i < ts.size(); i++)
				exprRetTypes[i] = ts.get(i).getReturnType();
			
			if (isLiteralList) {
				final Literal<?>[] ls = ts.toArray(new Literal[ts.size()]);
				assert ls != null;
				return new LiteralList(ls, Utils.getSuperType(exprRetTypes), !and.isFalse());
			} else {
				final Expression<?>[] es = ts.toArray(new Expression[ts.size()]);
				assert es != null;
				return new ExpressionList(es, Utils.getSuperType(exprRetTypes), !and.isFalse());
			}
		} finally {
			log.stop();
		}
	}
	
	@SuppressWarnings("null")
	private static final Pattern functionCallPattern = Pattern.compile("(" + Functions.functionNamePattern + ")\\((.*)\\)");
	
	/**
	 * @param types The required return type or null if it is not used (e.g. when calling a void function)
	 * @return The parsed function, or null if the given expression is not a function call or is an invalid function call (check for an error to differentiate these two)
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public final <T> FunctionReference<T> parseFunction(final @Nullable Class<? extends T>... types) {
		if (context != ParseContext.DEFAULT && context != ParseContext.EVENT)
			return null;
		final ParseLogHandler log = SkriptLogger.startParseLogHandler();
		try {
			final Matcher m = functionCallPattern.matcher(input);
			if (!m.matches()) {
				log.printLog();
				return null;
			}

			String functionName = "" + m.group(1);
			String args = m.group(2);
			Expression<?>[] params;
			
			// Check for incorrect quotes, e.g. "myFunction() + otherFunction()" being parsed as one function
			// See https://github.com/SkriptLang/Skript/issues/1532
			for (int i = 0; i < args.length(); i = next(args, i, context)) {
				if (i == -1) {
					log.printLog();
					return null;
				}
			}
			
			if ((flags & PARSE_EXPRESSIONS) == 0) {
				Skript.error("Functions cannot be used here (or there is a problem with your arguments).");
				log.printError();
				return null;
			}
			
			if (args.length() != 0) {
				final Expression<?> ps = new SkriptParser(args, flags | PARSE_LITERALS, context).suppressMissingAndOrWarnings().parseExpression(Object.class);
				if (ps == null) {
					log.printError();
					return null;
				}
				if (ps instanceof ExpressionList) {
					if (!ps.getAnd()) {
						Skript.error("Function arguments must be separated by commas and optionally an 'and', but not an 'or'."
								+ " Put the 'or' into a second set of parentheses if you want to make it a single parameter, e.g. 'give(player, (sword or axe))'");
						log.printError();
						return null;
					}
					params = ((ExpressionList<?>) ps).getExpressions();
				} else {
					params = new Expression[] {ps};
				}
			} else {
				params = new Expression[0];
			}

//			final List<Expression<?>> params = new ArrayList<Expression<?>>();
//			if (args.length() != 0) {
//				final int p = 0;
//				int j = 0;
//				for (int i = 0; i != -1 && i <= args.length(); i = next(args, i, context)) {
//					if (i == args.length() || args.charAt(i) == ',') {
//						final Expression<?> e = new SkriptParser("" + args.substring(j, i).trim(), flags | PARSE_LITERALS, context).parseExpression(function.getParameter(p).getType().getC());
//						if (e == null) {
//							log.printError("Can't understand this expression: '" + args.substring(j, i) + "'", ErrorQuality.NOT_AN_EXPRESSION);
//							return null;
//						}
//						params.add(e);
//						j = i + 1;
//					}
//				}
//			}
//			@SuppressWarnings("null")

			ParserInstance parser = ParserInstance.get();
			Script currentScript = parser.isActive() ? parser.getCurrentScript() : null;
			final FunctionReference<T> e = new FunctionReference<>(functionName, SkriptLogger.getNode(),
					currentScript != null ? currentScript.getConfig().getFileName() : null, types, params);//.toArray(new Expression[params.size()]));
			if (!e.validateFunction(true)) {
				log.printError();
				return null;
			}
			log.printLog();
			return e;
		} finally {
			log.stop();
		}
	}
	
	/**
	 * Prints parse errors (i.e. must start a ParseLog before calling this method)
	 */
	public static boolean parseArguments(final String args, final ScriptCommand command, final ScriptCommandEvent event) {
		final SkriptParser parser = new SkriptParser(args, PARSE_LITERALS, ParseContext.COMMAND);
		final ParseResult res = parser.parse_i(command.getPattern());
		if (res == null)
			return false;
		
		final List<Argument<?>> as = command.getArguments();
		assert as.size() == res.exprs.length;
		for (int i = 0; i < res.exprs.length; i++) {
			if (res.exprs[i] == null)
				as.get(i).setToDefault(event);
			else
				as.get(i).set(event, res.exprs[i].getArray(event));
		}
		return true;
	}
	
	/**
	 * Parses the text as the given pattern as {@link ParseContext#COMMAND}.
	 * <p>
	 * Prints parse errors (i.e. must start a ParseLog before calling this method)
	 */
	@Nullable
	public static ParseResult parse(final String text, final String pattern) {
		return new SkriptParser(text, PARSE_LITERALS, ParseContext.COMMAND).parse_i(pattern);
	}
	
	/**
	 * Counts how often the given character occurs in the given string, ignoring any escaped occurrences of the character.
	 * 
	 * @param pattern
	 * @param c The character to search for
	 * @return The number of unescaped occurrences of the given character
	 */
	static int countUnescaped(final String pattern, final char c) {
		return countUnescaped(pattern, c, 0, pattern.length());
	}
	
	static int countUnescaped(final String pattern, final char c, final int start, final int end) {
		assert start >= 0 && start <= end && end <= pattern.length() : start + ", " + end + "; " + pattern.length();
		int r = 0;
		for (int i = start; i < end; i++) {
			final char x = pattern.charAt(i);
			if (x == '\\') {
				i++;
			} else if (x == c) {
				r++;
			}
		}
		return r;
	}
	
	/**
	 * Find the next unescaped (i.e. single) double quote in the string.
	 * 
	 * @param s
	 * @param from Index after the starting quote
	 * @return Index of the end quote
	 */
	private static int nextQuote(final String s, final int from) {
		boolean inExpression = false;
		for (int i = from; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '"' && !inExpression) {
				if (i == s.length() - 1 || s.charAt(i + 1) != '"')
					return i;
				i++;
			} else if (c == '%') {
				inExpression = !inExpression;
			}
		}
		return -1;
	}
	
	/**
	 * @param cs
	 * @return "not an x" or "neither an x, a y nor a z"
	 */
	public static String notOfType(final Class<?>... cs) {
		if (cs.length == 1) {
			final Class<?> c = cs[0];
			assert c != null;
			return Language.get("not") + " " + Classes.getSuperClassInfo(c).getName().withIndefiniteArticle();
		} else {
			final StringBuilder b = new StringBuilder(Language.get("neither") + " ");
			for (int k = 0; k < cs.length; k++) {
				if (k != 0) {
					if (k != cs.length - 1)
						b.append(", ");
					else
						b.append(" " + Language.get("nor") + " ");
				}
				final Class<?> c = cs[k];
				assert c != null;
				b.append(Classes.getSuperClassInfo(c).getName().withIndefiniteArticle());
			}
			return "" + b.toString();
		}
	}
	
	public static String notOfType(final ClassInfo<?>... cs) {
		if (cs.length == 1) {
			return Language.get("not") + " " + cs[0].getName().withIndefiniteArticle();
		} else {
			final StringBuilder b = new StringBuilder(Language.get("neither") + " ");
			for (int k = 0; k < cs.length; k++) {
				if (k != 0) {
					if (k != cs.length - 1)
						b.append(", ");
					else
						b.append(" " + Language.get("nor") + " ");
				}
				b.append(cs[k].getName().withIndefiniteArticle());
			}
			return "" + b.toString();
		}
	}
	
	/**
	 * Returns the next character in the expression, skipping strings, variables and parentheses (unless <tt>context</tt> is {@link ParseContext#COMMAND}).
	 *
	 * @param expr The expression
	 * @param i The last index
	 * @return The next index (can be expr.length()), or -1 if an invalid string, variable or bracket is found or if <tt>i >= expr.length()</tt>.
	 * @throws StringIndexOutOfBoundsException if <tt>i < 0</tt>
	 */
	public static int next(final String expr, final int i, final ParseContext context) {
		if (i >= expr.length())
			return -1;
		if (i < 0)
			throw new StringIndexOutOfBoundsException(i);
		if (context == ParseContext.COMMAND)
			return i + 1;
		final char c = expr.charAt(i);
		if (c == '"') {
			final int i2 = nextQuote(expr, i + 1);
			return i2 < 0 ? -1 : i2 + 1;
		} else if (c == '{') {
			final int i2 = VariableString.nextVariableBracket(expr, i + 1);
			return i2 < 0 ? -1 : i2 + 1;
		} else if (c == '(') {
			for (int j = i + 1; j >= 0 && j < expr.length(); j = next(expr, j, context)) {
				if (expr.charAt(j) == ')')
					return j + 1;
			}
			return -1;
		}
		return i + 1;
	}

	@Nullable
	private ParseResult parse_i(String pattern) {
		SkriptPattern skriptPattern = PatternCompiler.compile(pattern);
		CheckContext checkContext = skriptPattern.check(input);
		if (checkContext == null)
			return null;
		ch.njol.skript.patterns.MatchResult matchResult = skriptPattern
			.visit(input, flags, checkContext, context);
		if (matchResult == null)
			return null;
		return matchResult.toParseResult();
	}

	/**
	 * Validates a user-defined pattern (used in {@link ExprParse}).
	 * 
	 * @return The pattern with %codenames% and a boolean array that contains whether the expressions are plural or not
	 */
	@Nullable
	public static NonNullPair<String, boolean[]> validatePattern(String pattern) {
		try {
//			SkriptPattern skriptPattern = PatternCompiler.compile(pattern);
//			if (visitContext != null)
//				return new NonNullPair<>(skriptPattern.toString(), visitContext.info.isPlural);
			
			Skript.error("Unknown Error");
			return null;
		} catch (Exception e) {
			Skript.error("Invalid pattern: " + e);
			return null;
		}
	}
	
	public static boolean validateLine(String line) {
		if (StringUtils.count(line, '"') % 2 != 0) {
			Skript.error(m_quotes_error.toString());
			return false;
		}
		for (int i = 0; i < line.length(); i = next(line, i, ParseContext.DEFAULT)) {
			if (i == -1) {
				Skript.error(m_brackets_error.toString());
				return false;
			}
		}
		return true;
	}
	
	public static final class ExprInfo {
		
		public final ClassInfo<?>[] classes;
		public final boolean[] isPlural;
		public boolean isOptional;
		public int flagMask = ~0;
		public int time = 0;
		
		public ExprInfo(int length) {
			classes = new ClassInfo[length];
			isPlural = new boolean[length];
		}
		
	}
	
}
