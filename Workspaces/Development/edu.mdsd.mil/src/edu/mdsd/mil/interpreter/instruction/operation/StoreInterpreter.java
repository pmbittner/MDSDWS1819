package edu.mdsd.mil.interpreter.instruction.operation;

import edu.mdsd.mil.RegisterReference;
import edu.mdsd.mil.StoreInstruction;
import edu.mdsd.mil.interpreter.MILInterpreter;

public class StoreInterpreter extends UnaryOperationInterpreter<StoreInstruction> {

	@Override
	protected void interpretOperation(MILInterpreter interpreter, StoreInstruction instruction, int rawValue) {
		RegisterReference registerReference = instruction.getRegisterReference();
		
		if (registerReference != null) {
			interpreter.getVariableRegister().put(registerReference.getAddress(), rawValue);
		}
	}

}
