package edu.mdsd.mpl.validation;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import edu.mdsd.mpl.AtomicExpression;
import edu.mdsd.mpl.Expression;
import edu.mdsd.mpl.Program;
import edu.mdsd.mpl.Variable;
import edu.mdsd.mpl.VariableDeclaration;
import edu.mdsd.mpl.VariableReference;
import edu.mdsd.mpl.util.ExpressionUtils;

public class InitialValueInVarDecExistsConstraint extends AbstractModelConstraint {
	private boolean isDefinedBefore(Variable target, Variable source, List<VariableDeclaration> varDecs) {
		for (VariableDeclaration varDec : varDecs) {
			if (varDec.getVariable() == source)
				break;
			
			if (varDec.getVariable() == target)
				return true;
		}
		
		return false;
	}
	
	@Override
	public IStatus validate(IValidationContext ctx) {
		VariableDeclaration varDec = (VariableDeclaration) ctx.getTarget();
		Variable variable = varDec.getVariable();
		Expression initialValue = varDec.getInitialValue();
		Program program = (Program) varDec.eContainer();
		
		List<VariableDeclaration> declarations = program.getVariableDeclarations();
		List<AtomicExpression> atomics = ExpressionUtils.getAtomicExpressions(initialValue);
		
		for (AtomicExpression atomic : atomics) {
			if (atomic instanceof VariableReference) {
				Variable other = ((VariableReference)atomic).getVariable();
				
				if (!isDefinedBefore(other, variable, declarations)) {
					return ctx.createFailureStatus(other);
				}
			}
		}
		
		return ctx.createSuccessStatus();
	}
}
