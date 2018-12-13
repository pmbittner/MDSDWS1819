package edu.mdsd.mpl.compiler.mil.compilers.operation;

import edu.mdsd.mpl.Operation;
import edu.mdsd.mpl.VariableDeclaration;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;

public abstract class OperationCompiler<T extends Operation> extends ElementCompiler<T> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, T element) {
		// create globally unique function name: Currently this is the function name itself
		compilation.add(compilation.getOrCreateJumpMarker(element));
		
		for (VariableDeclaration vardec : element.getVariableDeclarations()) {
			compiler.compile(vardec, compilation);
		}
		
		compiler.compile(element.getBlock(), compilation);
	}
}
