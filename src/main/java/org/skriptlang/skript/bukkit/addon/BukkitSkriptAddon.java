package org.skriptlang.skript.bukkit.addon;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.skriptlang.skript.Skript;
import org.skriptlang.skript.addon.SkriptAddon;
import org.skriptlang.skript.bukkit.registration.BukkitOrigin;
import org.skriptlang.skript.registration.SyntaxOrigin;

@ApiStatus.Experimental
public abstract class BukkitSkriptAddon extends JavaPlugin implements SkriptAddon {
	
	private final Skript skript;
	
	public BukkitSkriptAddon(Skript skript) {
		this.skript = skript;
	}
	
	@Override
	public final void onLoad() {
		skript.addons().register(this);
	}
	
	@Override
	public final void onEnable() {}
	
	@Override
	public final void onDisable() {}
	
	@Override
	public String name() {
		return getName();
	}
	
	@Override
	public SyntaxOrigin origin() {
		return BukkitOrigin.of(this);
	}
	
}
