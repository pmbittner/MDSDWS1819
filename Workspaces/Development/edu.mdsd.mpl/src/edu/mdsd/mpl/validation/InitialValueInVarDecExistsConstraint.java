package edu.mdsd.mpl.validation;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import edu.mdsd.mpl.AtomicExpression;
import edu.mdsd.mpl.Expression;
import edu.mdsd.mpl.Operation;
import edu.mdsd.mpl.Variable;
import edu.mdsd.mpl.VariableDeclaration;
import edu.mdsd.mpl.VariableReference;
import edu.mdsd.mpl.util.ExpressionUtils;
import edu.mdsd.mpl.util.VariableUtils;

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
		Variable currentlyDeclaredVariable = varDec.getVariable();
		Expression initialValue = varDec.getInitialValue();
		
		Operation operation = (Operation) varDec.eContainer();
		List<VariableDeclaration> parameters = operation.getParameters();
		List<VariableDeclaration> declarations = operation.getVariableDeclarations();
		List<AtomicExpression> atomics = ExpressionUtils.getAtomicExpressions(initialValue);
		
		for (AtomicExpression atomic : atomics) {
			if (atomic instanceof VariableReference) {
				Variable referencedVariable = ((VariableReference)atomic).getVariable();
				
				// check if referenced variable is a parameter
				if (VariableUtils.contains(parameters, referencedVariable))
					continue;
				
				if (!isDefinedBefore(referencedVariable, currentlyDeclaredVariable, declarations)) {
					return ctx.createFailureStatus(referencedVariable);
				}
			}
		}
		
		return ctx.createSuccessStatus();
	}
}
