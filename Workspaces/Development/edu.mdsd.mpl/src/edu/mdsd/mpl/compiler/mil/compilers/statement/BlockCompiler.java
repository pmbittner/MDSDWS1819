package edu.mdsd.mpl.compiler.mil.compilers.statement;

import edu.mdsd.mpl.Block;
import edu.mdsd.mpl.Statement;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;

public class BlockCompiler extends ElementCompiler<Block> {
	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, Block element) {
		for (Statement s : element.getStatements()) {
			compiler.compile(s, compilation);
		}
	}
}
