package edu.mdsd.mpl.compiler.mil.compilers.expression.atomic;

import edu.mdsd.mpl.VariableReference;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;

public class VariableReferenceCompiler extends ElementCompiler<VariableReference> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, VariableReference element) {
		compilation.addLoadInstruction(element.getVariable());
	}

}
