package ch.njol.skript.patterns;

import ch.njol.skript.patterns.elements.PatternElement;
import org.jetbrains.annotations.Contract;

public interface PatternParser {
	
	static PatternParser of(PatternLexer lexer) {
		return new PatternParserImpl(lexer);
	}
	
	@Contract("-> new")
	PatternParser.Instance instance();
	
	String pattern();
	
	interface Instance {
		
		PatternElement parse();
		
		int expressionOffset();
		
	}
	
}
