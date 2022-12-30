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

import com.google.common.base.MoreObjects;

final class Token {
	private final TokenType type;
	private final String value;
	
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public TokenType type() {
		return type;
	}
	
	public String value() {
		return value;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("type", type)
			.add("value", value)
			.toString();
	}
	
}
