package edu.mdsd.mpl.compiler.mil.compilers;

import edu.mdsd.mpl.Assignment;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;

public class AssignmentCompiler extends ElementCompiler<Assignment> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, Assignment element) {
		compiler.compile(element.getRightHandSide(), compilation);
		compilation.addStoreInstruction(element.getLeftHandSide().getVariable());
	}

}
