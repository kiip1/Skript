package ch.njol.skript;

import ch.njol.skript.structures.StructAliases;
import ch.njol.skript.structures.StructCommand;
import ch.njol.skript.structures.StructFunction;
import ch.njol.skript.structures.StructOptions;
import ch.njol.skript.structures.StructVariables;

/**
 * This code was automatically generated. DO NOT EDIT!
 */
public enum Structures {
	STRUCT_ALIASES(StructAliases.class),

	STRUCT_COMMAND(StructCommand.class),

	STRUCT_FUNCTION(StructFunction.class),

	STRUCT_OPTIONS(StructOptions.class),

	STRUCT_VARIABLES(StructVariables.class);

	Structures(Class clazz) {
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() {
	}
}
