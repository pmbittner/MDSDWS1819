package edu.mdsd.mpl.compiler.mil.compilers.statement;

import edu.mdsd.mpl.ExpressionStatement;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;

public class ExpressionStatementCompiler extends ElementCompiler<ExpressionStatement> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, ExpressionStatement element) {
		compiler.compile(element.getExpression(), compilation);
		compilation.addStoreInstruction();
	}

}
