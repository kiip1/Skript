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
import com.google.common.collect.ImmutableList;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public final class ListPatternElement implements PatternElement {
	
	private final List<PatternElement> elements;
	
	public ListPatternElement(List<PatternElement> elements) {
		this.elements = ImmutableList.copyOf(elements);
	}
	
	@Override
	public boolean check(CheckContext context) {
		int start = context.position;
		Queue<PatternElement> queue = new ArrayDeque<>(elements);
		while (!queue.isEmpty()) {
			PatternElement current = queue.poll();
			context.next = queue.peek();
			if (!current.check(context)) {
				context.position = start;
				return false;
			}
			context.previous = current;
		}
		
		return true;
	}
	
	@Override
	public String pattern() {
		return elements.stream()
			.map(PatternElement::pattern)
			.collect(Collectors.joining());
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("elements", elements)
			.toString();
	}
	
}
