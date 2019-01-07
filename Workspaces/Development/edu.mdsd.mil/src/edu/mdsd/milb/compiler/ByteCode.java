package edu.mdsd.milb.compiler;

public enum ByteCode {
	// no arguments
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
	
	// 1 argument
	LOD,
	LDV, // load var
	STO,
	STT, // store to
	
	JMP,
	JPC,
	CAL,
	
	PRT,
}
