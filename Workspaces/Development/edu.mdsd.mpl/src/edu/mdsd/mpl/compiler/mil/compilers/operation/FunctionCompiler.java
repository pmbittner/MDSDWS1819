package edu.mdsd.mpl.compiler.mil.compilers.operation;

import edu.mdsd.mpl.Function;
import edu.mdsd.mpl.VariableDeclaration;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;

public class FunctionCompiler extends OperationCompiler<Function> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, Function element) {
		super.compile(compiler, compilation, element);
		
		// pop parameters from operand stack
		for (VariableDeclaration parameter : element.getParameters()) {
			compilation.addStoreInstruction(parameter.getVariable());
		}
		
	}

}
