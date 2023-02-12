package org.skriptlang.skript.addon;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.skriptlang.skript.Skript;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApiStatus.Internal
public final class SkriptAddonsImpl implements SkriptAddons {
	
	private final Skript skript;
	private final Map<String, SkriptAddon> addons = new ConcurrentHashMap<>();
	
	public SkriptAddonsImpl(Skript skript) {
		this.skript = skript;
	}
	
	@Override
	@Unmodifiable
	public Collection<SkriptAddon> addons() {
		return Collections.unmodifiableCollection(addons.values());
	}
	
	@Override
	@Nullable
	public SkriptAddon addon(String name) {
		return addons.get(name);
	}
	
	@Override
	public void register(SkriptAddon addon) {
		if (!skript.state().registration())
			throw new IllegalStateException("Registration is closed");
		
		if (addons.putIfAbsent(addon.name(), addon) != null)
			throw new IllegalStateException("An addon with the name " + addon.name() + " already exists");
	}
	
	public void done() {
		for (SkriptAddon addon : addons())
			addon.initialize();
	}
	
	public void disable() {
		for (SkriptAddon addon : addons())
			addon.terminate();
	}
	
}
