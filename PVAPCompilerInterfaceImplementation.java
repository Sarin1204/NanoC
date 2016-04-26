package antlr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import antlr.pvapCompilerParser.ActualParametersContext;
import antlr.pvapCompilerParser.ArrayTypeContext;
import antlr.pvapCompilerParser.AssignmentstatementContext;
import antlr.pvapCompilerParser.BinaryAdditionContext;
import antlr.pvapCompilerParser.BinaryMultiplicationContext;
import antlr.pvapCompilerParser.CompoundstatementContext;
import antlr.pvapCompilerParser.DatatypesContext;
import antlr.pvapCompilerParser.DeclarationsContext;
import antlr.pvapCompilerParser.DigitContext;
import antlr.pvapCompilerParser.ExpressionContext;
import antlr.pvapCompilerParser.FunctionBodyContext;
import antlr.pvapCompilerParser.FunctionCallContext;
import antlr.pvapCompilerParser.FunctionsContext;
import antlr.pvapCompilerParser.IdentifierContext;
import antlr.pvapCompilerParser.IfStatementBodyContext;
import antlr.pvapCompilerParser.IfStatementContext;
import antlr.pvapCompilerParser.LoopEnterStatementContext;
import antlr.pvapCompilerParser.LoopStatementContext;
import antlr.pvapCompilerParser.LoopbreakstatementContext;
//import antlr.pvapCompilerParser.MainFunctionBodyContext;
//import antlr.pvapCompilerParser.MainFunctionContext;
import antlr.pvapCompilerParser.NegationContext;
import antlr.pvapCompilerParser.ParametersContext;
import antlr.pvapCompilerParser.PrintStatementContext;
import antlr.pvapCompilerParser.ProgramContext;
import antlr.pvapCompilerParser.RelationContext;
import antlr.pvapCompilerParser.ReturnstatementContext;
import antlr.pvapCompilerParser.SequenceofstatementsContext;
import antlr.pvapCompilerParser.SimplestatementContext;
import antlr.pvapCompilerParser.StackStatementContext;
import antlr.pvapCompilerParser.StatementContext;
import antlr.pvapCompilerParser.TermContext;
import antlr.pvapCompilerParser.UnaryContext;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class PVAPCompilerInterfaceImplementation implements pvapCompilerListener{

	//StringBuffer sb = new StringBuffer();
	ArrayList<String> sb = new ArrayList<String>();
	int lineNumber = 0;
	int savePreviousLineNumber = -1;
	boolean rewind = true;
	boolean loopShouldTerminate = false;
	
	int typeOfParam = 0;
	int returnType = 1;
	int argsList = 2;
	HashMap<String, ArrayList<Object>> hmap = new HashMap<String, ArrayList<Object>>();
	
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
					if(sb.get(i).contentEquals("TESTFGOTO ENDSCOPEIF"))
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
			sb.add("TESTFGOTO ENDSCOPEIF");
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
		if(ctx.lhs != null){
			if(hmap.containsKey(ctx.lhs.getText()))
			{
				lineNumber = lineNumber + 1;
				sb.add("STORE" + " " + ctx.lhs.getText());
			}
			else
			{
				System.out.println("Variable " + ctx.lhs.getText() + " does not exist");
			}
		}
		else if (ctx.lhsarray != null)
		{
			
			if(hmap.containsKey(ctx.lhsarray.i.getText()))
			{
				ArrayList<Object> var = hmap.get(ctx.lhsarray.i.getText());
				String type = (String) var.get(0);
				if(type.equalsIgnoreCase("stack"))
				{
					System.out.println("Random access on a variable of type Stack is not allowed");
					System.exit(1);
				}
			}
			else
			{
				System.out.println("Variable does not exist : " + ctx.lhsarray.i.getText());
				System.exit(1);
			}
			
			lineNumber = lineNumber + 1;
			sb.add("STORE" + " " + ctx.lhsarray.i.getText() + "@" + ctx.lhsarray.d.getText());
		}
		else
		{
			//function call
			if(hmap.containsKey(ctx.f.i.getText()) == false)
			{
				System.out.println("Function '" + ctx.f.i.getText() + "' does not exist");
				System.exit(1);
			}
		}
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
		if(ctx.sd != null)
		{
			if ((ctx.sd != null) && (ctx.sd.getText().equalsIgnoreCase("int")))
			{
				lineNumber = lineNumber + 1;
				sb.add("DECLI " + ctx.i.getText());
				
				ArrayList<Object> v = new ArrayList<Object>(1);
				v.add(typeOfParam, "int");
				hmap.put(ctx.i.getText(), v);
			}
			else if((ctx.sd != null) && (ctx.sd.getText().equalsIgnoreCase("bool")))
			{
				lineNumber = lineNumber + 1;
				sb.add("DECLB " + ctx.i.getText());

				ArrayList<Object> v = new ArrayList<Object>(1);
				v.add(typeOfParam, "bool");		
				hmap.put(ctx.i.getText(), v);
				}
		}
		else if (ctx.cdT != null)
		{
			if((ctx.cdT.getText().equalsIgnoreCase("array")) || (ctx.cdT.getText().equalsIgnoreCase("stack")))
			{
				if(ctx.cdtsd.getText().contentEquals("int"))
				{
					lineNumber = lineNumber + 1;
					sb.add("DECLI " + ctx.cdi.getText() + " " + ctx.d.getText());
				}
				else if(ctx.cdtsd.getText().contentEquals("bool"))
				{
					lineNumber = lineNumber + 1;
					sb.add("DECLB " + ctx.cdi.getText() + " " + ctx.d.getText());
				}
				
				if(ctx.cdT.getText().equalsIgnoreCase("array"))
				{
					ArrayList<Object> v = new ArrayList<Object>(1);
					v.add(typeOfParam, "Array");		
					hmap.put(ctx.cdi.getText(), v);
				}
				else if(ctx.cdT.getText().equalsIgnoreCase("stack"))
				{
					ArrayList<Object> v = new ArrayList<Object>(1);
					v.add(typeOfParam, "Stack");
					hmap.put(ctx.cdi.getText(), v);					
				}

			}
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
		if(ctx != null)
		{
			int argCount = 0;
			FunctionCallContext fcc = (FunctionCallContext) ctx.parent;
			//for (int i = 1, j =0; i <= apc.getChildCount();i = i + 2, j += 1)
			ArrayList<Object> funcDetails = hmap.get(fcc.i.getText());
			@SuppressWarnings("unchecked")
			HashMap<String, String> args = (HashMap<String, String>) funcDetails.get(argsList);
			
			for (int j =0; j < ctx.getChildCount();j += 1)
			{
				argCount = argCount + 1;
				if(args.containsKey(ctx.assignmentstatement(j).lhs.getText()))
				{
					lineNumber = lineNumber + 1;
					String argType = args.get(ctx.assignmentstatement(j).lhs.getText());
					if(argType.contentEquals("int"))
					{
						sb.add("DECLI " + ctx.assignmentstatement(j).lhs.getText());
						
						ArrayList<Object> v = new ArrayList<Object>(1);
						v.add(typeOfParam, "int");
						hmap.put(ctx.assignmentstatement(j).lhs.getText(), v);
					}
					else if(argType.contentEquals("bool"))
					{
						sb.add("DECLB " + ctx.assignmentstatement(j).lhs.getText());
						
						ArrayList<Object> v = new ArrayList<Object>(1);
						v.add(typeOfParam, "bool");
						hmap.put(ctx.assignmentstatement(j).lhs.getText(), v);
					}
				}
				else
				{
					System.out.println("Error! no such argument for the function '" + fcc.i.getText() + "' : " + ctx.assignmentstatement(j).lhs.getText());
					System.exit(1);
				}
			}
			
			if (argCount != args.size())
			{
				System.out.println("Invalid number of arguments to the function '" + fcc.i.getText() + "'");
				System.exit(1);
			}
		}
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

		HashMap<String, String> args = new HashMap<String, String>();
		
		if(ctx.d != null)
		{
			if(ctx.p != null)
			{
				for (int j =0; j < ctx.p.getChildCount();)
				{
					if(ctx.p.getChild(j).getText().contentEquals(","))
						j = j + 1;
	
					else
					{
						String dataType = ctx.p.getChild(j).getText();
						String variableName = ctx.p.getChild((j+1)).getText();
						
						if(dataType.contentEquals("int"))
						{
							//sb.add("DECLI " + variableName);
							args.put(variableName, dataType);
						}
						else if(dataType.contentEquals("bool"))
						{
							args.put(variableName, dataType);
						}
						j = j + 2;
					}
				}
			}
			
			ArrayList<Object> v = new ArrayList<Object>(3);
			v.add(typeOfParam, "function");
			v.add(returnType, ctx.d.getText());
			v.add(argsList, args);
			
			hmap.put(ctx.i.getText(), v);
		}
		else
		{
			hmap.put(ctx.i.getText(), null);
		}
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
		
		int i = sb.size()-1;
		
		while (i >= 0)
		{
			if(sb.get(i).contentEquals("TESTTGOTO ENDIF"))
			{
				sb.set(i, "TESTTGOTO " + (sb.size()));
			}
			i = i - 1;
		}
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
			if(hmap.containsKey(ctx.i.getText()))
			{
			lineNumber = lineNumber + 1;
			sb.add("PUSH " + ctx.i.getText());
			}
			else
			{
				System.out.println("Variable " + ctx.i.getText() + " does not exist");
				System.exit(1);
			}
		}
		else if(ctx.arrayName != null)
		{
			lineNumber = lineNumber + 1;
			sb.add("PUSH " + ctx.arrayName.getText() + "@" + ctx.index.getText());
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
		PrintWriter writer = new PrintWriter("/media/prabhanjan/25DDE38A4C3E00E5/ASU Classes Docs/compilers/gitproject/Compiler/intermediate code/myprog7.pvi", "UTF-8");
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
						
						ArrayList<Object> v = new ArrayList<Object>(1);
						v.add(typeOfParam, "int");
						hmap.put(variableName, v);
					}
					else if(dataType.contentEquals("bool"))
					{
						lineNumber = lineNumber + 1;
						sb.add("DECLB " + variableName);
						
						ArrayList<Object> v = new ArrayList<Object>(1);
						v.add(typeOfParam, "bool");
						hmap.put(variableName, v);
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

	@Override
	public void enterArrayType(ArrayTypeContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitArrayType(ArrayTypeContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterStackStatement(StackStatementContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitStackStatement(StackStatementContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("Exiting Stack Statement");
		
		if(hmap.containsKey(ctx.i.getText()))
		{
			ArrayList<Object> var = hmap.get(ctx.i.getText());
			String type = (String) var.get(0);
			if(type.equalsIgnoreCase("stack"))
			{
				lineNumber = lineNumber + 1;
				sb.add("STORE " + ctx.i.getText() + "@LAST");
			}
			else
			{
				System.out.println("Invalid type for " + ctx.i.getText());
				System.exit(1);
			}
		}
		else
		{
			System.out.println("Variable does not exist : " + ctx.i.getText());
			System.exit(1);
		}
	}

	@Override
	public void enterIfStatementBody(IfStatementBodyContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitIfStatementBody(IfStatementBodyContext ctx) {
		// TODO Auto-generated method stub
		lineNumber = lineNumber + 1;
		sb.add("PUSH " + "1");
		lineNumber = lineNumber + 1;
		sb.add("TESTTGOTO ENDIF");
	}

	@Override
	public void enterPrintStatement(PrintStatementContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitPrintStatement(PrintStatementContext ctx) {
		// TODO Auto-generated method stub
		String PrintStatement = new String();
		PrintStatement = "PRINTLN '" + ctx.str.getText().replace(" ", "%").replace("\"", "") + "'";
		
		for (int i = 3; i < ctx.getChildCount();i++)
		{
			if(ctx.getChild(i).getText().contentEquals(","))
				continue;
			PrintStatement = PrintStatement + " " + ctx.getChild(i).getText();
		}
		
		lineNumber = lineNumber + 1;
		sb.add(PrintStatement);
	}
}
