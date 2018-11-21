package edu.mdsd.mil.interpreter;

import edu.mdsd.mil.Instruction;

public abstract class InstructionInterpreter<T extends Instruction> {
	@SuppressWarnings("unchecked")
	public void interpretUntyped(MILInterpreter interpreter, Object o) {
		interpret(interpreter, (T) o);
	}
	
	protected abstract void interpret(MILInterpreter interpreter, T instruction);
}
