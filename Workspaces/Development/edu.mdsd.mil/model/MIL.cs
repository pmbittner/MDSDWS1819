SYNTAXDEF mil
FOR <http://mdsd.edu/mil/1.0>
START MILModel

OPTIONS {
	reloadGeneratorModel = "true";
	overrideLaunchConfigurationDelegate = "false";
}

TOKENS {
	DEFINE IDENTIFIER_TOKEN $('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*$;
	DEFINE INTEGER_TOKEN $('-')?('0'..'9')+$;
	DEFINE STRING_TOKEN $ '"' ('a'..'z'|'A'..'Z'|'_'|'0'..'9'|$ + WHITESPACE + $|'\\n')* '"'$;
	
	DEFINE SL_COMMENT $'//'(~('\n'|'\r'|'\uffff'))*$;
	DEFINE ML_COMMENT $'/*'.*'*/'$;
}

TOKENSTYLES {
	"IDENTIFIER_TOKEN" COLOR #6A3E3E;
	"INTEGER_TOKEN" COLOR #0000C0;
	"SL_COMMENT", "ML_COMMENT" COLOR #3F7F5F;
}

RULES {
	MILModel ::= statements*;
	
	JumpMarker ::= name[IDENTIFIER_TOKEN] ":";

	LoadInstruction ::= "lod" value;
	ReturnInstruction ::= "ret";
	PrintInstruction ::= "prt" text[STRING_TOKEN];
	JumpInstruction ::= "jmp" jumpTarget[IDENTIFIER_TOKEN];
	CallInstruction ::= "cal" jumpTarget[IDENTIFIER_TOKEN];
	
	ConditionalJumpInstruction ::= "jpc" jumpTarget[IDENTIFIER_TOKEN];
	NegateInstruction ::= "neg";
	StoreInstruction ::= "sto" registerReference?;
	YieldInstruction ::= "yld";
	
	AddInstruction ::= "add";
	SubInstruction ::= "sub";
	MultInstruction ::= "mul";
	DivInstruction ::= "div";
	
	EqualsComparison ::= "eq";
	NotEqualsComparison ::= "neq";
	LowerThanComparison ::= "lt";
	LowerEqualsComparison ::= "leq";
	GreaterThanComparison ::= "gt";
	GreaterEqualsComparison ::= "geq";

	ConstantInteger ::= rawValue[INTEGER_TOKEN];
	RegisterReference ::= address[IDENTIFIER_TOKEN];
}
