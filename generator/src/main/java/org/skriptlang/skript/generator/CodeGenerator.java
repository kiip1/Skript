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
package org.skriptlang.skript.generator;

import org.skriptlang.skript.generator.generators.Generator;
import org.skriptlang.skript.generator.generators.SyntaxGenerator;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CodeGenerator {
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Specify a target folder");
			System.exit(-1);
			return;
		}
		
		Path destination = Paths.get(args[0]);
		Files.createDirectories(destination);
		Files.walkFileTree(destination, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}
		});
		
		Generator syntaxGenerator = new SyntaxGenerator();
		syntaxGenerator.generate("conditions", "Conditions", destination);
		syntaxGenerator.generate("effects", "Effects", destination);
		syntaxGenerator.generate("events", "Events", destination);
		syntaxGenerator.generate("expressions", "Expressions", destination);
		syntaxGenerator.generate("entity", "Entities", destination);
		syntaxGenerator.generate("sections", "Sections", destination);
		syntaxGenerator.generate("structures", "Structures", destination);
		System.out.printf("Generated code in %s", destination);
	}
}
