package edu.mdsd.mpl.compiler.mil.compilers;

import edu.mdsd.mpl.Program;
import edu.mdsd.mpl.VariableDeclaration;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;

public class ProgramCompiler extends ElementCompiler<Program> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, Program element) {
		compiler.out().println("[ProgramCompiler.compile] " + element.getName());
		
		for (VariableDeclaration vardec : element.getVariableDeclarations()) {
			compiler.compile(vardec, compilation);
		}
		
		compiler.compile(element.getBlock(), compilation);
	}

}
