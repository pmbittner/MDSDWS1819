package edu.mdsd.mpl.compiler.mil.compilers;

import edu.mdsd.mpl.Expression;
import edu.mdsd.mpl.VariableDeclaration;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;

public class VariableDeclarationCompiler extends ElementCompiler<VariableDeclaration> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, VariableDeclaration element) {
		Expression initialValue = element.getInitialValue();
		
		if (initialValue == null) {
			compilation.addLoadInstruction(0);
		} else {
			compiler.compile(initialValue, compilation);
		}
		
		compilation.addStoreInstruction(element.getVariable());
	}

}
