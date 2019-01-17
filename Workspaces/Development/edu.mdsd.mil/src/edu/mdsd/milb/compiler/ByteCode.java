package edu.mdsd.milb.compiler;

public enum ByteCode {
	// nullary
	NOOP(0),
	
	RET(1),
	
	NEG(2),
	
	ADD(3),
	SUB(4),
	MUL(5),
	DIV(6),
	
	EQ(7),
	NEQ(8),
	LT(9),
	LEQ(10),
	GT(11),
	GEQ(12),
	
	YLD(13),
	
	// unary
	LOD(14),
	LDV(15), // load var
	STO(16),
	STT(17), // store to
	
	JMP(18),
	JPC(19),
	CAL(20),
	
	PRT(21);
	
	public final byte instructionIndex;
	
	/*
	 * Byte expected, but in used for simplicity,
	 * as byte literals can't be specified.
	 */
	private ByteCode(int index) {
		this.instructionIndex = (byte) index;
	}
}
