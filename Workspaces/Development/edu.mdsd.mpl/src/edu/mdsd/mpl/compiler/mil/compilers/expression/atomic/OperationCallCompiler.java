package edu.mdsd.mpl.compiler.mil.compilers.expression.atomic;

import java.util.List;
import java.util.ListIterator;

import edu.mdsd.mpl.Expression;
import edu.mdsd.mpl.Operation;
import edu.mdsd.mpl.OperationCall;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;

public class OperationCallCompiler extends ElementCompiler<OperationCall> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, OperationCall element) {
		Operation operation = element.getOperation();
		
		if (operation == null || operation.getName() == null) {
			compiler.error("Calling undefined function!");
		}
		
		// put parameters on operand stack
		List<Expression> parameters = element.getParameters();
		ListIterator<Expression> parameterIterator = parameters.listIterator(parameters.size());

		// Iterate in reverse.
		while(parameterIterator.hasPrevious()) {
			Expression parameter = parameterIterator.previous();
			compiler.compile(parameter, compilation);
		}
		
		// jump to function
		compilation.add(
				compilation.getMILCreator().createCallInstruction(
						compilation.getOrCreateJumpMarker(element.getOperation())));
	}

}
