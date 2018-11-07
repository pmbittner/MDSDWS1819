package edu.mdsd.mpl.validation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import edu.mdsd.mpl.OperationCall;

public class ValidOperationCall extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		OperationCall opCall = (OperationCall) ctx.getTarget();
		
		if (opCall.getOperation() == null)
			return ctx.createFailureStatus("Operation does not exist!");
		
		if (opCall.getParameters().size() != opCall.getOperation().getParameters().size())
			return ctx.createFailureStatus(
					"Number of arguments for operation "
					+ opCall.getOperation().getName()
					+ " does not match required number of "
					+ opCall.getOperation().getParameters().size() + ".");
		
		return ctx.createSuccessStatus();
	}

}
