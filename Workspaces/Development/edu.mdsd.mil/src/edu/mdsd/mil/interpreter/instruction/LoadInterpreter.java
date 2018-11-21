package edu.mdsd.mil.interpreter.instruction;

import edu.mdsd.mil.LoadInstruction;
import edu.mdsd.mil.interpreter.InstructionInterpreter;
import edu.mdsd.mil.interpreter.MILInterpreter;

public class LoadInterpreter extends InstructionInterpreter<LoadInstruction> {

	@Override
	protected void interpret(MILInterpreter interpreter, LoadInstruction instruction) {
		int rawValue = interpreter.getRawValue(instruction.getValue());
		interpreter.getOperandStack().push(rawValue);
	}

}
