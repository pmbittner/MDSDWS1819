SYNTAXDEF mpl
FOR <http://mdsd.edu/mpl/1.0>
START Program

OPTIONS {
	reloadGeneratorModel = "true";
	defaultTokenName = "IDENTIFIER_TOKEN";
}

TOKENS {
	DEFINE IDENTIFIER_TOKEN $('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*$;
	DEFINE INTEGER_TOKEN $('0'..'9')+$;
	
	DEFINE SL_COMMENT $'//'(~('\n'|'\r'|'\uffff'))*$;
	DEFINE ML_COMMENT $'/*'.*'*/'$;
}

TOKENSTYLES {
	"IDENTIFIER_TOKEN" COLOR #6A3E3E;
	"INTEGER_TOKEN" COLOR #0000C0;
	"SL_COMMENT", "ML_COMMENT" COLOR #3F7F5F;
}

RULES {
	// syntax definition for class 'Program'
	Program ::= "Program" #1 name[] (!1 "Variables" !1 variableDeclarations ("," #1 variableDeclarations)* ".")? (!1 statements)* "End" ".";
	
	VariableDeclaration ::= variable (":=" initialValue)?;
	Variable ::= name[];
	
	@Operator(type="binary_left_associative", weight="1", superclass="Expression")
	AddExpression ::= operand1 #1 "+" #1 operand2;
	@Operator(type="binary_left_associative", weight="1", superclass="Expression")
	SubExpression ::= operand1 #1 "-" #1 operand2;
	
	@Operator(type="binary_left_associative", weight="2", superclass="Expression")
	MultExpression ::= operand1 #1 "*" #1 operand2;
	@Operator(type="binary_left_associative", weight="2", superclass="Expression")
	DivExpression ::= operand1 #1 "/" #1 operand2;
	
	@Operator(type="unary_prefix", weight="3", superclass="Expression")
	UnaryMinusExpression ::= "-" operand;
	
	@Operator(type="primitive", weight="4", superclass="Expression")
	ParenthesisExpression ::= "(" operand ")";
	
	@Operator(type="primitive", weight="4", superclass="Expression")
	VariableReference ::= variable[];
	
	@Operator(type="primitive", weight="4", superclass="Expression")
	LiteralValue ::= rawValue[INTEGER_TOKEN];
	
	
	Assignment ::= leftHandSide #1 ":=" #1 rightHandSide ".";
	
	ExpressionStatement ::= expression ".";
}
