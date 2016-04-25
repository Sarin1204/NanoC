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
(declarations | assignmentstatement | functions)*
//mainFunction?
;

//mainFunction:
//'define' i='start' '(' (p=parameters)?')' ('returns' d=datatypes)?
//'{'
//mainFunctionBody
//'}'
//;

//mainFunctionBody:
//declarations*
//(assignmentstatement | sequenceofstatements)
//;

declarations:
sd=SIMPLEDATATYPE i=IDENT (op='=' expression )? ENDOFSTATEMENT
|
cd=compoundDataType identifier '=' SIMPLEDATATYPE'[' digit ']' ENDOFSTATEMENT
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

parameters: datatypes IDENT (',' datatypes IDENT)*;
sequenceofstatements: (statement)*;
statement: (simplestatement | compoundstatement);
simplestatement : returnstatement | assignmentstatement;

returnstatement: 'return' (e=expression | f=functionCall)? ENDOFSTATEMENT;
loopbreakstatement: 'terminate' ENDOFSTATEMENT;
assignmentstatement:
	lhs=identifier op='=' rhs=expression ENDOFSTATEMENT
	| (lhs=identifier '=')? f=functionCall ENDOFSTATEMENT
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



ifStatement:
	'if' expression 'then'
	'{'
		(declarations)*
		sequenceofstatements
	'}'
	('otherwise if' expression 'then'
	'{'
		(declarations)*
		sequenceofstatements
	'}'
	)*
	('otherwise'
	'{'
		(declarations)*
		sequenceofstatements
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

datatypes: (SIMPLEDATATYPE | compoundDataType);
SIMPLEDATATYPE: ('int' | 'bool');
compoundDataType: ('Array' | 'Stack');
identifier: IDENT;
ENDOFSTATEMENT: ';' ;
IDENT: ('a'..'z' | 'A'..'Z') ('a'..'z' | 'A'..'Z' | '0'..'9')*;
digit: DIGIT;
DIGIT :('0'..'9')+;
WS: ('\n' | '\r' | ' ' | '\t' | '\f' ) -> skip;