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

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
@ApiStatus.Internal
public final class SkriptRegistryImpl implements SkriptRegistry {
	
	private final Map<Key<?>, SyntaxRegister<?>> registers = new ConcurrentHashMap<>();
	
	@Override
	@Unmodifiable
	public <I extends SyntaxInfo<?>> List<I> syntaxes(Key<I> key) {
		return register(key).syntaxes();
	}
	
	@Override
	public <I extends SyntaxInfo<?>> void register(Key<I> key, I info) {
		register(key).add(info);
		if (key instanceof ChildKey)
			register(((ChildKey<? extends I, I>) key).parent(), info);
	}
	
	public void closeRegistration() {
		registers.replaceAll(((key, register) -> register.closeRegistration()));
	}
	
	private <I extends SyntaxInfo<?>> SyntaxRegister<I> register(Key<I> key) {
		return (SyntaxRegister<I>) registers.computeIfAbsent(key, k -> new SyntaxRegisterImpl<>());
	}
	
}
