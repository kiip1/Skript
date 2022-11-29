package ch.njol.skript;

import ch.njol.skript.sections.EffSecSpawn;
import ch.njol.skript.sections.SecConditional;
import ch.njol.skript.sections.SecLoop;
import ch.njol.skript.sections.SecWhile;

/**
 * This code was automatically generated. DO NOT EDIT!
 */
public enum Sections {
	EFF_SEC_SPAWN(EffSecSpawn.class),

	SEC_CONDITIONAL(SecConditional.class),

	SEC_LOOP(SecLoop.class),

	SEC_WHILE(SecWhile.class);

	Sections(Class clazz) {
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() {
	}
}
