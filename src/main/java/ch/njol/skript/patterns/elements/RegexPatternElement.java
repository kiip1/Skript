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

import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ParseLogHandler;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.patterns.MatchResult;
import com.google.common.base.MoreObjects;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexPatternElement extends PatternElement {

	private final Pattern pattern;

	public RegexPatternElement(Pattern pattern) {
		this.pattern = pattern;
	}

	@Override
	@Nullable
	public MatchResult match(String expr, MatchResult matchResult) {
		int exprIndex = matchResult.expressionOffset();
		try (ParseLogHandler log = SkriptLogger.startParseLogHandler()) {
			Matcher matcher = pattern.matcher(expr);
			for (int nextExprOffset = SkriptParser.next(expr, exprIndex, matchResult.parseContext());
			     nextExprOffset != -1;
			     nextExprOffset = SkriptParser.next(expr, nextExprOffset, matchResult.parseContext())
			) {
				log.clear();
				matcher.region(exprIndex, nextExprOffset);
				if (matcher.matches()) {
					MatchResult matchResultCopy = matchResult.copy();
					matchResultCopy.setExpressionOffset(nextExprOffset);

					MatchResult newMatchResult = matchNext(expr, matchResultCopy);
					if (newMatchResult != null) {
						// Append to end of list
						newMatchResult.regexResults().add(0, matcher.toMatchResult());
						log.printLog();
						return newMatchResult;
					}
				}
			}
			log.printError(null);
			return null;
		}
	}

	@Override
	public String pattern() {
		return "<" + pattern + ">";
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("pattern", pattern)
			.add("next", next)
			.add("originalNext", originalNext)
			.toString();
	}
	
}