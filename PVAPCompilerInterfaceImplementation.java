package antlr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import antlr.pvapCompilerParser.ActualParametersContext;
import antlr.pvapCompilerParser.AssignmentstatementContext;
import antlr.pvapCompilerParser.BinaryAdditionContext;
import antlr.pvapCompilerParser.BinaryMultiplicationContext;
import antlr.pvapCompilerParser.CompoundDataTypeContext;
import antlr.pvapCompilerParser.CompoundstatementContext;
import antlr.pvapCompilerParser.DatatypesContext;
import antlr.pvapCompilerParser.DeclarationsContext;
import antlr.pvapCompilerParser.DigitContext;
import antlr.pvapCompilerParser.ExpressionContext;
import antlr.pvapCompilerParser.FunctionBodyContext;
import antlr.pvapCompilerParser.FunctionCallContext;
import antlr.pvapCompilerParser.FunctionsContext;
import antlr.pvapCompilerParser.IdentifierContext;
import antlr.pvapCompilerParser.IfStatementContext;
import antlr.pvapCompilerParser.LoopEnterStatementContext;
import antlr.pvapCompilerParser.LoopStatementContext;
import antlr.pvapCompilerParser.LoopbreakstatementContext;
//import antlr.pvapCompilerParser.MainFunctionBodyContext;
//import antlr.pvapCompilerParser.MainFunctionContext;
import antlr.pvapCompilerParser.NegationContext;
import antlr.pvapCompilerParser.ParametersContext;
import antlr.pvapCompilerParser.ProgramContext;
import antlr.pvapCompilerParser.RelationContext;
import antlr.pvapCompilerParser.ReturnstatementContext;
import antlr.pvapCompilerParser.SequenceofstatementsContext;
import antlr.pvapCompilerParser.SimplestatementContext;
import antlr.pvapCompilerParser.StatementContext;
import antlr.pvapCompilerParser.TermContext;
import antlr.pvapCompilerParser.UnaryContext;

import java.io.PrintWriter;
import java.util.ArrayList;

public class PVAPCompilerInterfaceImplementation implements pvapCompilerListener{

	//StringBuffer sb = new StringBuffer();
	ArrayList<String> sb = new ArrayList<String>();
	int lineNumber = 0;
	int savePreviousLineNumber = -1;
	boolean rewind = true;
	boolean loopShouldTerminate = false;
	
	@Override
	public void enterEveryRule(ParserRuleContext arg0) {
		// TODO Auto-generated method stub
		//System.out.println("enterEveryRule");
	}

	@Override
	public void exitEveryRule(ParserRuleContext arg0) {
		// TODO Auto-generated method stub
		//System.out.println("exitEveryRule");
	}

	@Override
	public void visitErrorNode(ErrorNode arg0) {
		// TODO Auto-generated method stub
		//System.out.println("visitErrorNode");
	}

	@Override
	public void visitTerminal(TerminalNode arg0) {
		// TODO Auto-generated method stub
		//System.out.println("visitTerminal" + arg0);
		if(arg0.getText().contentEquals("{"))
		{
			//finalIntermediateCode.append(sb.toString());
			//sb.delete(0, sb.length());
			lineNumber = lineNumber + 1;
			sb.add("OPENSCOPE");
			savePreviousLineNumber = lineNumber;
			//System.out.println("scope opened at  " + lineNumber);
		}
		if(arg0.getText().contentEquals("}"))
		{
			lineNumber = lineNumber + 1;
			sb.add("ENDSCOPE");
			//System.out.println("scope closed at  " + lineNumber);
			//System.out.println("rewinding....1");
			if(rewind == true)
			{
				for(int i = sb.size()-1; i >= 0; i--)
				{
					//if(sb.get(i).contentEquals("TESTFGOTO ENDSCOPE"))
					if(sb.get(i).contentEquals("TESTFGOTO ENDSCOPE"))
					{
						sb.set(i, "TESTFGOTO " + (lineNumber + 1));
						break;
					}
				}
			}
		}
		
		if(arg0.getText().contentEquals("then"))
		{
			lineNumber = lineNumber + 1;
			sb.add("TESTFGOTO ENDSCOPE");
		}
	}

	@Override
	public void enterExpression(ExpressionContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterExpression");
	}

	@Override
	public void exitExpression(ExpressionContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitExpression");
		if((ctx.lhs != null) && (ctx.rhs != null))
		{			
			if (ctx.op.getText().contentEquals("&&"))
			{
				lineNumber = lineNumber + 1;
				sb.add("AND");
			}
			else if (ctx.op.getText().contentEquals("||"))
			{
				lineNumber = lineNumber + 1;
				sb.add("OR");
			}
		}
	}

	@Override
	public void enterCompoundstatement(CompoundstatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterCompoundstatement");
	}

	@Override
	public void exitCompoundstatement(CompoundstatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitCompoundstatement");
	}

	@Override
	public void enterSequenceofstatements(SequenceofstatementsContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterSequenceofstatements");
	}

	@Override
	public void exitSequenceofstatements(SequenceofstatementsContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitSequenceofstatements");
	}

	@Override
	public void enterNegation(NegationContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterNegation");
	}

	@Override
	public void exitNegation(NegationContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitNegation");
	}

	@Override
	public void enterUnary(UnaryContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterUnary");
	}

	@Override
	public void exitUnary(UnaryContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitUnary");
		if((ctx.rhs != null))
		{
			if((ctx.op != null) && (ctx.op.getText().contentEquals("+")))
			{
			}
			else if((ctx.op != null) && (ctx.op.getText().contentEquals("-")))
			{
				lineNumber = lineNumber + 1;
				sb.add("PUSH -1");
				lineNumber = lineNumber + 1;
				sb.add("MUL");
			}
		}
	}

	@Override
	public void enterFunctionCall(FunctionCallContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterFunctionCall");
	}

	@Override
	public void exitFunctionCall(FunctionCallContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitFunctionCall");
		String argNames = new String();
		if(ctx.ap != null)
		{
			ActualParametersContext apc = ctx.ap;
			//for (int i = 1, j =0; i <= apc.getChildCount();i = i + 2, j += 1)
			for (int j =0; j < apc.getChildCount();j += 1)
			{
				argNames += apc.assignmentstatement(j).lhs.getText() + " ";
			}
		}
		lineNumber = lineNumber + 1;
		sb.add("CALL " + ctx.i.getText() + " " + argNames);
	}

	@Override
	public void enterAssignmentstatement(AssignmentstatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterAssignmentstatement" + ctx.getText());
	}

	@Override
	public void exitAssignmentstatement(AssignmentstatementContext ctx) {
		// TODO Auto-generated method stub
		lineNumber = lineNumber + 1;
		sb.add("STORE" + " " + ctx.lhs.getText());
		//System.out.println("exitAssignmentstatement");
	}

	@Override
	public void enterReturnstatement(ReturnstatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterReturnstatement");
	}

	private FunctionBodyContext getFunctionBodyContext( RuleContext context)
	{
		FunctionBodyContext fbc = null;
		try{
			fbc = (FunctionBodyContext) context.parent;
		}
		catch (ClassCastException cce)
		{
			return getFunctionBodyContext(context.parent);
		}
		catch(Exception e)
		{
			System.out.println("couldnt cast at all");
			fbc = null;
		}
		
		return fbc;
		
	}
	
	@Override
	public void exitReturnstatement(ReturnstatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitReturnstatement");
		SimplestatementContext ssc = null;
		StatementContext sc = null;
		SequenceofstatementsContext sosc = null;
		FunctionBodyContext fbc = null;
		FunctionsContext fc = null;
		
		ssc = (SimplestatementContext) ctx.parent;
		if(ssc != null ){
			sc = (StatementContext) ssc.parent;
			if(sc != null)
			{
				sosc = (SequenceofstatementsContext) sc.parent;
				if(sosc != null)
				{
					fbc = getFunctionBodyContext(sosc);

					/*try
					{
						//check if we are returning from a function
						fbc = (FunctionBodyContext) sosc.parent;
					}
					catch(ClassCastException e)
					{
						// the return was inside another block.
						try
						{
							fbc = (FunctionBodyContext) sosc.parent.parent;
						}
						catch(Exception excp)
						{
							fbc = null;
						}
					}*/
					
					if(fbc != null)
					{
						fc = (FunctionsContext) fbc.parent;
						//System.out.println("exitReturnstatement      "  + fc.d.getText());

						if(fc.d != null)
						{
							if(fc.d.getText().contentEquals("int"))
							{
								lineNumber = lineNumber + 1;
								sb.add("DECLI " + "theReturnVariable");
								if((ctx.f !=null) || (ctx.e!=null))
								{
									lineNumber = lineNumber + 1;
									sb.add("STORE " + "theReturnVariable");
									lineNumber = lineNumber + 1;
									sb.add("RET " + "theReturnVariable");
								}
							}
							else if(fc.d.getText().contentEquals("bool"))
							{
								lineNumber = lineNumber + 1;
								sb.add("DECLB " + "theReturnVariable");
								if((ctx.f !=null) || (ctx.e!=null))
								{
									lineNumber = lineNumber + 1;
									sb.add("STORE " + "theReturnVariable");
									lineNumber = lineNumber + 1;
									sb.add("RET " + "theReturnVariable");
								}

							}
						}
					}
				}
			}
		}
	}

	@Override
	public void enterLoopbreakstatement(LoopbreakstatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterLoopbreakstatement");
		lineNumber = lineNumber + 1;
		sb.add("PUSH 0");
		lineNumber = lineNumber + 1;
		sb.add("LOOPTERMINATE");
		loopShouldTerminate = true;
	}

	@Override
	public void exitLoopbreakstatement(LoopbreakstatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitLoopbreakstatement");
	}

	@Override
	public void enterParameters(ParametersContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterParameters");
	}

	@Override
	public void exitParameters(ParametersContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitParameters");
	}

	@Override
	public void enterDatatypes(DatatypesContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterDatatypes");
	}

	@Override
	public void exitDatatypes(DatatypesContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitDatatypes");
	}

	@Override
	public void enterDeclarations(DeclarationsContext ctx) {
		// TODO Auto-generated method stub
		if((ctx.sd != null) && (ctx.sd.getText().equalsIgnoreCase("int")))
		{
			lineNumber = lineNumber + 1;
			sb.add("DECLI " + ctx.i.getText());
		}
		else if((ctx.sd != null) && (ctx.sd.getText().equalsIgnoreCase("bool")))
		{
			lineNumber = lineNumber + 1;
			sb.add("DECLB " + ctx.i.getText());
		}

		
		//System.out.println("enterDeclarations" + ctx.getText());
	}

	@Override
	public void exitDeclarations(DeclarationsContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitDeclarations    ");
		if((ctx.op != null) && (ctx.op.getText().contentEquals("=")))
		{
			lineNumber = lineNumber + 1;
			sb.add("STORE " + ctx.i.getText());
		}
	}

	@Override
	public void enterActualParameters(ActualParametersContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterActualParameters");
	}

	@Override
	public void exitActualParameters(ActualParametersContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitActualParameters");
		
	}

	@Override
	public void enterBinaryMultiplication(BinaryMultiplicationContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterBinaryMultiplication");
	}

	@Override
	public void exitBinaryMultiplication(BinaryMultiplicationContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitBinaryMultiplication");
		if((ctx.lhs != null) && (ctx.rhs != null))
		{
			if(ctx.op.getText().contentEquals("*"))
			{
				lineNumber = lineNumber + 1;
				sb.add("MUL");
			}
			else if(ctx.op.getText().contentEquals("/"))
			{
				lineNumber = lineNumber + 1;
				sb.add("DIV");
			}
			else if(ctx.op.getText().contentEquals("%"))
			{
				lineNumber = lineNumber + 1;
				sb.add("MOD");
			}
		}
	}

	@Override
	public void enterFunctions(FunctionsContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterFunctions");
		lineNumber = lineNumber + 1;
		sb.add("FUNCSTART " + ctx.i.getText());
		/*if(ctx.d != null)
		{
			if(ctx.d.getText().contentEquals("int"))
			{
				lineNumber = lineNumber + 1;
				sb.add("DECLI " + "theReturnVariable");
			}
			else if(ctx.d.getText().contentEquals("bool"))
			{
				lineNumber = lineNumber + 1;
				sb.add("DECLB " + "theReturnVariable");
			}
		}*/
	}

	@Override
	public void exitFunctions(FunctionsContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitFunctions");
		lineNumber = lineNumber + 1;
		sb.add("FUNCEND " + ctx.i.getText());
	}

	@Override
	public void enterLoopStatement(LoopStatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterLoopStatement");
		lineNumber = lineNumber + 1;
		sb.add("LOOPBEGIN");
	}

	@Override
	public void exitLoopStatement(LoopStatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitLoopStatement");
		// now repeating the loop
		lineNumber = lineNumber + 1;
		if(loopShouldTerminate == false)
			sb.add("PUSH 1");
		else
			sb.add("PUSH 0");
		
		for(int i = sb.size()-1; i>= 0;)
		{
			if(sb.get(i).contentEquals("LOOPBEGIN"))
			{
				lineNumber = lineNumber + 1;
				//sb.add("TESTTGOTO " + (i + 1));
				//sb.remove(i);
				sb.set(i, "");
				sb.add("TESTTGOTO " + (i+1));
				break;
			}
			i = i - 1;
		}
		
		for(int i = sb.size()-1; i >= 0; i--)
		{
			//if(sb.get(i).contentEquals("TESTFGOTO ENDSCOPE"))
			if(sb.get(i).contentEquals("TESTFGOTO LOOPEND"))
			{
				sb.set(i, "TESTFGOTO " + (1 + lineNumber));
				break;
			}
		}
		
		for(int i = sb.size()-1; i >= 0; i--)
		{
			//if(sb.get(i).contentEquals("TESTFGOTO ENDSCOPE"))
			if(sb.get(i).contentEquals("LOOPTERMINATE"))
			{
				sb.set(i, "TESTFGOTO " + (1 + lineNumber));
				break;
			}
		}

//		lineNumber = lineNumber + 1;
//		sb.add("LOOPEND");
	}

	@Override
	public void enterRelation(RelationContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterRelation");
	}

	@Override
	public void exitRelation(RelationContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitRelation");
		if((ctx.lhs != null) && (ctx.rhs != null))
		{
			if(ctx.op.getText().contentEquals("=="))
			{
				lineNumber = lineNumber + 1;
				sb.add("EQUAL");
			}
			else if(ctx.op.getText().contentEquals("!="))
			{
				lineNumber = lineNumber + 1;
				sb.add("NEQUAL");
			}
			else if(ctx.op.getText().contentEquals("<"))
			{
				lineNumber = lineNumber + 1;
				sb.add("LESS");
			}
			else if(ctx.op.getText().contentEquals(">"))
			{
				lineNumber = lineNumber + 1;
				sb.add("GTR");
			}
			else if(ctx.op.getText().contentEquals("<="))
			{
				lineNumber = lineNumber + 1;
				sb.add("LEQ");
			}
			else if(ctx.op.getText().contentEquals(">="))
			{
				lineNumber = lineNumber + 1;
				sb.add("GEQ");
			}
		}
	}

	@Override
	public void enterIfStatement(IfStatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterIfStatement");
	}

	@Override
	public void exitIfStatement(IfStatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitIfStatement");
		//sb.add("TESTFGOTO ENDSCOPE");
	}

	@Override
	public void enterStatement(StatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterStatement");
	}

	@Override
	public void exitStatement(StatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitStatement");
	}

	@Override
	public void enterSimplestatement(SimplestatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterSimplestatement");
	}

	@Override
	public void exitSimplestatement(SimplestatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitSimplestatement");
	}

	@Override
	public void enterTerm(TermContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterTerm");
		if(ctx.d != null)
		{
			lineNumber = lineNumber + 1;
			sb.add("PUSH " + ctx.d.getText());
		}
		else if(ctx.i != null)
		{
			lineNumber = lineNumber + 1;
			sb.add("PUSH " + ctx.i.getText());
		}
	}

	@Override
	public void exitTerm(TermContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitTerm");
	}

	@Override
	public void enterProgram(ProgramContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterProgram");
	}

	private void writeIntermediateCodeToFile()
	{
		try{
		PrintWriter writer = new PrintWriter("/media/prabhanjan/25DDE38A4C3E00E5/ASU Classes Docs/compilers/gitproject/Compiler/intermediate code/swap.pvi", "UTF-8");
		for (int i = 0; i< sb.size(); i++)
			writer.println(sb.get(i));

		writer.close();
		}catch(Exception e)
		{
			System.out.println("Cannot write to the file \n\n\n\n" + e.toString());
		}
	}
	
	@Override
	public void exitProgram(ProgramContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitProgram");
//		System.out.println(sb.toString());
		lineNumber = lineNumber + 1;
		sb.add("EOF");
		writeIntermediateCodeToFile();
		//System.out.println("total intermediate codes generated     " + lineNumber);
	}

	@Override
	public void enterBinaryAddition(BinaryAdditionContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterBinaryAddition");
		
	}

	@Override
	public void exitBinaryAddition(BinaryAdditionContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitBinaryAddition");
		if((ctx.lhs != null) && (ctx.rhs != null))
		{
			if(ctx.op.getText().contentEquals("+"))
			{
				lineNumber = lineNumber + 1;
				sb.add("ADD");
			}
			else if(ctx.op.getText().contentEquals("-"))
			{
				lineNumber = lineNumber + 1;
				sb.add("SUB");
			}
		}
	}

	@Override
	public void enterCompoundDataType(CompoundDataTypeContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterCompoundDataType");
	}

	@Override
	public void exitCompoundDataType(CompoundDataTypeContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitCompoundDataType");
	}

	@Override
	public void enterDigit(DigitContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterDigit" + "    " + ctx.getText());
		//sb.add(ctx.getText());
	}

	@Override
	public void exitDigit(DigitContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitDigit"+ "    " + ctx.getText());
//		sb.add("PUSH " + ctx.getText());
//		sb.add("PUSH " );
	}

	@Override
	public void enterIdentifier(IdentifierContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("enterIdentifier" + "    " + ctx.getText());
	}

	@Override
	public void exitIdentifier(IdentifierContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("exitIdentifier" + "    " + ctx.getText());
	}

	@Override
	public void enterLoopEnterStatement(LoopEnterStatementContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitLoopEnterStatement(LoopEnterStatementContext ctx) {
		// TODO Auto-generated method stub
		lineNumber = lineNumber + 1;
		sb.add("TESTFGOTO LOOPEND");
	}

	@Override
	public void enterFunctionBody(FunctionBodyContext ctx) {
		// TODO Auto-generated method stub
		FunctionsContext fc = null;
		
		if(ctx.parent != null)
		{
			fc = (FunctionsContext) ctx.parent;
		}
		
		if(fc.p != null)
		{
			ParametersContext pc = fc.p;
			for (int j =0; j < pc.getChildCount();)
			{
				if(pc.getChild(j).getText().contentEquals(","))
					j = j + 1;

				else
				{
					String dataType = pc.getChild(j).getText();
					String variableName = pc.getChild((j+1)).getText();
					
					if(dataType.contentEquals("int"))
					{
						lineNumber = lineNumber + 1;
						sb.add("DECLI " + variableName);
					}
					else if(dataType.contentEquals("bool"))
					{
						lineNumber = lineNumber + 1;
						sb.add("DECLB " + variableName);
					}
					j = j + 2;
				}
			}
		}

		
	}

	@Override
	public void exitFunctionBody(FunctionBodyContext ctx) {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void enterMainFunction(MainFunctionContext ctx) {
		// TODO Auto-generated method stub
		lineNumber = lineNumber + 1;
		sb.add("FUNCSTART " + ctx.i.getText());
	}

	@Override
	public void exitMainFunction(MainFunctionContext ctx) {
		// TODO Auto-generated method stub
		lineNumber = lineNumber + 1;
		sb.add("FUNCEND " + ctx.i.getText());
	}

	@Override
	public void enterMainFunctionBody(MainFunctionBodyContext ctx) {
		// TODO Auto-generated method stub
		MainFunctionContext fc = null;
		
		if(ctx.parent != null)
		{
			fc = (MainFunctionContext) ctx.parent;
		}
		
		if(fc.p != null)
		{
			ParametersContext pc = fc.p;
			for (int j =0; j < pc.getChildCount();)
			{
				if(pc.getChild(j).getText().contentEquals(","))
					j = j + 1;

				else
				{
					String dataType = pc.getChild(j).getText();
					String variableName = pc.getChild((j+1)).getText();
					
					if(dataType.contentEquals("int"))
					{
						lineNumber = lineNumber + 1;
						sb.add("DECLI " + variableName);
					}
					else if(dataType.contentEquals("bool"))
					{
						lineNumber = lineNumber + 1;
						sb.add("DECLB " + variableName);
					}
					j = j + 2;
				}
			}
		}
	}

	@Override
	public void exitMainFunctionBody(MainFunctionBodyContext ctx) {
		// TODO Auto-generated method stub
		
	}*/
}
