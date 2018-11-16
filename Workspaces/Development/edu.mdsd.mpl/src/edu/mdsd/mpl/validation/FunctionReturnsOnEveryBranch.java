package edu.mdsd.mpl.validation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import edu.mdsd.mpl.Block;
import edu.mdsd.mpl.ForLoop;
import edu.mdsd.mpl.Function;
import edu.mdsd.mpl.IfStatement;
import edu.mdsd.mpl.ReturnStatement;
import edu.mdsd.mpl.Statement;
import edu.mdsd.mpl.WhileLoop;
import edu.mdsd.mpl.util.StatementUtils;

public class FunctionReturnsOnEveryBranch extends AbstractModelConstraint {
	private static boolean providesReturnStatement(Block block) {
		if (block == null)
			return false;
		
		// If a return statement is found at the top level, we are done.
		for (Statement statement : block.getStatements()) {
			if (statement instanceof ReturnStatement)
				return true;
			if (statement instanceof WhileLoop)
				if (providesReturnStatement(((WhileLoop) statement).getBlock()))
					return true;
			if (statement instanceof ForLoop)
				if (providesReturnStatement(((ForLoop) statement).getBlock()))
					return true;
		}
		
		// If there are no branches, the return statement is missing.
		if (!StatementUtils.contains(block, IfStatement.class))
			return false;
		
		// Now we can assume, that IfStatements are present.
		// At least one IfStatement has to have return statements at both branches.
		// Otherwise always the non-returning branch could be chosen.
		for (Statement statement : block.getStatements()) {
			if (statement instanceof IfStatement) {
				IfStatement condition = (IfStatement) statement;
				
				// If the condition does not have an else branch, we are not interested in it,
				// since it does not enforce returning.
				if (condition.getElse() != null) {
					if (providesReturnStatement(condition.getThen()) && providesReturnStatement(condition.getElse()))
						return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public IStatus validate(IValidationContext ctx) {
		Function fun = (Function) ctx.getTarget();
		
		if (fun.getBlock() == null || !providesReturnStatement(fun.getBlock())) {
			return ctx.createFailureStatus();
		}
		
		return ctx.createSuccessStatus();
	}
}
