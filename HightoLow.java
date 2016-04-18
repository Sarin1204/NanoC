package antlr.compiler;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

public class HightoLow extends OurCompiler2BaseListener{
	
	@Override
	public void enterDeclarations(OurCompiler2Parser.DeclarationsContext ctx){
		int count = 0;
		List<ParseTree> children = ctx.children;
		for(ParseTree p : children){
			System.out.println("enterDeclarations "+count + p.getPayload().toString());
			count+=1;
		}
		System.out.println(ctx.getChild(2).getText());
		if (ctx.getChild(2).getText().equals("=")){
			System.out.println("push "+ctx.expression().getText());
		}
		if(ctx.simpleDataType() != null){
			System.out.println("store "+ctx.IDENT());
		}
	}
	
	@Override
	public void enterSimpleDataType(OurCompiler2Parser.SimpleDataTypeContext ctx){
		
		List<ParseTree> children = ctx.children;
		for(ParseTree p : children){
			System.out.println("enterSimpleDataType" + p.getPayload().toString());
		}
	}
		
		@Override
		public void enterTerm(OurCompiler2Parser.TermContext ctx){
			
			List<ParseTree> children = ctx.children;
			for(ParseTree p : children){
				System.out.println("Term" + p.getPayload().toString());
			}
	}
}