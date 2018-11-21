package edu.mdsd.mil.interpreter.instruction;

import edu.mdsd.mil.AddInstruction;
import edu.mdsd.mil.interpreter.InstructionInterpreter;
import edu.mdsd.mil.interpreter.MILInterpreter;
import edu.mdsd.mil.interpreter.OperandStack;

public class AddInterpreter extends InstructionInterpreter<AddInstruction> {

	@Override
	public void interpret(MILInterpreter interpreter, AddInstruction instruction) {
		System.out.println("AddInstruction");
		
		OperandStack ops = interpreter.getOperandStack();
		int operand2 = ops.pop();
		int operand1 = ops.pop();
		
		ops.push(operand1 + operand2);
	}
	
}
