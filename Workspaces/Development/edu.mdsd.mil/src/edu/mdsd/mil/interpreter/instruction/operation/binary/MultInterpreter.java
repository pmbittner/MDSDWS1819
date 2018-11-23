package edu.mdsd.mil.interpreter.instruction.operation.binary;

import edu.mdsd.mil.MultInstruction;
import edu.mdsd.mil.interpreter.MILInterpreter;
import edu.mdsd.mil.interpreter.instruction.operation.BinaryOperationInterpreter;

public class MultInterpreter extends BinaryOperationInterpreter<MultInstruction> {

	@Override
	public void interpretOperation(MILInterpreter interpreter, MultInstruction instruction, int operand1, int operand2) {
		interpreter.getOperandStack().push(operand1 * operand2);
	}
	
}
