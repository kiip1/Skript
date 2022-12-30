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
package ch.njol.skript.patterns;

import ch.njol.skript.patterns.elements.ChoicePatternElement;
import ch.njol.skript.patterns.elements.EmptyPatternElement;
import ch.njol.skript.patterns.elements.GroupPatternElement;
import ch.njol.skript.patterns.elements.ListPatternElement;
import ch.njol.skript.patterns.elements.LiteralPatternElement;
import ch.njol.skript.patterns.elements.MarkPatternElement;
import ch.njol.skript.patterns.elements.OptionalPatternElement;
import ch.njol.skript.patterns.elements.PatternElement;
import ch.njol.skript.patterns.elements.RegexPatternElement;
import ch.njol.skript.patterns.elements.TagPatternElement;
import ch.njol.skript.patterns.elements.TypePatternElement;
import ch.njol.skript.patterns.elements.TypePatternElement.Modifier;
import com.google.common.collect.ImmutableSet;
import org.eclipse.jdt.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

final class ParserImpl implements Parser {
	
	private final Lexer lexer;
	
	ParserImpl(Lexer lexer) {
		this.lexer = lexer;
	}
	
	@Override
	public Parser.Instance instance() {
		return new ParserImpl.InstanceImpl(this, lexer.instance());
	}
	
	@Override
	public String pattern() {
		return lexer.pattern();
	}
	
	private static final class InstanceImpl implements Parser.Instance {
		
		private final Parser parser;
		private final Lexer.Instance lexer;
		private Token previous = new Token(TokenType.END, "");
		private Token current = new Token(TokenType.END, "");
		
		private InstanceImpl(Parser parser, Lexer.Instance lexer) {
			this.parser = parser;
			this.lexer = lexer;
			next();
		}
		
		@Override
		public PatternElement parse() {
			PatternElement element = list();
			eat(TokenType.END);
			
			return element;
		}
		
		private PatternElement list() {
			return element(TokenType.END);
		}
		
		private PatternElement element(TokenType... closers) {
			assert closers.length > 0;
			return element(ImmutableSet.copyOf(closers));
		}
		
		private PatternElement element(Set<TokenType> closers) {
			assert closers.size() > 0;
			
			List<PatternElement> elements = new ArrayList<>();
			
			while (!closers.contains(current.type())) {
				switch (current.type()) {
					case GROUP_OPEN:
						elements.add(group());
						break;
					case OPTIONAL_OPEN:
						elements.add(optional());
						break;
					case REGEX_OPEN:
						elements.add(regex());
						break;
					case NUMBER:
						elements.add(mark());
						break;
					case TYPE:
						elements.add(type());
						break;
					default:
						if (peek().type() == TokenType.MARK)
							elements.add(tag());
						else
							elements.add(literal());
						break;
				}
			}
			
			elements.removeIf(element -> element instanceof EmptyPatternElement);
			if (elements.size() == 0)
				return new EmptyPatternElement();
			else if (elements.size() == 1)
				return elements.get(0);
			return new ListPatternElement(elements);
		}
		
		private PatternElement type() {
			List<String> types = new ArrayList<>();
			List<Modifier> modifiers = new ArrayList<>();
			int time = 0;
			
			eat(TokenType.TYPE);
			TokenType modifier;
			while ((modifier = ifEat(TokenType.NULLABLE, TokenType.PARSE_EXPRESSIONS, TokenType.PARSE_LITERALS, TokenType.TIME)) != null)
				modifiers.add(Modifier.modifierOfCharacter(modifier.value));
			
			while (current.type() != TokenType.TYPE) {
				types.add(current.value());
				eat(TokenType.IDENTIFIER);
				if (current.type() != TokenType.SLASH)
					break;
				else if (peek().type() == TokenType.TYPE)
					throw new MalformedPatternException(lexer, parser.pattern(), "Trailing slash found");
				
				eat(TokenType.SLASH);
			}
			
			if (ifEat(TokenType.TIME)) {
				time = Integer.parseInt(current.value());
				eat(TokenType.NUMBER);
			};
			
			eat(TokenType.TYPE);
			
			return new TypePatternElement(types, modifiers, time);
		}
		
		private PatternElement literal() {
			String value = current.value();
			eat(TokenType.IDENTIFIER);
			
			return new LiteralPatternElement(value);
		}
		
		private String string() {
			String value = current.value();
			eat(TokenType.STRING);
			
			return value;
		}
		
		private PatternElement group() {
			eat(TokenType.GROUP_OPEN);
			PatternElement element = choice(TokenType.GROUP_CLOSE);
			eat(TokenType.GROUP_CLOSE);
			
			if (element instanceof EmptyPatternElement)
				return new EmptyPatternElement();
			return new GroupPatternElement(element);
		}
		
		private PatternElement choice(TokenType closer) {
			List<PatternElement> choices = new ArrayList<>();
			
			while (current.type() != closer) {
				choices.add(element(TokenType.CHOICE, TokenType.GROUP_CLOSE));
				if (current.type() != TokenType.CHOICE)
					break;
				else if (peek().type() == closer)
					throw new MalformedPatternException(lexer, parser.pattern(), "Trailing pipe found");
				
				eat(TokenType.CHOICE);
			}
			
			if (choices.size() == 0)
				return new EmptyPatternElement();
			else if (choices.size() == 1)
				return choices.get(0);
			return new ChoicePatternElement(choices);
		}
		
		private PatternElement optional() {
			eat(TokenType.OPTIONAL_OPEN);
			PatternElement element = element(TokenType.OPTIONAL_CLOSE);
			eat(TokenType.OPTIONAL_CLOSE);
			
			if (element instanceof EmptyPatternElement)
				return new EmptyPatternElement();
			return new OptionalPatternElement(element);
		}
		
		private PatternElement regex() {
			eat(TokenType.REGEX_OPEN);
			String value = string();
			eat(TokenType.REGEX_CLOSE);
			
			return new RegexPatternElement(Pattern.compile(value));
		}
		
		private PatternElement mark() {
			if (previous.type() == TokenType.MARK || previous.type() == TokenType.OLD_MARK)
				throw new MalformedPatternException(lexer, parser.pattern(), current.type());
			
			int mark = Integer.parseInt(current.value());
			eat(TokenType.NUMBER);
			TokenType type = ifEat(TokenType.MARK, TokenType.OLD_MARK);
			if (type == null)
				throw new MalformedPatternException(lexer, parser.pattern(), TokenType.MARK, current.type());
			PatternElement element = element(TokenType.CHOICE, TokenType.GROUP_CLOSE, TokenType.OPTIONAL_CLOSE);
			
			if (element instanceof EmptyPatternElement)
				return new EmptyPatternElement();
			return new MarkPatternElement(element, mark);
		}
		
		private PatternElement tag() {
			String tag = current.value();
			eat(TokenType.IDENTIFIER);
			eat(TokenType.MARK);
			PatternElement element = element(TokenType.CHOICE, TokenType.GROUP_CLOSE, TokenType.OPTIONAL_CLOSE);
			
			if (element instanceof EmptyPatternElement)
				return new EmptyPatternElement();
			return new TagPatternElement(element, tag);
		}
		
		private void next() {
			previous = current;
			current = lexer.next();
		}
		
		private Token peek() {
			return lexer.peek();
		}
		
		private void eat(TokenType type) {
			if (current.type() != type)
				throw new MalformedPatternException(lexer, parser.pattern(), type, current.type());
			next();
		}
		
		private boolean ifEat(TokenType type) {
			if (current.type() == type) {
				next();
				return true;
			}
			
			return false;
		}
		
		@Nullable
		private TokenType ifEat(TokenType... types) {
			for (TokenType type : types)
				if (ifEat(type))
					return type;
			
			return null;
		}
		
	}
	
}
