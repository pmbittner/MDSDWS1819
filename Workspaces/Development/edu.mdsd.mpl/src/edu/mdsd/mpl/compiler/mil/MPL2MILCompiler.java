package edu.mdsd.mpl.compiler.mil;

import edu.mdsd.mil.MILModel;
import edu.mdsd.mpl.MPLModel;
import edu.mdsd.mpl.Operation;
import edu.mdsd.mpl.Program;
import utils.Output;


public class MPL2MILCompiler {
	private Output output;

	public MILModel compile(MPLModel mpl) {
		Compilation compilation = new Compilation(new EcoreMILCreator());
		
		compileProgram(mpl.getProgram(), compilation);
		
		for (Operation op : mpl.getOperations()) {
			compileOperation(op, compilation);
		}
		
		return compilation.getMILModel();
	}

	private void compileProgram(Program program, Compilation compilation) {
		out().println("[MPL2MILCompiler.compileProgram] " + program.getName());
	}
	
	private void compileOperation(Operation operation, Compilation compilation) {
		out().println("[MPL2MILCompiler.compileOperation] " + operation.getName());
	}
	
	public void setOutput(Output output) {
		this.output = output;
	}
	
	public Output out() {
		return output;
	}
}
