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
package ch.njol.skript.hooks;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Documentation;
import ch.njol.skript.localization.ArgsMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.UnknownNullability;

import java.io.IOException;

@ApiStatus.Internal
public abstract class SimpleHook<T extends Plugin> implements Hook {

	private static final ArgsMessage HOOKED_MESSAGE = new ArgsMessage("hooks.hooked");
	private static final ArgsMessage HOOK_ERROR_MESSAGE = new ArgsMessage("hooks.error");

	@UnknownNullability
	private final T plugin;
	private final String name;

	@SuppressWarnings("unchecked")
	public SimpleHook(String name) {
		this.name = name;
		plugin = (T) Bukkit.getPluginManager().getPlugin(getName());
		if (plugin == null) {
			if (Documentation.canGenerateUnsafeDocs()) {
				loadClasses();
				if (Skript.logHigh())
					Skript.info(HOOKED_MESSAGE.toString(getName()));
			}
			return;
		}

		if (!init()) {
			Skript.error(HOOK_ERROR_MESSAGE.toString(getName()));
			return;
		}

		loadClasses();

		if (Skript.logHigh())
			Skript.info(HOOKED_MESSAGE.toString(getName()));
	}

	@Override
	public String getName() {
		return name;
	}

	protected T plugin() {
		return plugin;
	}

	protected void loadClasses() {
		try {
			Skript.getAddonInstance().loadClasses(getClass().getPackage().getName());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
