package edu.mdsd.mpl.validation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import edu.mdsd.mpl.Expression;
import edu.mdsd.mpl.ForLoop;
import edu.mdsd.mpl.LiteralValue;

public class WellDeclaredForLoop extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext context) {
		ForLoop loop = (ForLoop) context.getTarget();
		
		Integer tryFrom = evaluate(loop.getIndex().getRightHandSide());
		if (tryFrom != null) {
			int from = tryFrom;
			int to = loop.getUpperBound();
			
			//String s = loop.getStep();
			
			if (
					//s.contains("Down")
					!loop.isIncrement()
					) {
				 if (from < to) {
					 return context.createFailureStatus("greater", "Down To");
				 }
			} else if (from > to) {
				return context.createFailureStatus("smaller", "To");
			}
		}
		
		return context.createSuccessStatus();
	}

	private Integer evaluate(Expression expr) {
		if (expr instanceof LiteralValue)
			return ((LiteralValue) expr).getRawValue();
		return null;
	}

}
