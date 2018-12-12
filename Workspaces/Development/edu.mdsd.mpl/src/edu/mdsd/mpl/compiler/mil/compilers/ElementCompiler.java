package edu.mdsd.mpl.compiler.mil.compilers;

import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;

public abstract class ElementCompiler<T> {
	@SuppressWarnings("unchecked")
	public void compileUntyped(MPL2MILCompiler compiler, Compilation compilation, Object o) {
		compile(compiler, compilation, (T) o);
	}
	
	protected abstract void compile(MPL2MILCompiler compiler, Compilation compilation, T element);
}
