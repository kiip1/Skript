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

import com.google.common.collect.Queues;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

final class LexerImpl implements PatternLexer {
	
	private final String pattern;
	
	LexerImpl(String pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public PatternLexer.Instance instance() {
		return new InstanceImpl(Queues.newArrayDeque(pattern.chars()
			.mapToObj(integer -> (char) integer)
			.collect(Collectors.toList())));
	}
	
	@Override
	public String pattern() {
		return pattern;
	}
	
	private static final class InstanceImpl implements PatternLexer.Instance {
		
		private final Deque<Character> chars;
		private final int length;
		
		@Nullable
		private Token peek;
		@Nullable
		private TokenType previous;
		
		private InstanceImpl(Deque<Character> chars) {
			this.chars = chars;
			length = chars.size();
		}
		
		@Override
		public Token next() {
			peek = null;
			while (!chars.isEmpty()) {
				Character next = chars.peek();
				
				if (Character.isDigit(next)) {
					previous = TokenType.NUMBER;
					return number();
				}
				
				if (previous == TokenType.REGEX_OPEN) {
					previous = TokenType.STRING;
					return string();
				}
				
				if (Character.isAlphabetic(next) || Character.isWhitespace(next)) {
					previous = TokenType.IDENTIFIER;
					return identifier();
				}
				
				chars.poll();
				TokenType tokenType = TokenType.typeOfCharacter(next);
				if (tokenType != null) {
					previous = tokenType;
					return new Token(tokenType, next.toString());
				}
			}
			
			return new Token(TokenType.END, "");
		}
		
		@Override
		public Token peek() {
			if (peek != null)
				return peek;
			
			TokenType previous = this.previous;
			Deque<Character> chars = new ArrayDeque<>(this.chars);
			Token peek = next();
			this.chars.clear();
			this.chars.addAll(chars);
			this.peek = peek;
			this.previous = previous;
			
			return peek;
		}
		
		@Override
		public boolean hasNext() {
			return !chars.isEmpty();
		}
		
		@Override
		public int position() {
			return length - chars.size();
		}
		
		private Token number() {
			StringBuilder result = new StringBuilder();
			while (!chars.isEmpty() && Character.isDigit(chars.peek()))
				result.append(chars.poll());
			
			return new Token(TokenType.NUMBER, result.toString());
		}
		
		private Token identifier() {
			StringBuilder result = new StringBuilder();
			//noinspection DataFlowIssue
			while (!chars.isEmpty() && (Character.isAlphabetic(chars.peek()) || Character.isWhitespace(chars.peek())))
				result.append(chars.poll());
			
			return new Token(TokenType.IDENTIFIER, result.toString());
		}
		
		private Token string() {
			StringBuilder result = new StringBuilder();
			while (!chars.isEmpty() && chars.peek() != TokenType.REGEX_CLOSE.value)
				result.append(chars.poll());
			
			return new Token(TokenType.STRING, result.toString());
		}
		
	}
	
}
