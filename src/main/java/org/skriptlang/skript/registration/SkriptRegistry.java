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
package org.skriptlang.skript.registration;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Section;
import ch.njol.skript.lang.Statement;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

/**
 * The Skript registry manages all registries for syntax registration.
 * By default, the implementation is practically a wrapper around {@code Map<Key<?>, SyntaxRegistry<?>>}.
 */
@ApiStatus.Experimental
@ApiStatus.NonExtendable
public interface SkriptRegistry {
	
	/**
	 * Gets all syntaxes related to a key.
	 */
	@Unmodifiable
	<I extends SyntaxInfo<?>> List<I> syntaxes(Key<I> key);
	
	<I extends SyntaxInfo<?>> void register(Key<I> key, I info);
	
	/**
	 * Represents a syntax element type.
	 */
	interface Key<T extends SyntaxInfo<?>> {
		Key<SyntaxInfo.Expression<?, ?>> EXPRESSION = of("expression");
		Key<SyntaxInfo<? extends Section>> SECTION = of("section");
		
		Key<SyntaxInfo<? extends Statement>> STATEMENT = of("statement");
		Key<SyntaxInfo<? extends Condition>> CONDITION = ChildKey.of(STATEMENT, "condition");
		Key<SyntaxInfo<? extends Effect>> EFFECT = ChildKey.of(STATEMENT, "effect");
		
		Key<SyntaxInfo.Structure<?>> STRUCTURE = of("structure");
		
		String name();
		
		static <T extends SyntaxInfo<?>> Key<T> of(String name) {
			return new KeyImpl<>(name);
		}
		
	}
	
	/**
	 * Like a {@link Key} but has a parent which causes elements to
	 * be registered to both this and the parent.
	 */
	interface ChildKey<T extends P, P extends SyntaxInfo<?>> extends Key<T> {
		
		static <T extends P, P extends SyntaxInfo<?>> SkriptRegistry.Key<T> of(SkriptRegistry.Key<P> parent, String name) {
			return new KeyImpl.Child<>(parent, name);
		}
		
		Key<P> parent();
		
	}
	
}
