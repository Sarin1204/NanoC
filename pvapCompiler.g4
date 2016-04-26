grammar pvapCompiler;
options {
language = Java;
//output = AST;
//ASTLabelType=CommonTree;
}

@header {
  package antlr;
}

tokens
{
	PARAMS
}
program:
(declarations | assignmentstatement | stackStatement | printStatement | functions)*
;

declarations:
sd=SIMPLEDATATYPE i=IDENT (op='=' expression )? ENDOFSTATEMENT
|
cdT=COMPOUNDDATATYPE cdi=IDENT '=' cdtsd=SIMPLEDATATYPE'[' d=DIGIT ']' ENDOFSTATEMENT
;

functions:
'define' i=identifier '(' (p=parameters)?')' ('returns' d=datatypes)?
'{'
functionBody
'}'
;

functionBody:
declarations*
sequenceofstatements
;

parameters: dtl=datatypes dtli=IDENT (',' dtr=datatypes dtri=IDENT)*;
sequenceofstatements: (statement)*;
statement: (simplestatement | compoundstatement);
simplestatement : returnstatement | assignmentstatement | stackStatement | printStatement;

returnstatement: 'return' (e=expression | f=functionCall)? ENDOFSTATEMENT;
loopbreakstatement: 'terminate' ENDOFSTATEMENT;
assignmentstatement:
	(lhs=identifier | lhsarray=arrayType) op='=' rhs=expression ENDOFSTATEMENT
	| (lhs=identifier '=')? f=functionCall ENDOFSTATEMENT
;
stackStatement:
	sop=STACKOPERATIONS i=IDENT ',' expression ENDOFSTATEMENT
;

arrayType:
	i=IDENT '[' d=DIGIT ']'
;

printStatement:
	'print' str=STRING_LITERAL (',' i=IDENT)*
;

STRING_LITERAL:
	'"' (~'\'' | '\\' (.))* '"'
;

functionCall:
	i=identifier '(' ap=actualParameters? ')'
;

actualParameters:
	assignmentstatement (assignmentstatement)*
;

term: i=IDENT 
	| '(' expression ')'
	| d=DIGIT
	| arrayName=IDENT '[' index=DIGIT ']'
;

negation:
	(op='~')* rhs=term
	;
	
unary:
	op=('+' | '-')* rhs=negation
;

binaryMultiplication:
	lhs=unary (op=('*' | '/' | '%') rhs=unary)*
;
	
binaryAddition:
	lhs=binaryMultiplication (op=('+' | '-') rhs=binaryMultiplication )*
;

relation:
	lhs=binaryAddition (op=('==' | '!=' | '<' | '>' | '<=' | '>=') rhs=binaryAddition )*
;

expression:


lhs=relation (op=('&&' | '||') rhs=relation)

*
;

compoundstatement:
 	ifStatement
 	| loopStatement
 ;

ifStatementBody:
		(declarations)*
		sequenceofstatements
;

ifStatement:
	'if' expression 'then'
	'{'
		ifStatementBody
	'}'
	('otherwise if' expression 'then'
	'{'
		ifStatementBody
	'}'
	)*
	('otherwise'
	'{'
		ifStatementBody
	'}'
	)?
;

loopEnterStatement:
'loop' expression
;

loopStatement:
	loopEnterStatement
	'{'
		(declarations)*
		sequenceofstatements
		(loopbreakstatement)?
	'}'
;

datatypes: (SIMPLEDATATYPE | COMPOUNDDATATYPE);
SIMPLEDATATYPE: ('int' | 'bool');
COMPOUNDDATATYPE: ('Array' | 'Stack');
STACKOPERATIONS: ('push' | 'pop');
identifier: IDENT;
ENDOFSTATEMENT: ';' ;
IDENT: ('a'..'z' | 'A'..'Z') ('a'..'z' | 'A'..'Z' | '0'..'9')*;
digit: DIGIT;
DIGIT :('0'..'9')+;
WS: ('\n' | '\r' | ' ' | '\t' | '\f' ) -> skip;