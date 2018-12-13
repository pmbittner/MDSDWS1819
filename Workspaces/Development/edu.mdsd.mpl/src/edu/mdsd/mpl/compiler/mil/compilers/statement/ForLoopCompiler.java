package edu.mdsd.mpl.compiler.mil.compilers.statement;

import edu.mdsd.mil.JumpMarker;
import edu.mdsd.mpl.ForLoop;
import edu.mdsd.mpl.Variable;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;
import edu.mdsd.mpl.util.MILCreator;

public class ForLoopCompiler extends ElementCompiler<ForLoop> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, ForLoop element) {
		String salt = compiler.generateUniqueName();
		MILCreator factory = compilation.getMILCreator();
		
		Variable index = element.getIndex().getLeftHandSide().getVariable();
		
		JumpMarker forBegin = factory.createJumpMarker("For_Begin_" + salt);
		JumpMarker forEnd   = factory.createJumpMarker("For_End_" + salt);
		
		compiler.compile(element.getIndex(), compilation);
		compilation.add(forBegin);
		
		// Condition
		compilation.addLoadInstruction(index);
		compilation.addLoadInstruction(element.getUpperBound());
		if (element.isIncrement()) {
			compilation.add(factory.createLowerEqualsComparison());
		} else {
			compilation.add(factory.createGreaterEqualsComparison());
		}
		
		compilation.add(factory.createNegateInstruction());
		compilation.add(factory.createConditionalJumpInstruction(forEnd));
		
		compiler.compile(element.getBlock(), compilation);
		
		compilation.addLoadInstruction(index);
		compilation.addLoadInstruction(1);
		if (element.isIncrement()) {
			compilation.add(factory.createAddInstruction());
		} else {
			compilation.add(factory.createSubInstruction());
		}
		compilation.addStoreInstruction(index);
		
		compilation.add(factory.createJumpInstruction(forBegin));
		
		compilation.add(forEnd);
		
		// TODO: Find a prettier solution for this
		compilation.addLoadInstruction(element.getUpperBound());
		compilation.addStoreInstruction(index);
	}

}
