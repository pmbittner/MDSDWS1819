package edu.mdsd.mpl.compiler.mil.compilers.operation;

import edu.mdsd.mpl.Program;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;

public class ProgramCompiler extends OperationCompiler<Program> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, Program element) {
		super.compile(compiler, compilation, element);
		compilation.add(compilation.getMILCreator().createJumpInstruction(compilation.getOrCreateJumpMarker("EndProgram")));
	}

}
