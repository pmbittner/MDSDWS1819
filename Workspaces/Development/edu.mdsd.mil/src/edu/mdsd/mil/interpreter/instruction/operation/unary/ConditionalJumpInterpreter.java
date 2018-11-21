package edu.mdsd.mil.interpreter.instruction.operation.unary;

import edu.mdsd.mil.ConditionalJumpInstruction;
import edu.mdsd.mil.interpreter.MILInterpreter;
import edu.mdsd.mil.interpreter.instruction.operation.UnaryOperationInterpreter;
import utils.BoolUtils;

public class ConditionalJumpInterpreter extends UnaryOperationInterpreter<ConditionalJumpInstruction> {

	@Override
	protected void interpretOperation(MILInterpreter interpreter, ConditionalJumpInstruction instruction, int operand) {
		if (BoolUtils.toBool(operand)) {
			interpreter.jumpTo(instruction.getJumpTarget());
		}
	}

}
