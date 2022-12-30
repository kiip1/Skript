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

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

enum TokenType {
	
	NUMBER,
	STRING,
	IDENTIFIER,
	
	GROUP_OPEN('('),
	GROUP_CLOSE(')'),
	
	OPTIONAL_OPEN('['),
	OPTIONAL_CLOSE(']'),
	
	REGEX_OPEN('<'),
	REGEX_CLOSE('>'),
	
	CHOICE('|'),
	MARK(':'),
	OLD_MARK('¦'),
	
	TYPE('%'),
	SLASH('/'),
	NULLABLE('-'),
	PARSE_EXPRESSIONS('*'),
	PARSE_LITERALS('~'),
	TIME('@'),
	
	END;
	
	private static final Map<Character, TokenType> BY_VALUE = new HashMap<>();
	
	static {
		for (TokenType type : values())
			if (type.value != null)
				BY_VALUE.put(type.value, type);
	}
	
	@Nullable
	public final Character value;
	
	TokenType() {
		this.value = null;
	}
	
	TokenType(char value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		if (value == null)
			return name().toLowerCase(Locale.ENGLISH);
		
		return value.toString();
	}
	
	@Nullable
	public static TokenType typeOfCharacter(char character) {
		return BY_VALUE.get(character);
	}
	
}
