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

import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.addon.SkriptAddons;
import org.skriptlang.skript.addon.SkriptAddonsImpl;
import org.skriptlang.skript.registration.SkriptRegistry;
import org.skriptlang.skript.registration.SkriptRegistryImpl;

final class SkriptImpl implements Skript {
	
	@Nullable
	private static volatile Skript instance;
	
	static Skript instance() {
		Skript instance = SkriptImpl.instance;
		if (instance != null)
			return instance;
		instance = new SkriptImpl();
		SkriptImpl.instance = instance;
		return instance;
	}
	
	private final SkriptRegistryImpl registry = new SkriptRegistryImpl(this);
	private final SkriptAddonsImpl addons = new SkriptAddonsImpl(this);
	
	private volatile State state = State.INIT;
	
	@Override
	public SkriptRegistry registry() {
		return registry;
	}
	
	@Override
	public SkriptAddons addons() {
		return addons;
	}
	
	@Override
	public State state() {
		return state;
	}
	
	@Override
	public void updateState(State state) {
		if (this.state.ordinal() >= state.ordinal())
			throw new IllegalStateException("State may only go forward");
		
		this.state = state;
		
		switch (state) {
			case DONE:
				registry.done();
				addons.done();
				break;
			case DISABLE:
				registry.disable();
				addons.disable();
				break;
		}
	}
	
}
