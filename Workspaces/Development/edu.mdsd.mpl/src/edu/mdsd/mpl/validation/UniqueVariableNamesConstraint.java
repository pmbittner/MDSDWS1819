package edu.mdsd.mpl.validation;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import edu.mdsd.mpl.Program;
import edu.mdsd.mpl.Variable;
import edu.mdsd.mpl.VariableDeclaration;

public class UniqueVariableNamesConstraint extends AbstractModelConstraint {
	@Override
	public IStatus validate(IValidationContext context) {
		Variable variable = (Variable) context.getTarget();
		String variableName = variable.getName();
		
		VariableDeclaration variableDeclaration = (VariableDeclaration) variable.eContainer();
		Program program = (Program) variableDeclaration.eContainer();
		
		List<VariableDeclaration> variableDeclarations = program.getVariableDeclarations();
		
		for (VariableDeclaration otherVariableDeclaration : variableDeclarations) {
			Variable otherVariable = otherVariableDeclaration.getVariable();
			String otherVariableName = otherVariable.getName();
			
			if (variable != otherVariable) {
				if (variableName.equals(otherVariableName)) {
					return context.createFailureStatus(variableName);
				}
			}
		}
		
		return context.createSuccessStatus();
	}
}
