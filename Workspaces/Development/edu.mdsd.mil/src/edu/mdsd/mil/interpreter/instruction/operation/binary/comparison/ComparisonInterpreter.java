package edu.mdsd.mil.interpreter.instruction.operation.binary.comparison;

import edu.mdsd.mil.Comparison;
import edu.mdsd.mil.interpreter.MILInterpreter;
import edu.mdsd.mil.interpreter.instruction.operation.BinaryOperationInterpreter;
import utils.BoolUtils;

public abstract class ComparisonInterpreter extends BinaryOperationInterpreter<Comparison> {

	@Override
	protected final void interpretOperation(MILInterpreter interpreter, Comparison instruction, int operand1, int operand2) {
		interpreter.getOperandStack().push(BoolUtils.toInt(compare(operand1, operand2)));
	}
	
	protected abstract boolean compare(int operand1, int operand2);

}
