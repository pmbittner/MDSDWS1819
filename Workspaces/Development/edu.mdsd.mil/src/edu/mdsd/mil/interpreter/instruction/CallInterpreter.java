package edu.mdsd.mil.interpreter.instruction;

import edu.mdsd.mil.CallInstruction;
import edu.mdsd.mil.interpreter.InstructionInterpreter;
import edu.mdsd.mil.interpreter.MILInterpreter;

public class CallInterpreter extends InstructionInterpreter<CallInstruction> {

	@Override
	protected void interpret(MILInterpreter interpreter, CallInstruction instruction) {
		int pos = interpreter.getCurrentPosition();
		interpreter.getCallStack().push(pos);
		interpreter.jumpTo(instruction.getJumpTarget());
	}

}
