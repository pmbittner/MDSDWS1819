package edu.mdsd.mpl.validation;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import edu.mdsd.mpl.MPLModel;
import edu.mdsd.mpl.Operation;

public class UniqueOperationNames extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		Operation op = (Operation) ctx.getTarget();
		String opName = op.getName();
		MPLModel model = (MPLModel) op.eContainer();
		List<Operation> operations = model.getOperations();
		
		for (Operation other : operations) {
			if (other != op && other.getName().equals(opName)) {
				return ctx.createFailureStatus(opName);
			}
		}
		
		return ctx.createSuccessStatus();
	}
}
