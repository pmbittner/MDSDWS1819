package edu.mdsd.mpl.util;

import java.util.ArrayList;
import java.util.List;

import edu.mdsd.mpl.AtomicExpression;
import edu.mdsd.mpl.BinaryExpression;
import edu.mdsd.mpl.Expression;
import edu.mdsd.mpl.UnaryExpression;

public class ExpressionUtils {	
	private static void findVariableReferences(List<AtomicExpression> refs, Expression expr) {
		if (expr instanceof AtomicExpression) {
			refs.add((AtomicExpression) expr);
		} else if (expr instanceof UnaryExpression) {
			findVariableReferences(refs, ((UnaryExpression) expr).getOperand());
		} else if (expr instanceof BinaryExpression) {
			BinaryExpression binExpr = (BinaryExpression) expr;
			findVariableReferences(refs, binExpr.getOperand1());
			findVariableReferences(refs, binExpr.getOperand2());
		}
	}
	
	public static List<AtomicExpression> getAtomicExpressions(Expression expr) {
		List<AtomicExpression> refs = new ArrayList<>();
		findVariableReferences(refs, expr);
		return refs;
	}
}
