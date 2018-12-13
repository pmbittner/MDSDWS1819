package edu.mdsd.mpl.compiler.mil.compilers.statement;

import edu.mdsd.mpl.AssignmentStatement;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;

public class AssignmentStatementCompiler extends ElementCompiler<AssignmentStatement> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, AssignmentStatement element) {
		compiler.compile(element.getAssignment(), compilation);
	}

}
