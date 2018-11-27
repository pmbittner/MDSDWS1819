package utils;

import java.io.PrintStream;

import edu.mdsd.mil.interpreter.MILInterpreter;

public class SystemOutput implements Output {
	private PrintStream outStream, errorStream;
	private MILInterpreter interpreter;

	public SystemOutput(MILInterpreter interpreter) {
		this.interpreter = interpreter;
		
		outStream = System.out;
		errorStream = System.err;
	}
	
	public void print(Object o) {
		outStream.print(o.toString());
	}

	public void println(Object o) {
		outStream.println(o.toString());
	}

	public void err(String s) {
		errorStream.println("[ERROR in line " + interpreter.getCurrentPosition() + "] " + s);
	}

	public void warn(String s) {
		errorStream.println("[WARNING in line " + interpreter.getCurrentPosition() + "] " + s);
	}
}
