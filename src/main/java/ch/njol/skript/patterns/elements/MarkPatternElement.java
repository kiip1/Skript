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

import ch.njol.skript.patterns.MatchResult;
import com.google.common.base.MoreObjects;
import org.jetbrains.annotations.Nullable;

public final class MarkPatternElement extends PatternElement {

	private final int mark;

	public MarkPatternElement(int mark) {
		this.mark = mark;
	}

	@Override
	@Nullable
	public MatchResult match(String expr, MatchResult matchResult) {
		matchResult.setMark(matchResult.mark() ^ mark);
		return matchNext(expr, matchResult);
	}

	@Override
	public String pattern() {
		return mark + "¦";
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("mark", mark)
			.add("next", next)
			.add("originalNext", originalNext)
			.toString();
	}
	
}