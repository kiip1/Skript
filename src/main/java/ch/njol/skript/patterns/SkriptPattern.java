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

import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.patterns.elements.PatternElement;
import ch.njol.skript.patterns.elements.PatternElement.CheckContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public final class SkriptPattern {
	
	private final PatternElement element;
	
	public SkriptPattern(PatternElement element) {
		this.element = element;
	}
	
	/**
	 * @return Null if not a match, the context if a match
	 */
	@Nullable
	public CheckContext check(String input) {
		CheckContext context = new CheckContext(input);
		return element.check(context) ? context : null;
	}
	
	/**
	 * @return Null if not a match, the context if a match
	 */
	@Nullable
	@Contract("_, _, null, _ -> null")
	public MatchResult visit(String input, int flags, @Nullable CheckContext checkContext, ParseContext parseContext) {
		if (checkContext == null)
			return null;
		MatchResult result = new MatchResult(input, 0, flags, checkContext, parseContext);
		return element.visit(result) ? result : null;
	}
	
	@Override
	public String toString() {
		return element.toString();
	}
	
}
