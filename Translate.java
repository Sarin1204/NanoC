package antlr.compiler;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
public class Translate {
public static void main(String[] args) throws Exception {
	// create a CharStream that reads from standard input
	ANTLRInputStream input = new ANTLRInputStream(System.in);
	// create a lexer that feeds off of input CharStream
	OurCompiler2Lexer lexer = new OurCompiler2Lexer(input);
	// create a buffer of tokens pulled from the lexer
	CommonTokenStream tokens = new CommonTokenStream(lexer);
	// create a parser that feeds off the tokens buffer
	OurCompiler2Parser parser = new OurCompiler2Parser(tokens);
	ParseTree tree = parser.rule(); // begin parsing at init rule
	System.out.println(tree.toStringTree(parser)); // print LISP-style tree
	
	//walk the parse tree to convert from high to low level bytecode
	ParseTreeWalker walker = new ParseTreeWalker();
	walker.walk(new HightoLow(), tree);
	
	}
}
