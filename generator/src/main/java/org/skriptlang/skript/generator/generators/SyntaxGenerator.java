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
package org.skriptlang.skript.generator.generators;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SyntaxGenerator implements Generator {
    private static final String PACKAGE = "ch.njol.skript";
    private static final String NOTICE = "This code was automatically generated. DO NOT EDIT!";
	
    public void generate(String subPackage, String name, Path destination) {
		ClassName syntaxElement = ClassName.get("ch.njol.skript.lang", "SyntaxElement");
        TypeSpec.Builder elements = TypeSpec.enumBuilder(name)
                .addJavadoc(NOTICE)
                .addModifiers(Modifier.PUBLIC);

        for (String element : elements(subPackage)) {
			String[] parts = element.split("\\.");
			String packageName = String.join(".", Arrays.copyOfRange(parts, 0, parts.length - 1));
			String className = parts[parts.length - 1];
            ClassName elementClassName = ClassName.get(packageName, className);
	
	        elements.addEnumConstant(
				elementClassName.simpleName().replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase(),
		        TypeSpec.anonymousClassBuilder("$T.class", elementClassName).build());
        }
	
	    elements.addMethod(MethodSpec.methodBuilder("init")
		    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
		    .build());
		
		elements.addMethod(MethodSpec.constructorBuilder()
				.addParameter(ParameterSpec.builder(ParameterizedTypeName.get(Class.class), "clazz").build())
				.beginControlFlow("try")
				.addStatement("Class.forName(clazz.getName())")
				.nextControlFlow("catch ($T e)", ClassNotFoundException.class)
				.addStatement("throw new $T(e)", RuntimeException.class)
				.endControlFlow()
				.build());
		
	    try {
		    JavaFile.builder(PACKAGE, elements.build())
			    .indent("\t")
			    .skipJavaLangImports(true)
			    .build()
			    .writeTo(destination);
	    } catch (IOException e) {
		    throw new RuntimeException(e);
	    }
    }

    private static List<String> elements(String subPackage) {
	    try (JarFile jar = new JarFile(new File("../build/libs/Skript.jar"))) {
		    List<String> classNames = new ArrayList<>();
		
		    Enumeration<JarEntry> entries = jar.entries();
		    while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				
			    if (entry.getName().startsWith(
					PACKAGE.replaceAll("\\.", "/") + "/" +
					subPackage.replaceAll("\\.", "/")
			    )) {
					if (entry.getName().contains("package-info")) continue;
					else if (entry.getName().contains("$")) continue;
					else if (!entry.getName().endsWith(".class")) continue;
				    classNames.add(entry.getName().replace('/', '.').substring(0, entry.getName().length() - ".class".length()));
			    }
		    }
			
		    classNames.sort(String::compareToIgnoreCase);
		
		    return classNames;
	    } catch (IOException e) {
		    throw new RuntimeException(e);
	    }
    }
}