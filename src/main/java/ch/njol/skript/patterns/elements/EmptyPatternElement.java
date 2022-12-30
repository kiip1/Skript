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
 * A {@link PatternElement} that represents nothing; or better said a lack of something that should be there.
 */
public final class EmptyPatternElement implements PatternElement {
	
	@Override
	public boolean check(CheckContext context) {
		throw new UnsupportedOperationException("Empty pattern element after tree completed");
	}
	
	@Override
	public String pattern() {
		return "";
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.toString();
	}
	
}
