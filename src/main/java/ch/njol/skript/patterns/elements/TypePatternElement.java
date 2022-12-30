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

import ch.njol.skript.SkriptAPIException;
import ch.njol.skript.lang.DefaultExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.SkriptParser.ExprInfo;
import ch.njol.skript.patterns.MatchResult;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.Utils;
import ch.njol.util.NonNullPair;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A {@link PatternElement} that contains a type to be matched with an expressions, for example {@code %number%}.
 */
public final class TypePatternElement implements PatternElement {
	
	private final List<String> types;
	private final List<Modifier> modifiers;
	private final int time;
	
	public TypePatternElement(List<String> types, List<Modifier> modifiers, int time) {
		this.types = types;
		this.modifiers = modifiers;
		this.time = time;
	}
	
	@Override
	public boolean check(CheckContext context) {
		int start = context.position;
		if (context.next == null) {
			context.position = context.input.length();
			context.pushMatch(this, start);
			return true;
		}
		
		int length = 0;
		while (!context.next.check(context)) {
			context.position++;
			length++;
			
			if (context.position > context.input.length()) {
				context.position = start;
				return false;
			}
		}
		
		context.position = start + length;
		context.pushMatch(this, start, start + length);
		return true;
	}
	
	@Override
	public boolean visit(MatchResult result) {
		ExprInfo info = new ExprInfo(types.size());
		int i = 0;
		for (String type : types) {
			NonNullPair<String, Boolean> pair = Utils.getEnglishPlural(type);
			info.classes[i] = Classes.getClassInfo(pair.getFirst());
			info.isPlural[i] = pair.getSecond();
			for (Modifier modifier : modifiers)
				modifier.apply(info);
			
			i++;
		}
		
		if (modifiers.contains(Modifier.NULLABLE)) {
			Expression<?> expression = new SkriptParser(result.checkContext().matches.get(this).string,
				result.flags() & info.flagMask, result.parseContext()).parseExpression(info);
			return true;
		}
		
		DefaultExpression<?> expression = info.classes[0].getDefaultExpression();
		String name = info.classes[0].getCodeName();
		String suffix = " [pattern: " + pattern() + "] ";
		if (expression == null)
			throw new SkriptAPIException("The class '" + info.classes[0].getCodeName() + "' does not provide a default expression. Either allow null (with %-" + name + "%) or make it mandatory" + suffix);
		if (!(expression instanceof Literal) && !modifiers.contains(Modifier.PARSE_EXPRESSIONS))
			throw new SkriptAPIException("The default expression of '" + name + "' is not a literal. Either allow null (with %-*" + name + "%) or make it mandatory" + suffix);
		if (expression instanceof Literal && !modifiers.contains(Modifier.PARSE_LITERALS))
			throw new SkriptAPIException("The default expression of '" + name + "' is a literal. Either allow null (with %-~" + name + "%) or make it mandatory" + suffix);
		if (!info.isPlural[0] && !expression.isSingle())
			throw new SkriptAPIException("The default expression of '" + name + "' is not a single-element expression. Change your pattern to allow multiple elements or make the expression mandatory" + suffix);
		if (info.time != 0 && !expression.setTime(info.time))
			throw new SkriptAPIException("The default expression of '" + name + "' does not have distinct time states." + suffix);
		if (!expression.init())
			return false;
		
		result.info = info;
		return true;
	}
	
	@Override
	public String pattern() {
		StringBuilder builder = new StringBuilder().append("%");
		
		for (Modifier modifier : modifiers)
			builder.append(modifier.character());
		
		for (String type : types)
			builder.append(type).append("/");
		builder.deleteCharAt(builder.length() - 1);
		
		if (time != 0)
			builder.append("@").append(time);
		
		return builder.append("%").toString();
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("types", types)
			.add("modifiers", modifiers)
			.add("time", time)
			.toString();
	}
	
	public enum Modifier {
		
		NULLABLE('-', info -> info.isOptional = true),
		PARSE_LITERALS('~', info -> info.flagMask &= ~SkriptParser.PARSE_LITERALS),
		PARSE_EXPRESSIONS('*', info -> info.flagMask &= ~SkriptParser.PARSE_EXPRESSIONS);
		
		private static final Map<Character, Modifier> BY_VALUE = new HashMap<>();
		
		static {
			for (Modifier type : values())
				BY_VALUE.put(type.value, type);
		}
		
		private final char value;
		private final Consumer<ExprInfo> consumer;
		
		Modifier(char value, Consumer<ExprInfo> consumer) {
			this.value = value;
			this.consumer = consumer;
		}
		
		public char character() {
			return value;
		}
		
		public void apply(ExprInfo info) {
			consumer.accept(info);
		}
		
		@Nullable
		public static Modifier modifierOfCharacter(@Nullable Character character) {
			return BY_VALUE.get(character);
		}
		
	}
	
}
