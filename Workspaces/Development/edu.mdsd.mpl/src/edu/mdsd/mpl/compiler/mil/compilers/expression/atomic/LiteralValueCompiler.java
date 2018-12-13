package edu.mdsd.mpl.compiler.mil.compilers.expression.atomic;

import edu.mdsd.mpl.LiteralValue;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;

public class LiteralValueCompiler extends ElementCompiler<LiteralValue> {
	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, LiteralValue element) {
		compilation.addLoadInstruction(element.getRawValue());
	}
}
