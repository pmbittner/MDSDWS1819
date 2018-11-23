package edu.mdsd.mil.interpreter.instruction.operation.binary;

import edu.mdsd.mil.SubInstruction;
import edu.mdsd.mil.interpreter.MILInterpreter;
import edu.mdsd.mil.interpreter.instruction.operation.BinaryOperationInterpreter;

public class SubInterpreter extends BinaryOperationInterpreter<SubInstruction> {

	@Override
	public void interpretOperation(MILInterpreter interpreter, SubInstruction instruction, int operand1, int operand2) {
		interpreter.getOperandStack().push(operand1 - operand2);
	}
	
}
