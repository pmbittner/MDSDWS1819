package edu.mdsd.mpl.compiler.mil.compilers.expression.binary;

import edu.mdsd.mpl.DivExpression;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;

public class DivExpressionCompiler extends BinaryExpressionCompiler<DivExpression> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, DivExpression element) {
		super.compile(compiler, compilation, element);
		compilation.add(compilation.getMILCreator().createDivInstruction());
	}

}
