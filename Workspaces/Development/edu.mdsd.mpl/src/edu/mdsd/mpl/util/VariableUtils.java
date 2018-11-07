package edu.mdsd.mpl.util;

import java.util.List;

import edu.mdsd.mpl.Variable;
import edu.mdsd.mpl.VariableDeclaration;

public class VariableUtils {
	public static boolean contains(List<VariableDeclaration> declarations, Variable variable) {
		for (VariableDeclaration varDec : declarations) {
			if (varDec.getVariable() == variable)
				return true;
		}
		
		return false;
	}
}
