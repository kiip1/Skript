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

import com.google.common.base.MoreObjects;

import java.util.Locale;

/**
 * A {@link PatternElement} that contains a literal string to be matched, for example {@code hello world}.
 * This element does not handle spaces as would be expected.
 */
public final class LiteralPatternElement implements PatternElement {
	
	private final String literal;
	
	public LiteralPatternElement(String literal) {
		this.literal = literal.toLowerCase(Locale.ENGLISH);
	}
	
	@Override
	public boolean check(CheckContext context) {
		int start = context.position;
		int end = start + literal.length();
		if (end > context.input.length())
			return false;
		
		if (context.input.substring(start, end).equals(literal)) {
			context.position = end;
			return true;
		}
		
		return false;
	}
	
	@Override
	public String pattern() {
		return literal;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("literal", literal)
			.toString();
	}
	
}
