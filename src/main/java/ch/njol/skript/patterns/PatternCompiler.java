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

import ch.njol.skript.patterns.elements.PatternElement;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.Duration;

/**
 * The pattern structure is a chain of {@link PatternElement}s
 */
public final class PatternCompiler {
	
	private static final LoadingCache<String, SkriptPattern> cache = CacheBuilder.newBuilder()
		.expireAfterAccess(Duration.ofMinutes(1))
		.maximumSize(10000)
		.build(CacheLoader.from(pattern -> {
			Parser parser = Parser.of(Lexer.of(pattern));
			Parser.Instance instance = parser.instance();
			PatternElement element = instance.parse();
			return new SkriptPattern(element, instance.expressionOffset());
		}));
	
	private PatternCompiler() {}
	
	/**
	 * Parses a pattern String into a {@link SkriptPattern}.
	 */
	public static SkriptPattern compile(String pattern) {
		return cache.getUnchecked(pattern);
	}
	
}
