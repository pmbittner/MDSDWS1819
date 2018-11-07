package edu.mdsd.mpl.validation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import edu.mdsd.mpl.Expression;
import edu.mdsd.mpl.ExpressionStatement;
import edu.mdsd.mpl.util.ExpressionUtils;

public class UnusedExpressionResult extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		ExpressionStatement exprStatement = (ExpressionStatement) ctx.getTarget();
		Expression expr = exprStatement.getExpression();
		
		if (!ExpressionUtils.containsAtLeastOneOperationCall(expr))
			return ctx.createFailureStatus();
		
		return ctx.createSuccessStatus();
	}

}
