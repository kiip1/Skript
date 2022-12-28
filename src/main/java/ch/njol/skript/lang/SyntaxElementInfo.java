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
package ch.njol.skript.lang;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * @author Peter Güttinger
 * @param <E> the syntax element this info is for
 */
public class SyntaxElementInfo<E extends SyntaxElement> {
	
	public final Class<E> c;
	@Nullable
	public final Supplier<E> supplier;
	public final String[] patterns;
	public final String originClassPath;
	
	public SyntaxElementInfo(String[] patterns, Class<E> elementClass,
	                         String originClassPath) throws IllegalArgumentException {
		
		this(patterns, elementClass, null, originClassPath);
	}
	
	public SyntaxElementInfo(String[] patterns, Class<E> elementClass, @Nullable Supplier<E> supplier,
	                         String originClassPath) throws IllegalArgumentException {
		
		this.c = elementClass;
		this.supplier = supplier;
		this.patterns = patterns;
		this.originClassPath = originClassPath;
		
		if (supplier == null) {
			try {
				elementClass.getConstructor();
			} catch (NoSuchMethodException e) {
				// throwing an Exception throws an (empty) ExceptionInInitializerError instead, thus an Error is used
				throw new Error(elementClass + " does not have a public nullary constructor", e);
			} catch (SecurityException e) {
				throw new IllegalStateException("Skript cannot run properly because a security manager is blocking it!");
			}
		}
	}
	
	/**
	 * Get the class that represents this element.
	 * @return The Class of the element
	 */
	public Class<E> getElementClass() {
		return c;
	}
	
	public E instance() {
		if (supplier == null) {
			try {
				return c.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		
		return supplier.get();
	}
	
	/**
	 * Get the patterns of this syntax element.
	 * @return Array of Skript patterns for this element
	 */
	public String[] getPatterns() {
		return Arrays.copyOf(patterns, patterns.length);
	}
	
	/**
	 * Get the original classpath for this element.
	 * @return The original ClassPath for this element
	 */
	public String getOriginClassPath() {
		return originClassPath;
	}
}
