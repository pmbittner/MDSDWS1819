package edu.mdsd.mpl.validation;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import edu.mdsd.mpl.Block;
import edu.mdsd.mpl.ReturnStatement;
import edu.mdsd.mpl.Statement;

public class UnreachableCodeAfterReturnInSameBlock extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		Statement target = (Statement) ctx.getTarget();
		Block block = (Block) target.eContainer();
		List<Statement> statements = block.getStatements();
		
		for (Statement statement : statements) {
			if (statement == target)
				break;
			else if (statement instanceof ReturnStatement)
				return ctx.createFailureStatus();
		}
		
		return ctx.createSuccessStatus();
	}

}
