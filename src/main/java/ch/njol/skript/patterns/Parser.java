package ch.njol.skript.patterns;

import ch.njol.skript.patterns.elements.PatternElement;
import org.jetbrains.annotations.Contract;

public interface Parser {
	
	static Parser of(Lexer lexer) {
		return new ParserImpl(lexer);
	}
	
	@Contract("-> new")
	Parser.Instance instance();
	
	String pattern();
	
	interface Instance {
		
		PatternElement parse();
		
		int expressionOffset();
		
	}
	
}
