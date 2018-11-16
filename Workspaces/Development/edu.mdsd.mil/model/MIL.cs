SYNTAXDEF mil
FOR <http://mdsd.edu/mil/1.0>
START MILModel

OPTIONS {
	reloadGeneratorModel = "true";
}

TOKENS {
	DEFINE IDENTIFIER_TOKEN $('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*$;
	DEFINE INTEGER_TOKEN $('-')?('0'..'9')+$;
	
	DEFINE SL_COMMENT $'//'(~('\n'|'\r'|'\uffff'))*$;
	DEFINE ML_COMMENT $'/*'.*'*/'$;
}

TOKENSTYLES {
	"IDENTIFIER_TOKEN" COLOR #6A3E3E;
	"INTEGER_TOKEN" COLOR #0000C0;
	"SL_COMMENT", "ML_COMMENT" COLOR #3F7F5F;
}

RULES {
	// syntax definition for class 'MILModel'
	MILModel ::= instructions*;
	
	LoadInstruction ::= "lod" value;
	StoreInstruction ::= "sto" registerReference?;
	AddInstruction ::= "add";
	SubInstruction ::= "sub";
	MultInstruction ::= "mul";
	DivInstruction ::= "div";
	
	ConstantInteger ::= rawValue[INTEGER_TOKEN];
	RegisterReference ::= address[IDENTIFIER_TOKEN];
}
