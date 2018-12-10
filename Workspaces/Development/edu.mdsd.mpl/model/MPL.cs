SYNTAXDEF mpl
FOR <http://mdsd.edu/mpl/1.0>
START MPLModel

OPTIONS {
	reloadGeneratorModel = "true";
	defaultTokenName = "IDENTIFIER_TOKEN";
	overrideLaunchConfigurationDelegate = "false";
}

TOKENS {
	DEFINE IDENTIFIER_TOKEN $('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*$;
	DEFINE INTEGER_TOKEN $('0'..'9')+$;
	
	DEFINE COMPARISON_OPERATOR_TOKEN $ '=' | '<>' | '<' | '>' | '<=' | '>=' $;
	
	DEFINE SL_COMMENT $'//'(~('\n'|'\r'|'\uffff'))*$;
	DEFINE ML_COMMENT $'/*'.*'*/'$;
}

TOKENSTYLES {
	"IDENTIFIER_TOKEN" COLOR #6A3E3E;
	"INTEGER_TOKEN" COLOR #0000C0;
	"SL_COMMENT", "ML_COMMENT" COLOR #3F7F5F;
}

RULES {
	MPLModel ::= (operations : Procedure, Function)* program (operations : Procedure, Function)*;
	
	// syntax definition for class 'Program'
	Program ::= "Program" #1 name[] (!1 "Variables" !1 variableDeclarations ("," #1 variableDeclarations)* ".")? !1 block !1 "End" ".";
	
	// OPERATIONS
	Procedure ::= "Procedure" name[] "(" (parameters ("," #1 parameters)*)? ")" (!1 "Variables" !1 variableDeclarations ("," #1 variableDeclarations)* ".")? block "End" ".";
	Function  ::= "Function"  name[] "(" (parameters ("," #1 parameters)*)? ")" (!1 "Variables" !1 variableDeclarations ("," #1 variableDeclarations)* ".")? block "End" ".";
	
	// VARIABLES

	VariableDeclaration ::= variable (":=" initialValue)?;
	Variable ::= name[];
	
	// EXPRESSIONS
	
	Comparison ::= operand1 #1 operator[COMPARISON_OPERATOR_TOKEN] #1 operand2;
	
	@Operator(type="binary_left_associative", weight="2", superclass="Expression")
	AddExpression ::= operand1 #1 "+" #1 operand2;
	@Operator(type="binary_left_associative", weight="2", superclass="Expression")
	SubExpression ::= operand1 #1 "-" #1 operand2;
	
	@Operator(type="binary_left_associative", weight="3", superclass="Expression")
	MultExpression ::= operand1 #1 "*" #1 operand2;
	@Operator(type="binary_left_associative", weight="3", superclass="Expression")
	DivExpression ::= operand1 #1 "/" #1 operand2;
	
	@Operator(type="unary_prefix", weight="4", superclass="Expression")
	UnaryMinusExpression ::= "-" operand;
	
	@Operator(type="primitive", weight="5", superclass="Expression")
	ParenthesisExpression ::= "(" operand ")";
	@Operator(type="primitive", weight="5", superclass="Expression")
	VariableReference ::= variable[];
	@Operator(type="primitive", weight="5", superclass="Expression")
	LiteralValue ::= rawValue[INTEGER_TOKEN];
	@Operator(type="primitive", weight="5", superclass="Expression")
	OperationCall ::= operation[] "(" (parameters ("," #1 parameters)*)? ")";
	
	// STATEMENTS
	
	//@Operator(type="primitive", weight="1", superclass="Statement")
	Block ::= statements*;
	
	Assignment ::= leftHandSide #1 ":=" #1 rightHandSide;
	
	@Operator(type="primitive", weight="2", superclass="Statement")
	AssignmentStatement ::= assignment ".";
	@Operator(type="primitive", weight="2", superclass="Statement")
	ExpressionStatement ::= expression ".";
	@Operator(type="primitive", weight="2", superclass="Statement")
	TraceStatement ::= "Trace" "(" variableReference ")" ".";
	@Operator(type="primitive", weight="2", superclass="Statement")
	ReturnStatement ::= "Return" value? ".";
	
	@Operator(type="primitive", weight="2", superclass="Statement")
	IfStatement ::= "If" "(" condition ")" "Then" then ("Else" else)? "End" ".";
	@Operator(type="primitive", weight="2", superclass="Statement")
	WhileLoop ::= "While" "(" condition ")" "Do" block "End" ".";
	@Operator(type="primitive", weight="2", superclass="Statement")
	ForLoop ::= "For" index increment["" : "Down"] "To" upperBound[INTEGER_TOKEN] block "End" ".";
	
}
