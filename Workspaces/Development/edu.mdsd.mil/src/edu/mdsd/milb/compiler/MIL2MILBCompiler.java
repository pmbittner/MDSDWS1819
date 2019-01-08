package edu.mdsd.milb.compiler;

import java.util.List;

import edu.mdsd.mil.Instruction;
import edu.mdsd.mil.JumpMarker;
import edu.mdsd.mil.MILModel;
import edu.mdsd.mil.Statement;
import utils.Output;

public class MIL2MILBCompiler {
	private Output output;
	private InstructionCompiler instructionCompiler;
	
	public MIL2MILBCompiler() {
		instructionCompiler = new InstructionCompiler();
	}
	
	public void initialize() {
		instructionCompiler.initialize();
	}
	
	public void compile(MILModel mil, String exportPath) {
		out().println("Compiling to binary format");
		
		Milbe milb = new Milbe();
		
		List<Statement> statements = mil.getStatements();
		
		for (Statement statement : statements) {
			compile(statement, milb);
		}
		
		{
			byte[] bytes = milb.getBytesPrimitive();
	
			out().println();
			for (byte b : bytes)
				out().print(String.format("%03d ", b));
			out().println();
		}
		
		MilbeExporter exporter = new MilbeExporter();
		exporter.writeToFile(milb, exportPath, output);
	}

	private void compile(Statement statement, Milbe milb) {
		if (statement instanceof Instruction) {
			instructionCompiler.compileInstruction((Instruction) statement, milb);
		} else if (statement instanceof JumpMarker) {
			milb.registerJumpMarker((JumpMarker) statement);
		} else
			throw new UnsupportedOperationException();
	}
	
	public void setOutput(Output output) {
		this.output = output;
	}
	
	public Output out() {
		return output;
	}
}
