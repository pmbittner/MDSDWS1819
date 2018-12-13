package edu.mdsd.mpl.compiler.mil.compilers.expression.binary;

import edu.mdsd.mpl.SubExpression;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;

public class SubExpressionCompiler extends BinaryExpressionCompiler<SubExpression> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, SubExpression element) {
		super.compile(compiler, compilation, element);
		compilation.add(compilation.getMILCreator().createSubInstruction());
	}

}
