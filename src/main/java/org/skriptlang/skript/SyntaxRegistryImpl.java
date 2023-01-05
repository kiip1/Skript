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
package org.skriptlang.skript;

import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

final class SyntaxRegistryImpl<T extends SyntaxInfo<?>> implements SyntaxRegistry<T> {
	
	private final Set<T> registry = new HashSet<>();
	
	@Override
	public @Unmodifiable Set<T> syntaxes() {
		return Collections.unmodifiableSet(registry);
	}
	
	@Override
	@Contract("_ -> this")
	public SyntaxRegistry<T> register(T info) {
		registry.add(info);
		return this;
	}
	
	static final class FinalSyntaxRegistry<T extends SyntaxInfo<?>> implements SyntaxRegistry<T> {
		
		private final Set<T> registry;
		
		FinalSyntaxRegistry(Set<T> registry) {
			this.registry = ImmutableSet.copyOf(registry);
		}
		
		@Override
		public @Unmodifiable Set<T> syntaxes() {
			return registry;
		}
		
		@Override
		@Contract("_ -> fail")
		public SyntaxRegistry<T> register(T info) {
			throw new UnsupportedOperationException("Registration is closed");
		}
		
	}
	
}
