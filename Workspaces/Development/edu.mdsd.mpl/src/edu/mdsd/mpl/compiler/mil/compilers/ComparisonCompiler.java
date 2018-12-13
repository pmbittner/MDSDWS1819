package edu.mdsd.mpl.compiler.mil.compilers;

import edu.mdsd.mil.Statement;
import edu.mdsd.mpl.Comparison;
import edu.mdsd.mpl.compiler.mil.Compilation;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import edu.mdsd.mpl.util.MILCreator;

public class ComparisonCompiler extends ElementCompiler<Comparison> {

	@Override
	protected void compile(MPL2MILCompiler compiler, Compilation compilation, Comparison element) {
		compiler.compile(element.getOperand1(), compilation);
		compiler.compile(element.getOperand2(), compilation);
		
		Statement comparison = null;
		MILCreator factory = compilation.getMILCreator();
		
		switch (element.getOperator()) {
		case "=": {
			comparison = factory.createEqualsComparison();
			break;
		}
		case "<>": {
			comparison = factory.createNotEqualsComparison();
			break;
		}
		case "<": {
			comparison = factory.createLowerThanComparison();
			break;
		}
		case ">": {
			comparison = factory.createGreaterThanComparison();
			break;
		}
		case "<=": {
			comparison = factory.createLowerEqualsComparison();
			break;
		}
		case ">=": {
			comparison = factory.createGreaterEqualsComparison();
			break;
		}
		default: {
			throw new UnsupportedOperationException("The comparions operator '" + element.getOperator() + "' is not supported by the compiler!");
		}
		}
		
		compilation.add(comparison);
	}

}
