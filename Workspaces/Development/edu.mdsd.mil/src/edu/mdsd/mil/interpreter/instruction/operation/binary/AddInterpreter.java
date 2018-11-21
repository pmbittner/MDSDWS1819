package edu.mdsd.mil.interpreter.instruction.operation;

import edu.mdsd.mil.AddInstruction;
import edu.mdsd.mil.interpreter.MILInterpreter;

public class AddInterpreter extends BinaryOperationInterpreter<AddInstruction> {

	@Override
	public void interpretOperation(MILInterpreter interpreter, AddInstruction instruction, int operand1, int operand2) {
		interpreter.getOperandStack().push(operand1 + operand2);
	}
	
}
