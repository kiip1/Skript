package org.skriptlang.skript.addon;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;

@ApiStatus.Experimental
@ApiStatus.NonExtendable
public interface SkriptAddons {
	
	@Unmodifiable
	Collection<SkriptAddon> addons();
	
	@Nullable
	SkriptAddon addon(String name);
	
	void register(SkriptAddon addon);
	
}
