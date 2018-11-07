package edu.mdsd.mpl.validation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import edu.mdsd.mpl.Function;
import edu.mdsd.mpl.Operation;
import edu.mdsd.mpl.Procedure;
import edu.mdsd.mpl.Program;
import edu.mdsd.mpl.ReturnStatement;
import edu.mdsd.mpl.util.StatementUtils;

public class ValidReturnStatement extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		ReturnStatement ret = (ReturnStatement) ctx.getTarget();
		Operation op = StatementUtils.getContainingOperation(ret);
		
		if (op instanceof Program) {
			return ctx.createFailureStatus("Return statement is not allowed inside Program scope.");
		} else if (op instanceof Function) {
			if (ret.getValue() == null)
				return ctx.createFailureStatus("Return statments inside Functions must return a value!");
		} else if (op instanceof Procedure) {
			if (ret.getValue() != null)
				return ctx.createFailureStatus("Return statments inside Procedures can not have return a value!");
		}
		
		return ctx.createSuccessStatus();
	}

}
