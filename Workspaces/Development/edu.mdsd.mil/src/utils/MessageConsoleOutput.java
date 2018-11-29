package utils;

import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import edu.mdsd.mil.interpreter.MILInterpreter;

public class MessageConsoleOutput implements Output {

	private MessageConsoleStream out, warn, error;
	private MILInterpreter interpreter;
	
	public MessageConsoleOutput(MessageConsole console, MILInterpreter interpreter) {
		this.interpreter = interpreter;
		
		out = console.newMessageStream();
		warn = console.newMessageStream();
		error = console.newMessageStream();
		
		//out.setColor(new Color(d, new RGB(0, 0, 0)));
		//warn.setColor(new Color(d, new RGB(1, 1, 0)));
		//error.setColor(new Color(d, new RGB(1, 0, 0)));
	}
	
	@Override
	public void print(Object o) {
		out.print(o.toString());
	}

	@Override
	public void println(Object o) {
		out.println(o.toString());
	}

	@Override
	public void err(String s) {
		error.println("[ERROR in line " + interpreter.getCurrentPosition() + "] " + s);
	}

	@Override
	public void warn(String s) {
		warn.println("[WARNING in line " + interpreter.getCurrentPosition() + "] " + s);
	}
}
