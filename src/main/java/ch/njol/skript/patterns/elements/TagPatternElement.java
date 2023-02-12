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

import java.util.List;

public final class TagPatternElement extends PatternElement {

	private String tag;

	public TagPatternElement(String tag) {
		this.tag = tag;
	}

	@Override
	void setNext(@Nullable PatternElement next) {
		if (tag.isEmpty()) {
			if (next instanceof LiteralPatternElement) {
				// (:a)
				tag = next.pattern().trim();
			} else {
				// Get the inner element from either a group or optional pattern element
				PatternElement inner = null;
				if (next instanceof GroupPatternElement) {
					inner = ((GroupPatternElement) next).getElement();
				} else if (next instanceof OptionalPatternElement) {
					inner = ((OptionalPatternElement) next).getElement();
				}

				if (inner instanceof ChoicePatternElement) {
					// :(a|b) or :[a|b]
					ChoicePatternElement choicePatternElement = (ChoicePatternElement) inner;
					List<PatternElement> patternElements = choicePatternElement.elements();
					for (int i = 0; i < patternElements.size(); i++) {
						PatternElement patternElement = patternElements.get(i);
						// Prevent a pattern such as :(a|b|) from being turned into (a:a|b:b|:), instead (a:a|b:b|)
						if (patternElement instanceof LiteralPatternElement && !patternElement.pattern().isEmpty()) {
							TagPatternElement newTag = new TagPatternElement(patternElement.pattern().trim());
							newTag.setNext(patternElement);
							newTag.originalNext = patternElement;
							patternElements.set(i, newTag);
						}
					}
				}
			}
		}
		super.setNext(next);
	}

	@Override
	@Nullable
	public MatchResult match(String expr, MatchResult matchResult) {
		matchResult.tags().add(tag);
		return matchNext(expr, matchResult);
	}

	@Override
	public String pattern() {
		if (tag.isEmpty())
			return "";
		return tag + ":";
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("tag", tag)
			.add("next", next)
			.add("originalNext", originalNext)
			.toString();
	}
	
}