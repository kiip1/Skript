package org.skriptlang.skript.addon;

import org.jetbrains.annotations.ApiStatus;
import org.skriptlang.skript.registration.SyntaxOrigin;

@ApiStatus.Experimental
public interface SkriptAddon {
	
	void initialize();
	
	void terminate();
	
	String name();
	
	default SyntaxOrigin origin() {
		return AddonOrigin.of(this);
	}
	
}
