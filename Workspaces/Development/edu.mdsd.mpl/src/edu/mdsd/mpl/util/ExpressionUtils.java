package edu.mdsd.mpl.util;

import java.util.ArrayList;
import java.util.List;

import edu.mdsd.mpl.AtomicExpression;
import edu.mdsd.mpl.BinaryExpression;
import edu.mdsd.mpl.Expression;
import edu.mdsd.mpl.OperationCall;
import edu.mdsd.mpl.UnaryExpression;

public class ExpressionUtils {
	private static void getAtomicExpressions(List<AtomicExpression> refs, Expression expr) {
		if (expr instanceof AtomicExpression) {
			refs.add((AtomicExpression) expr);
		} else if (expr instanceof UnaryExpression) {
			getAtomicExpressions(refs, ((UnaryExpression) expr).getOperand());
		} else if (expr instanceof BinaryExpression) {
			BinaryExpression binExpr = (BinaryExpression) expr;
			getAtomicExpressions(refs, binExpr.getOperand1());
			getAtomicExpressions(refs, binExpr.getOperand2());
		}
	}
	
	public static List<AtomicExpression> getAtomicExpressions(Expression expr) {
		List<AtomicExpression> refs = new ArrayList<>();
		getAtomicExpressions(refs, expr);
		return refs;
	}
	
	public static boolean containsAtLeastOneOperationCall(Expression expr) {
		List<AtomicExpression> atoms = getAtomicExpressions(expr);
		
		for (AtomicExpression atom : atoms) {
			if (atom instanceof OperationCall)
				return true;
		}
		
		return false;
	}
}
