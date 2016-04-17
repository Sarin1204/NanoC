package antlr.compiler;

import java.io.IOException;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.TokenStream;

import antlr.RecognitionException;

public class TestInJava {

	/**
	 * @param args
	 * @throws org.antlr.runtime.RecognitionException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws org.antlr.runtime.RecognitionException, IOException {
		// TODO Auto-generated method stub
		/*CharStream charStream = new ANTLRStringStream("define func(int a, bool b) returns bool" +
				"{" +
				"int a;" +
				"a = b + 1;" +
				"loop (1 < 2)" +
				"{" +
				"a = 3;" +
				"}" +
				"" +
				"a =  func(1, 2+3);" +
				"return a;" +
				"}");
		
		OurCompilerLexer lexer = new OurCompilerLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		OurCompilerParser parser = new OurCompilerParser(tokenStream);
		parser.rule();
		System.out.println("done");*/
		
		CharStream charStream = new ANTLRFileStream("/home/prabhanjan/Desktop/myprog.pvap");
		
		OurCompiler2Lexer lexer = new OurCompiler2Lexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		OurCompiler2Parser parser = new OurCompiler2Parser(tokenStream);
		parser.rule();
		System.out.println("done");
	}

}
