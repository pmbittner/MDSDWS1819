package edu.mdsd.mil.interpreter.instruction;

import edu.mdsd.mil.RegisterReference;
import edu.mdsd.mil.StoreInstruction;
import edu.mdsd.mil.interpreter.InstructionInterpreter;
import edu.mdsd.mil.interpreter.MILInterpreter;

public class StoreInterpreter extends InstructionInterpreter<StoreInstruction> {

	@Override
	public void interpret(MILInterpreter interpreter, StoreInstruction instruction) {
		System.out.println("StoreInstruction");
		
		RegisterReference registerReference = instruction.getRegisterReference();
		int rawValue = interpreter.popFromOperandStack();
		
		if (registerReference != null) {
			interpreter.setVariableRegisterValue(registerReference.getAddress(), rawValue);
		}
	}

}
