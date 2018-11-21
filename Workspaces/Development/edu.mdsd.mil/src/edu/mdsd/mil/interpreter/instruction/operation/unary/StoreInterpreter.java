package edu.mdsd.mil.interpreter.instruction.operation.unary;

import edu.mdsd.mil.RegisterReference;
import edu.mdsd.mil.StoreInstruction;
import edu.mdsd.mil.interpreter.MILInterpreter;
import edu.mdsd.mil.interpreter.instruction.operation.UnaryOperationInterpreter;

public class StoreInterpreter extends UnaryOperationInterpreter<StoreInstruction> {

	@Override
	protected void interpretOperation(MILInterpreter interpreter, StoreInstruction instruction, int rawValue) {
		RegisterReference registerReference = instruction.getRegisterReference();
		
		if (registerReference != null) {
			interpreter.getVariableRegister().put(registerReference.getAddress(), rawValue);
		}
	}

}
