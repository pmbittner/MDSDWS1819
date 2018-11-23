package edu.mdsd.mil.interpreter.instruction.operation.binary;

import edu.mdsd.mil.DivInstruction;
import edu.mdsd.mil.interpreter.MILInterpreter;
import edu.mdsd.mil.interpreter.instruction.operation.BinaryOperationInterpreter;

public class DivInterpreter extends BinaryOperationInterpreter<DivInstruction> {

	@Override
	public void interpretOperation(MILInterpreter interpreter, DivInstruction instruction, int operand1, int operand2) {
		interpreter.getOperandStack().push(operand1 / operand2);
	}
	
}
