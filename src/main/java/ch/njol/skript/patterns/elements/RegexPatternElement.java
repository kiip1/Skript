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

import java.util.regex.Pattern;

/**
 * A {@link PatternElement} that contains a regex {@link Pattern}, for example {@code <.+>}.
 */
public final class RegexPatternElement implements PatternElement {

	private final Pattern pattern;

	public RegexPatternElement(Pattern pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public boolean check(CheckContext context) {
		int start = context.position;
		while (context.next != null && context.position < context.input.length() && !context.next.check(context))
			context.position++;
		
		if (pattern.matcher(context.input.substring(start, context.position)).find()) {
			context.pushMatch(this, start);
			return true;
		}
		
		context.position = start;
		return false;
	}
	
	@Override
	public String pattern() {
		return "<" + pattern + ">";
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("pattern", pattern)
			.toString();
	}

}
