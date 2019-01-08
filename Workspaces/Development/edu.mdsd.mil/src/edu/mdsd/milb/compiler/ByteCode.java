package edu.mdsd.milb.compiler;

public enum ByteCode {
	// nullary
	NOOP,
	
	RET,
	
	NEG,
	
	ADD,
	SUB,
	MUL,
	DIV,
	
	EQ,
	NEQ,
	LT,
	LEQ,
	GT,
	GEQ,
	
	YLD,
	
	// unary
	LOD,
	LDV, // load var
	STO,
	STT, // store to
	
	JMP,
	JPC,
	CAL,
	
	PRT,
}
