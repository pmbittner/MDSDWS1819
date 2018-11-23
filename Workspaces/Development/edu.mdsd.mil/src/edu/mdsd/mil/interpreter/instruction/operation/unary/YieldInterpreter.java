package edu.mdsd.mil.interpreter.instruction.operation.unary;

import edu.mdsd.mil.YieldInstruction;
import edu.mdsd.mil.interpreter.MILInterpreter;
import edu.mdsd.mil.interpreter.instruction.operation.UnaryOperationInterpreter;

public class YieldInterpreter extends UnaryOperationInterpreter<YieldInstruction> {

	@Override
	protected void interpretOperation(MILInterpreter interpreter, YieldInstruction instruction, int operand) {
		interpreter.out().println(operand);
	}

}
