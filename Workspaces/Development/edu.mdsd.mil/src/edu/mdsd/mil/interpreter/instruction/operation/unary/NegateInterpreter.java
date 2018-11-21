package edu.mdsd.mil.interpreter.instruction.operation.unary;

import edu.mdsd.mil.NegateInstruction;
import edu.mdsd.mil.interpreter.MILInterpreter;
import edu.mdsd.mil.interpreter.instruction.operation.UnaryOperationInterpreter;

public class NegateInterpreter extends UnaryOperationInterpreter<NegateInstruction> {

	@Override
	protected void interpretOperation(MILInterpreter interpreter, NegateInstruction instruction, int operand) {
		int neg = operand == 0 ? 1 : 0;
		interpreter.getOperandStack().push(neg);
	}

}
