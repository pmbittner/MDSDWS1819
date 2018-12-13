package edu.mdsd.mpl.compiler.mil.compilers.expression.binary;

import edu.mdsd.mpl.AddExpression;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;

public class AddExpressionCompiler extends BinaryExpressionCompiler<AddExpression> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, AddExpression element) {
		super.compile(compiler, compilation, element);
		compilation.add(compilation.getMILCreator().createAddInstruction());
	}

}
