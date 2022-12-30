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
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@ApiStatus.NonExtendable
public interface PatternElement {
	
	default boolean check(CheckContext context) {
		return true;
	}
	
	default boolean visit(MatchResult result) {
		return true;
	}
	
	String pattern();
	
	final class CheckContext {
		
		// Only mutate if you are returning true
		public final Map<PatternElement, PositionedString> matches = new HashMap<>();
		
		final String input;
		
		int position;
		@Nullable
		PatternElement previous = null;
		@Nullable
		PatternElement next = null;
		
		public CheckContext(String input) {
			this.input = input;
		}
		
		String from(int start) {
			if (position > input.length())
				return "";
			
			return input.substring(start, position);
		}
		
		void pushMatch(PatternElement element, int start) {
			matches.put(element, new PositionedString(start, from(start)));
		}
		
		void pushMatch(PatternElement element, int start, int end) {
			matches.put(element, new PositionedString(start, input.substring(start, end)));
		}
		
	}
	
	final class PositionedString {
		
		public final int position;
		public final String string;
		
		public PositionedString(int position, String string) {
			this.position = position;
			this.string = string;
		}
		
	}
	
}
