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

import java.util.Locale;

/**
 * A {@link PatternElement} that contains a literal string to be matched, for example {@code hello world}.
 * This element does not handle spaces as would be expected.
 */
public final class LiteralPatternElement extends PatternElement {

	private final String literal;

	public LiteralPatternElement(String literal) {
		this.literal = literal.toLowerCase(Locale.ENGLISH);
	}

	public boolean isEmpty() {
		return literal.length() == 0;
	}

	@Override
	@Nullable
	public MatchResult match(String expr, MatchResult matchResult) {
		char[] exprChars = expr.toCharArray();
		int exprIndex = matchResult.expressionOffset();
		for (char c : literal.toCharArray()) {
			if (c == ' ') { // spaces have special handling to account for extraneous spaces within lines
				// ignore patterns leading or ending with spaces (or if we have multiple leading spaces)
				if (exprIndex == 0 || exprIndex == exprChars.length)
					continue;
				if (exprChars[exprIndex] == ' ') { // pattern is ' fly' and we were given ' fly'
					exprIndex++;
					continue;
				}
				if (exprChars[exprIndex - 1] == ' ') // pattern is ' fly' but we were given something like '  fly' or 'fly'
					continue;
				return null;
			} else if (exprIndex == exprChars.length || Character.toLowerCase(c) != Character.toLowerCase(exprChars[exprIndex]))
				return null;
			exprIndex++;
		}

		matchResult.setExpressionOffset(exprIndex);
		return matchNext(expr, matchResult);
	}

	@Override
	public String pattern() {
		return literal;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("literal", literal)
			.add("next", next)
			.add("originalNext", originalNext)
			.toString();
	}
	
}