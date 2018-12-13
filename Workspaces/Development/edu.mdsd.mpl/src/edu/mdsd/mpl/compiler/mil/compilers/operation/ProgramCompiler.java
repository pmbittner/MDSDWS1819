package edu.mdsd.mpl.compiler.mil.compilers.operation;

import edu.mdsd.mpl.Program;
import edu.mdsd.mpl.VariableDeclaration;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;

public class ProgramCompiler extends ElementCompiler<Program> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, Program element) {
		for (VariableDeclaration vardec : element.getVariableDeclarations()) {
			compiler.compile(vardec, compilation);
		}
		
		compiler.compile(element.getBlock(), compilation);
	}

}
