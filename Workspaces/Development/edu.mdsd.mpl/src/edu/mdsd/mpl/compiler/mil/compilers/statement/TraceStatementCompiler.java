package edu.mdsd.mpl.compiler.mil.compilers.statement;

import edu.mdsd.mpl.TraceStatement;
import edu.mdsd.mpl.Variable;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;

public class TraceStatementCompiler extends ElementCompiler<TraceStatement> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, TraceStatement element) {
		Variable variable = element.getVariableReference().getVariable();
		
		compilation.add(compilation.getMILCreator().createPrintInstruction(variable.getName() + " = "));
		compilation.addLoadInstruction(variable);
		compilation.add(compilation.getMILCreator().createYieldInstruction());
	}

}
