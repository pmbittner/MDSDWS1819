package edu.mdsd.mil.interpreter.instruction;

import edu.mdsd.mil.AddInstruction;
import edu.mdsd.mil.interpreter.InstructionInterpreter;
import edu.mdsd.mil.interpreter.MILInterpreter;

public class AddInterpreter extends InstructionInterpreter<AddInstruction> {

	@Override
	public void interpret(MILInterpreter interpreter, AddInstruction instruction) {
		System.out.println("AddInstruction");
		
		int operand2 = interpreter.popFromOperandStack();
		int operand1 = interpreter.popFromOperandStack();
		
		interpreter.pushOnOperandStack(operand1 + operand2);
	}
	
}
