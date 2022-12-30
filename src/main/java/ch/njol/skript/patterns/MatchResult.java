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

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.patterns.elements.PatternElement.CheckContext;
import com.google.common.base.MoreObjects;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * A result from pattern matching.
 */
@ApiStatus.Internal
public final class MatchResult {

	private final String input;
	private final CheckContext checkContext;
	private final ParseContext parseContext;
	private final List<Expression<?>> expressions = new ArrayList<>();
	private final List<String> tags = new ArrayList<>();
	private final List<java.util.regex.MatchResult> regexResults = new ArrayList<>();
	
	private int mark;
	private int flags;
	
	public MatchResult(String input, int mark, int flags, CheckContext checkContext, ParseContext parseContext) {
		this.input = input;
		this.mark = mark;
		this.flags = flags;
		this.checkContext = checkContext;
		this.parseContext = parseContext;
	}

	public MatchResult copy() {
		MatchResult result = new MatchResult(input, mark, flags, checkContext, parseContext);
		result.expressions().addAll(expressions);
		result.tags().addAll(tags);
		result.regexResults().addAll(regexResults);
		return result;
	}

	public ParseResult toParseResult() {
		ParseResult parseResult = new ParseResult(input, expressions.toArray(new Expression<?>[0]));
		parseResult.regexes.addAll(regexResults);
		parseResult.mark = mark;
		parseResult.tags.addAll(tags);
		return parseResult;
	}
	
	public List<Expression<?>> expressions() {
		return expressions;
	}
	
	public String input() {
		return input;
	}
	
	public int mark() {
		return mark;
	}
	
	public void setMark(int mark) {
		this.mark = mark;
	}
	
	public List<String> tags() {
		return tags;
	}
	
	public List<java.util.regex.MatchResult> regexResults() {
		return regexResults;
	}
	
	public CheckContext checkContext() {
		return checkContext;
	}
	
	public ParseContext parseContext() {
		return parseContext;
	}
	
	public int flags() {
		return flags;
	}
	
	public void setFlags(int flags) {
		this.flags = flags;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("expressions", expressions)
			.add("tags", tags)
			.add("regexResults", regexResults)
			.add("input", input)
			.add("mark", mark)
			.add("flags", flags)
			.add("checkContext", checkContext)
			.add("parseContext", parseContext)
			.toString();
	}
	
}
