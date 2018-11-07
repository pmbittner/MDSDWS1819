package edu.mdsd.mpl.validation;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import edu.mdsd.mpl.Operation;
import edu.mdsd.mpl.Variable;
import edu.mdsd.mpl.VariableDeclaration;

public class UniqueVariableNamesConstraint extends AbstractModelConstraint {
	private static Variable getVarWithSameName(Variable variable, List<VariableDeclaration> decs) {
		String variableName = variable.getName();
		
		for (VariableDeclaration otherVariableDeclaration : decs) {
			Variable otherVariable = otherVariableDeclaration.getVariable();
			if (variable != otherVariable && variableName.equals(otherVariable.getName()))
				return otherVariable;
		}
		
		return null;
	}
	
	@Override
	public IStatus validate(IValidationContext context) {
		Variable variable = (Variable) context.getTarget();
		VariableDeclaration variableDeclaration = (VariableDeclaration) variable.eContainer();
		Operation routine = (Operation) variableDeclaration.eContainer();

		Variable duplicate = getVarWithSameName(variable, routine.getVariableDeclarations());
		if (duplicate == null)
			duplicate = getVarWithSameName(variable, routine.getParameters());
		
		if (duplicate != null) {
			return context.createFailureStatus(variable.getName());
		}
		
		return context.createSuccessStatus();
	}
}
