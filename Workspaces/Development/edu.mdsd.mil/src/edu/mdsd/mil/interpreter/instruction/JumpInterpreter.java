package edu.mdsd.mil.interpreter.instruction;

import edu.mdsd.mil.JumpInstruction;
import edu.mdsd.mil.interpreter.InstructionInterpreter;
import edu.mdsd.mil.interpreter.MILInterpreter;

public class JumpInterpreter extends InstructionInterpreter<JumpInstruction> {

	@Override
	protected void interpret(MILInterpreter interpreter, JumpInstruction instruction) {
		interpreter.jumpTo(instruction.getJumpTarget());
	}

}
