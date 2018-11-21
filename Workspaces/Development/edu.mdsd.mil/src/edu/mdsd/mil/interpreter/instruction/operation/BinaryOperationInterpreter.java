package edu.mdsd.mil.interpreter.instruction.operation;

import edu.mdsd.mil.BinaryOperation;
import edu.mdsd.mil.interpreter.InstructionInterpreter;
import edu.mdsd.mil.interpreter.MILInterpreter;
import edu.mdsd.mil.interpreter.OperandStack;

public abstract class BinaryOperationInterpreter<T extends BinaryOperation> extends InstructionInterpreter<T> {
	@Override
	protected final void interpret(MILInterpreter interpreter, T instruction) {
		int operandStackSize = interpreter.getOperandStack().size();
		if (operandStackSize < 2) {
			throw new IllegalArgumentException("Too few arguments (" + operandStackSize + ") on operand stack for BinaryOperation. Expected 2");
		} else {
			OperandStack ops = interpreter.getOperandStack();
			int operand2 = ops.pop();
			int operand1 = ops.pop();
			interpretOperation(interpreter, instruction, operand1, operand2);
		}
	}
	
	protected abstract void interpretOperation(MILInterpreter interpreter, T instruction, int operand1, int operand2);
}
