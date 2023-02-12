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

import org.jetbrains.annotations.ApiStatus;
import org.skriptlang.skript.addon.SkriptAddons;
import org.skriptlang.skript.registration.SkriptRegistry;

/**
 * The main class for everything related to Skript.
 * This is separated from platform-specific implementations.
 */
@ApiStatus.Experimental
@ApiStatus.NonExtendable
public interface Skript {
	
	/**
	 * This guarantees to always return the same object.
	 *
	 * @return {@link Skript}
	 */
	static Skript instance() {
		return SkriptImpl.instance();
	}
	
	/**
	 * @return {@link SkriptRegistry}
	 */
	SkriptRegistry registry();
	
	/**
	 * @return {@link SkriptAddons}
	 */
	SkriptAddons addons();
	
	/**
	 * @return The current state Skript is in
	 */
	State state();
	
	@ApiStatus.Internal
	void updateState(State state);
	
	enum State {
		
		INIT(false), // Skript starts loading
		REGISTRATION(true), // Addons and syntax can be registered
		DONE(false), // Finished loading
		DISABLE(false); // Disable Skript
		
		private final boolean registration;
		
		State(boolean registration) {
			this.registration = registration;
		}
		
		public boolean registration() {
			return registration;
		}
		
	}
	
}
