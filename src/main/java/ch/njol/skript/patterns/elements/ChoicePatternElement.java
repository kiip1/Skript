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
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public final class ChoicePatternElement extends PatternElement {

	private final List<PatternElement> elements;
	
	public ChoicePatternElement(List<PatternElement> elements) {
		this.elements = ImmutableList.copyOf(elements);
	}
	
	public List<PatternElement> elements() {
		return elements;
	}

	@Override
	void setNext(@Nullable PatternElement next) {
		super.setNext(next);
		for (PatternElement patternElement : elements)
			patternElement.setLastNext(next);
	}

	@Override
	@Nullable
	public MatchResult match(String expr, MatchResult matchResult) {
		for (PatternElement patternElement : elements) {
			MatchResult matchResultCopy = matchResult.copy();
			MatchResult newMatchResult = patternElement.match(expr, matchResultCopy);
			if (newMatchResult != null)
				return newMatchResult;
		}
		return null;
	}

	@Override
	public String pattern() {
		return elements.stream()
			.map(PatternElement::fullPattern)
			.collect(Collectors.joining("|"));
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("patternElements", elements)
			.add("next", next)
			.add("originalNext", originalNext)
			.toString();
	}

}
