package edu.mdsd.mpl.compiler.mil.compilers.expression.unary;

import edu.mdsd.mpl.UnaryExpression;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;

public abstract class UnaryExpressionCompiler<T extends UnaryExpression> extends ElementCompiler<T> {
	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, T element) {
		compiler.compile(element.getOperand(), compilation);
	}
}
