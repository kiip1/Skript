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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public interface PatternLexer {
	
	static PatternLexer of(String pattern) {
		return new LexerImpl(pattern);
	}
	
	@Contract("-> new")
	PatternLexer.Instance instance();
	
	String pattern();
	
	interface Instance extends Iterable<Token> {
		
		Token next();
		
		Token peek();
		
		boolean hasNext();
		
		int position();
		
		@NotNull
		@Override
		default Iterator<Token> iterator() {
			return new Iterator<Token>() {
				@Override
				public boolean hasNext() {
					return Instance.this.hasNext();
				}
				
				@Override
				public Token next() {
					return Instance.this.next();
				}
			};
		}
		
	}
	
}
