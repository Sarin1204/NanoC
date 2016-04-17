grammar OurCompiler2;

options {
language = Java;
}

@header {
  package antlr.compiler;
}

@lexer::header {
  package antlr.compiler;
}
rule: program;

program:
(declarations)*
functions*
;

declarations:
simpleDataType IDENT ('=' expression )? ';'
|
compoundDataType IDENT '=' simpleDataType'[' DIGIT ']' ';'
;

functions:
'define' IDENT '(' (parameters)?')' ('returns' datatypes)?
'{'
declarations*
sequenceofstatements
'}'
;

parameters: datatypes IDENT (',' datatypes IDENT)*;
sequenceofstatements: (statement)*;
statement: (simplestatement | compoundstatement);
simplestatement : returnstatement | assignmentstatement;

returnstatement: 'return' (expression | functionCall) ';';
loopbreakstatement: 'terminate' ';';
assignmentstatement:
	IDENT '=' expression ';'
	| IDENT '=' functionCall ';'
 ;

functionCall:
	IDENT '(' actualParameters? ')'
;

actualParameters:
	expression (',' expression)
;

term: IDENT 
	| '(' expression ')'
	| DIGIT
;

negation:
	('negate')* term
	;
	
unary:
	('+' | '-')* negation
;

binaryMultiplication:
	unary (('*' | '/' | '%') unary)*
;
	
binaryAddition:
	binaryMultiplication (('+' | '-')binaryMultiplication )*
;

relation:
	binaryAddition (('==' | '!=' | '<' | '>' | '<=' | '>=') binaryAddition )*
;

expression:
relation (('&&' | '||') relation)*
 	;
 	
compoundstatement:
 	ifStatement
 	| loopStatement
 ;

ifStatement:
	'if' expression
	'{'
		sequenceofstatements
	'}'
	('otherwise if' expression
	'{'
		sequenceofstatements
	'}'
	)*
	('otherwise'
	'{'
	sequenceofstatements
	'}'
	)?
;

loopStatement:
'loop' expression
	'{'
		sequenceofstatements
		(loopbreakstatement)?
	'}'

;

datatypes: (simpleDataType | compoundDataType);
simpleDataType: ('int' | 'bool');
compoundDataType: ('Array' | 'Stack');
IDENT: ('a'..'z' | 'A'..'Z') ('a'..'z' | 'A'..'Z' | '0'..'9')*;
DIGIT: '0'..'9'+;
WS: ('\n' | '\r' | ' ' | '\t' | '\f' ) {$channel=HIDDEN;};