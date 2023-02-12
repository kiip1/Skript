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

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public final class MalformedPatternException extends IllegalArgumentException {
	
	public MalformedPatternException(PatternLexer.Instance lexer, String pattern, TokenType received) {
		this(lexer.position(), pattern, "Unexpected " + received);
	}
	
	public MalformedPatternException(PatternLexer.Instance lexer, String pattern, TokenType expected, TokenType received) {
		this(lexer.position(), pattern, "Expected " + expected + " but got " + received);
	}
	
	public MalformedPatternException(PatternLexer.Instance lexer, String pattern, @Nullable String message) {
		this(lexer.position(), pattern, message);
	}
	
	public MalformedPatternException(int position, String pattern, @Nullable String message) {
		this(pattern.substring(0, position - 1) + ">>>" + pattern.charAt(position - 1) + "<<<" + pattern.substring(position) + (message == null ? "" : " [" + message + "]"));
	}
	
	public MalformedPatternException(String message) {
		super(message, null);
	}

}
