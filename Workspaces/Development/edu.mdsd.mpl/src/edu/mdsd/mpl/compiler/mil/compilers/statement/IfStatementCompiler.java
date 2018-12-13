package edu.mdsd.mpl.compiler.mil.compilers.statement;

import edu.mdsd.mil.JumpMarker;
import edu.mdsd.mpl.Block;
import edu.mdsd.mpl.IfStatement;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;

public class IfStatementCompiler extends ElementCompiler<IfStatement> {
	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, IfStatement element) {
		String salt = compiler.generateUniqueName();
		Block elseBlock = element.getElse();
		
		// <condition>
		compiler.compile(element.getCondition(), compilation);
		// neg
		compilation.add(compilation.getMILCreator().createNegateInstruction());
		
		JumpMarker endifMarker = compilation.getMILCreator().createJumpMarker("EndIf_" + salt);
		JumpMarker elseMarker = elseBlock == null ? endifMarker : compilation.getMILCreator().createJumpMarker("Else_" + salt);
		
		// jpc else
		compilation.add(compilation.getMILCreator().createConditionalJumpInstruction(elseMarker));
		// <Then>
		compiler.compile(element.getThen(), compilation);
		// jmp EndIf
		compilation.add(compilation.getMILCreator().createJumpInstruction(endifMarker));
		
		if (elseBlock != null) {
			// Else:
			compilation.add(elseMarker);
			// <Else>
			compiler.compile(elseBlock, compilation);
		}
		
		// EndIf:
		compilation.add(endifMarker);
	}
}
