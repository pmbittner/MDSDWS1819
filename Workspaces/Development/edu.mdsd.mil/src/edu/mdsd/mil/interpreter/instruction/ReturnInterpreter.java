package edu.mdsd.mil.interpreter.instruction;

import edu.mdsd.mil.ReturnInstruction;
import edu.mdsd.mil.interpreter.CallStack;
import edu.mdsd.mil.interpreter.InstructionInterpreter;
import edu.mdsd.mil.interpreter.MILInterpreter;

public class ReturnInterpreter extends InstructionInterpreter<ReturnInstruction> {

	@Override
	protected void interpret(MILInterpreter interpreter, ReturnInstruction instruction) {
		CallStack.Frame lastFrame = interpreter.getCallStack().pop();
		int returnPos = lastFrame.getReturnAddress();
		interpreter.jumpTo(returnPos);
	}

}
