package edu.mdsd.mpl.compiler.mil.compilers.expression.binary;

import edu.mdsd.mpl.BinaryExpression;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;

public abstract class BinaryExpressionCompiler<T extends BinaryExpression> extends ElementCompiler<T> {
	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, T element) {
		compiler.compile(element.getOperand1(), compilation);
		compiler.compile(element.getOperand2(), compilation);
	}
}
