package utils;

import java.io.PrintStream;

public interface Output {
	public PrintStream out();
	public PrintStream err();
	public PrintStream warn();
}
