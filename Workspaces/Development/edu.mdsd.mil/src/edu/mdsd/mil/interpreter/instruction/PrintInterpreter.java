package edu.mdsd.mil.interpreter.instruction;

import edu.mdsd.mil.PrintInstruction;
import edu.mdsd.mil.interpreter.InstructionInterpreter;
import edu.mdsd.mil.interpreter.MILInterpreter;

public class PrintInterpreter extends InstructionInterpreter<PrintInstruction> {
	@Override
	protected void interpret(MILInterpreter interpreter, PrintInstruction instruction) {
		String msg = instruction.getText();
		msg = msg.substring(1, msg.length() - 1);
		// Twice for Java
		// Twice for replaceAll expecting a regex
		msg = msg.replaceAll("\\\\n", "\n");
		interpreter.out().print(msg);
	}
}