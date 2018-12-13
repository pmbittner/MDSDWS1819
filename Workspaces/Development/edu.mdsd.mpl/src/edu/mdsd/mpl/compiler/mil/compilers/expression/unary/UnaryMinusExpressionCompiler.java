package edu.mdsd.mpl.compiler.mil.compilers.expression.unary;

import edu.mdsd.mpl.UnaryMinusExpression;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;

public class UnaryMinusExpressionCompiler extends UnaryExpressionCompiler<UnaryMinusExpression> {
	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, UnaryMinusExpression element) {
		compilation.addLoadInstruction(0);
		super.compile(compiler, compilation, element);
		compilation.add(compilation.getMILCreator().createSubInstruction());
	}
}
