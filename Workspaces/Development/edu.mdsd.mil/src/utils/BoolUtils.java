package utils;

public class BoolUtils {
	public static boolean toBool(int val) {
		return val != 0;
	}
	
	public static int toInt(boolean val) {
		return val ? 1 : 0;
	}
}
