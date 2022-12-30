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

/**
 * A {@link PatternElement} that applies a parse tag when matched.
 */
public final class TagPatternElement implements PatternElement {
	
	private final PatternElement element;
	private final String tag;
	
	public TagPatternElement(PatternElement element, String tag) {
		this.element = element;
		this.tag = tag;
	}
	
	@Override
	public boolean check(CheckContext context) {
		int start = context.position;
		if (element.check(context)) {
			context.pushMatch(this, start);
			return true;
		}
		
		context.position = start;
		return false;
	}
	
	@Override
	public String pattern() {
		if (tag.isEmpty())
			return element.pattern();
		return tag + ":" + element.pattern();
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("element", element)
			.add("tag", tag)
			.toString();
	}
	
}
