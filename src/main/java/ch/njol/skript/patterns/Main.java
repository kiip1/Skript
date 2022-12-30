package ch.njol.skript.patterns;

import ch.njol.skript.patterns.elements.PatternElement;
import org.junit.Assert;

final class Main {
	public static void main(String[] args) {
		Lexer lexer = Lexer.of("(send|message) %objects% to %players%");
		Parser parser = Parser.of(lexer);
		PatternElement element = parser.instance().parse();
		SkriptPattern pattern = new SkriptPattern(element);
		
		for (Token token : lexer.instance()) {
			System.out.println(token);
		}
		System.out.println(element.pattern());
		System.out.println(element);
		Assert.assertNull("Obvious fail", pattern.check("a bac d e"));
		Assert.assertNull("Missing part", pattern.check("send"));
		Assert.assertNotNull("Should work", pattern.check("send a to b"));
	}
}
