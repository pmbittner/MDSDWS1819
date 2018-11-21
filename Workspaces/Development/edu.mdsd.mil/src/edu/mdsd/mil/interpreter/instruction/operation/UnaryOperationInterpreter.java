package edu.mdsd.mil.interpreter.instruction.operation;

import edu.mdsd.mil.UnaryOperation;
import edu.mdsd.mil.interpreter.InstructionInterpreter;
import edu.mdsd.mil.interpreter.MILInterpreter;
import edu.mdsd.mil.interpreter.OperandStack;

public abstract class UnaryOperationInterpreter<T extends UnaryOperation> extends InstructionInterpreter<T> {
	@Override
	protected final void interpret(MILInterpreter interpreter, T instruction) {
		int operandStackSize = interpreter.getOperandStack().size();
		if (operandStackSize < 1) {
			throw new IllegalArgumentException("Too few arguments (" + operandStackSize + ") on operand stack for BinaryOperation. Expected 1");
		} else {
			OperandStack ops = interpreter.getOperandStack();
			int operand = ops.pop();
			interpretOperation(interpreter, instruction, operand);
		}
	}
	
	protected abstract void interpretOperation(MILInterpreter interpreter, T instruction, int operand);
}
