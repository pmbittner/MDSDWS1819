package edu.mdsd.mpl.compiler.mil.compilers.statement;

import edu.mdsd.mil.JumpMarker;
import edu.mdsd.mpl.WhileLoop;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;
import edu.mdsd.mpl.util.MILCreator;

public class WhileLoopCompiler extends ElementCompiler<WhileLoop> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, WhileLoop element) {
		String salt = compiler.generateUniqueName();
		MILCreator factory = compilation.getMILCreator();
		
		JumpMarker whileBegin = compilation.getOrCreateJumpMarker("While_Begin_" + salt);
		JumpMarker whileEnd   = compilation.getOrCreateJumpMarker("While_End_" + salt);
		
		compilation.add(whileBegin);
		compiler.compile(element.getCondition(), compilation);
		compilation.add(factory.createNegateInstruction());
		compilation.add(factory.createConditionalJumpInstruction(whileEnd));
		compiler.compile(element.getBlock(), compilation);
		compilation.add(factory.createJumpInstruction(whileBegin));
		compilation.add(whileEnd);
	}

}
