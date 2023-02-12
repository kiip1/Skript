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
package ch.njol.skript.patterns.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.SkriptParser.ExprInfo;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.log.ParseLogHandler;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.patterns.MatchResult;
import ch.njol.skript.util.Utils;
import ch.njol.util.Kleenean;
import com.google.common.base.MoreObjects;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A {@link PatternElement} that contains a type to be matched with an expressions, for example {@code %number%}.
 */
public final class TypePatternElement extends PatternElement {

	private final ClassInfo<?>[] classes;
	private final boolean[] isPlural;
	private final List<Modifier> modifiers;
	private final int time;

	private final int expressionIndex;

	public TypePatternElement(ClassInfo<?>[] classes, boolean[] isPlural, List<Modifier> modifiers, int time, int expressionIndex) {
		this.classes = classes;
		this.isPlural = isPlural;
		this.modifiers = modifiers;
		this.time = time;
		this.expressionIndex = expressionIndex;
	}

	@Override
	@Nullable
	public MatchResult match(String expr, MatchResult matchResult) {
		int newExprOffset;

		String nextLiteral = null;
		boolean nextLiteralIsWhitespace = false;

		if (next == null) {
			newExprOffset = expr.length();
		} else if (next instanceof LiteralPatternElement) {
			nextLiteral = next.pattern();

			nextLiteralIsWhitespace = nextLiteral.trim().isEmpty();

			if (!nextLiteralIsWhitespace) { // Don't do this for literal patterns that are *only* whitespace - they have their own special handling
				// trim trailing whitespace - it can cause issues with optional patterns following the literal
				int nextLength = nextLiteral.length();
				for (int i = nextLength; i > 0; i--) {
					if (nextLiteral.charAt(i - 1) != ' ') {
						if (i != nextLength)
							nextLiteral = nextLiteral.substring(0, i);
						break;
					}
				}
			}

			newExprOffset = SkriptParser.nextOccurrence(expr, nextLiteral, matchResult.expressionOffset(), matchResult.parseContext(), false);
			if (newExprOffset == -1 && nextLiteralIsWhitespace) { // We need to tread more carefully here
				// This may be because the next PatternElement is optional or an empty choice (there may be other cases too)
				nextLiteral = null;
				newExprOffset = SkriptParser.next(expr, matchResult.expressionOffset(), matchResult.parseContext());
			}
		} else {
			newExprOffset = SkriptParser.next(expr, matchResult.expressionOffset(), matchResult.parseContext());
		}

		if (newExprOffset == -1)
			return null;

		ExprInfo exprInfo = getExprInfo();

		ParseLogHandler loopLogHandler = SkriptLogger.startParseLogHandler();
		try {
			while (newExprOffset != -1) {
				loopLogHandler.clear();

				MatchResult matchResultCopy = matchResult.copy();
				matchResultCopy.setExpressionOffset(newExprOffset);

				MatchResult newMatchResult = matchNext(expr, matchResultCopy);

				if (newMatchResult != null) {
					ParseLogHandler expressionLogHandler = SkriptLogger.startParseLogHandler();
					try {
						Expression<?> expression = new SkriptParser(expr.substring(matchResult.expressionOffset(), newExprOffset), matchResult.flags() & exprInfo.flagMask, matchResult.parseContext()).parseExpression(exprInfo);
						if (expression != null) {
							if (time != 0) {
								if (expression instanceof Literal)
									return null;

								if (ParserInstance.get().getHasDelayBefore() == Kleenean.TRUE) {
									Skript.error("Cannot use time states after the event has already passed", ErrorQuality.SEMANTIC_ERROR);
									return null;
								}

								if (!expression.setTime(time)) {
									Skript.error(expression + " does not have a " + (time == -1 ? "past" : "future") + " state", ErrorQuality.SEMANTIC_ERROR);
									return null;
								}
							}

							expressionLogHandler.printLog();
							loopLogHandler.printLog();

							newMatchResult.expressions().set(expressionIndex, expression);
							return newMatchResult;
						}
					} finally {
						expressionLogHandler.printError();
					}
				}

				if (nextLiteral != null) {
					int oldNewExprOffset = newExprOffset;
					newExprOffset = SkriptParser.nextOccurrence(expr, nextLiteral, newExprOffset + 1, matchResult.parseContext(), false);
					if (newExprOffset == -1 && nextLiteralIsWhitespace) {
						// This may be because the next PatternElement is optional or an empty choice (there may be other cases too)
						// So, from this point on, we're going to go character by character
						nextLiteral = null;
						newExprOffset = SkriptParser.next(expr, oldNewExprOffset, matchResult.parseContext());
					}
				} else {
					newExprOffset = SkriptParser.next(expr, newExprOffset, matchResult.parseContext());
				}
			}
		} finally {
			if (!loopLogHandler.isStopped())
				loopLogHandler.printError();
		}

		return null;
	}

	@Override
	public String pattern() {
		StringBuilder builder = new StringBuilder().append("%");
		
		for (Modifier modifier : modifiers)
			builder.append(modifier.character());
		
		for (int i = 0; i < classes.length; i++) {
			String codeName = classes[i].getCodeName();
			builder.append(isPlural[i]
					? Utils.toEnglishPlural(codeName)
					: codeName).append("/");
		}
		
		builder.deleteCharAt(builder.length() - 1);
		
		if (time != 0)
			builder.append("@").append(time);
		return builder.append("%").toString();
	}

	private ExprInfo getExprInfo() {
		ExprInfo exprInfo = new ExprInfo(classes.length);
		for (int i = 0; i < classes.length; i++) {
			exprInfo.classes[i] = classes[i];
			exprInfo.isPlural[i] = isPlural[i];
		}
		for (Modifier modifier : modifiers)
			modifier.apply(exprInfo);
		exprInfo.time = time;
		return exprInfo;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("classes", classes)
			.add("isPlural", isPlural)
			.add("modifiers", modifiers)
			.add("time", time)
			.add("expressionIndex", expressionIndex)
			.add("next", next)
			.add("originalNext", originalNext)
			.toString();
	}
	
	public enum Modifier {
		
		NULLABLE('-', info -> info.isOptional = true),
		PARSE_LITERALS('~', info -> info.flagMask &= ~SkriptParser.PARSE_LITERALS),
		PARSE_EXPRESSIONS('*', info -> info.flagMask &= ~SkriptParser.PARSE_EXPRESSIONS);
		
		private static final Map<Character, Modifier> BY_VALUE = new HashMap<>();
		
		static {
			for (Modifier type : values())
				BY_VALUE.put(type.value, type);
		}
		
		private final char value;
		private final Consumer<ExprInfo> consumer;
		
		Modifier(char value, Consumer<ExprInfo> consumer) {
			this.value = value;
			this.consumer = consumer;
		}
		
		public char character() {
			return value;
		}
		
		public void apply(ExprInfo info) {
			consumer.accept(info);
		}
		
		@Nullable
		public static Modifier modifierOfCharacter(@Nullable Character character) {
			return BY_VALUE.get(character);
		}
		
	}
	
}