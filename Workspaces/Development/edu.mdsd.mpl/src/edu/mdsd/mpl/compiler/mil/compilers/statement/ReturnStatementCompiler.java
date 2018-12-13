package edu.mdsd.mpl.compiler.mil.compilers.statement;

import edu.mdsd.mpl.ReturnStatement;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;

public class ReturnStatementCompiler extends ElementCompiler<ReturnStatement> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, ReturnStatement element) {
		compiler.compile(element.getValue(), compilation);
		compilation.add(compilation.getMILCreator().createReturnInstruction());
	}

}
