package edu.mdsd.mpl.compiler.mil.compilers.expression.binary;

import edu.mdsd.mpl.MultExpression;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;

public class MultExpressionCompiler extends BinaryExpressionCompiler<MultExpression> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, MultExpression element) {
		super.compile(compiler, compilation, element);
		compilation.add(compilation.getMILCreator().createMultInstruction());
	}

}
