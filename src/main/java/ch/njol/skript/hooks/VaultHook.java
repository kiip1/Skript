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
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.docs.Documentation;

import java.io.IOException;

/**
 * @author Peter Güttinger
 */
public class VaultHook extends Hook<Vault> {

	public static final String NO_GROUP_SUPPORT = "The permissions plugin you are using does not support groups.";

	public VaultHook() throws IOException {}
	
	@Nullable
	public static Economy economy;
	@Nullable
	public static Chat chat;
	@Nullable
	public static Permission permission;
	
	@SuppressWarnings("null")
	@Override
	protected boolean init() {
		economy = Bukkit.getServicesManager().getRegistration(Economy.class) == null ? null : Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
		chat = Bukkit.getServicesManager().getRegistration(Chat.class) == null ? null : Bukkit.getServicesManager().getRegistration(Chat.class).getProvider();
		permission = Bukkit.getServicesManager().getRegistration(Permission.class) == null ? null : Bukkit.getServicesManager().getRegistration(Permission.class).getProvider();
		return economy != null || chat != null || permission != null;
	}
	
	@Override
	protected void loadClasses() throws IOException {
		if (economy != null || Documentation.canGenerateUnsafeDocs())
			Skript.getAddonInstance().loadClasses(getClass().getPackage().getName() + ".economy");
		if (chat != null || Documentation.canGenerateUnsafeDocs())
			Skript.getAddonInstance().loadClasses(getClass().getPackage().getName() + ".chat");
		if (permission != null || Documentation.canGenerateUnsafeDocs())
			Skript.getAddonInstance().loadClasses(getClass().getPackage().getName() + ".permission");
	}
	
	@Override
	public String getName() {
		return "Vault";
	}
	
}
