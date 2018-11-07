package edu.mdsd.mpl.util;

import org.eclipse.emf.ecore.EObject;

import edu.mdsd.mpl.Block;
import edu.mdsd.mpl.Operation;
import edu.mdsd.mpl.Statement;

public class StatementUtils {
	public static Operation getContainingOperation(Statement statement) {
		EObject container = statement.eContainer();
		while (!(container instanceof Operation))
			container = container.eContainer();
		return (Operation) container;
	}
	
	public static <T extends Statement> boolean contains(Block block, Class<T> statementType) {
		for (Statement s : block.getStatements()) {
			if (statementType.isInstance(s)) {
				return true;
			}
		}
		
		return false;
	}
}
