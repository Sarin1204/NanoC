package antlr;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


public class PVAPCompiler {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		pvapCompilerLexer lexer = new pvapCompilerLexer( new ANTLRFileStream("/home/prabhanjan/Desktop/myprog.pvap"));
	    CommonTokenStream tokens = new CommonTokenStream( lexer );
	    pvapCompilerParser parser = new pvapCompilerParser( tokens );
	    ParseTree tree = parser.program();
	    ParseTreeWalker walker = new ParseTreeWalker();
	    walker.walk( new PVAPCompilerInterfaceImplementation(), tree );
	}
}
