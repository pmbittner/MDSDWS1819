package edu.mdsd.mpl.validation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import edu.mdsd.mpl.ExpressionStatement;
import edu.mdsd.mpl.Function;
import edu.mdsd.mpl.Operation;
import edu.mdsd.mpl.OperationCall;

public class OnlyFunctionCallsInsideExpressions extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		OperationCall opCall = (OperationCall) ctx.getTarget();
		Operation op = opCall.getOperation();
		
		// Function calls are always allowed.
		if (!(op instanceof Function)) {
			// op has to be an instance of Procedure now. It could be Program theoretically, but that is forbidden by the grammar.
			EObject container = opCall.eContainer();
			
			// Procedures are only allowed to appear inside ExpressionStatements directly.
			if (!(container instanceof ExpressionStatement))
				return ctx.createFailureStatus();
		}
		
		return ctx.createSuccessStatus();
	}

}
