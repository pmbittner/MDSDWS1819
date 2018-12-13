package edu.mdsd.mpl.compiler.mil;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.mdsd.mil.MILModel;
import edu.mdsd.mpl.AddExpression;
import edu.mdsd.mpl.Assignment;
import edu.mdsd.mpl.AssignmentStatement;
import edu.mdsd.mpl.Block;
import edu.mdsd.mpl.Comparison;
import edu.mdsd.mpl.DivExpression;
import edu.mdsd.mpl.ExpressionStatement;
import edu.mdsd.mpl.ForLoop;
import edu.mdsd.mpl.IfStatement;
import edu.mdsd.mpl.LiteralValue;
import edu.mdsd.mpl.MPLModel;
import edu.mdsd.mpl.MultExpression;
import edu.mdsd.mpl.Operation;
import edu.mdsd.mpl.ParenthesisExpression;
import edu.mdsd.mpl.Program;
import edu.mdsd.mpl.ReturnStatement;
import edu.mdsd.mpl.SubExpression;
import edu.mdsd.mpl.TraceStatement;
import edu.mdsd.mpl.UnaryMinusExpression;
import edu.mdsd.mpl.VariableDeclaration;
import edu.mdsd.mpl.VariableReference;
import edu.mdsd.mpl.WhileLoop;
import edu.mdsd.mpl.compiler.mil.compilers.AssignmentCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ComparisonCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.VariableDeclarationCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.expression.atomic.LiteralValueCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.expression.atomic.VariableReferenceCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.expression.binary.AddExpressionCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.expression.binary.DivExpressionCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.expression.binary.MultExpressionCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.expression.binary.SubExpressionCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.expression.unary.ParenthesisExpressionCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.expression.unary.UnaryMinusExpressionCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.operation.ProgramCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.statement.AssignmentStatementCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.statement.BlockCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.statement.ExpressionStatementCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.statement.ForLoopCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.statement.IfStatementCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.statement.ReturnStatementCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.statement.TraceStatementCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.statement.WhileLoopCompiler;
import edu.mdsd.mpl.util.EcoreMILCreator;
import utils.Output;


public class MPL2MILCompiler {
	private Output output;
	
	private int idCounter;
	
	@SuppressWarnings("rawtypes")
	private Map<Class, ElementCompiler> compilers;
	
	public void initialize() {
		compilers = new HashMap<>();
		idCounter = 0;
		
		// Operations
		registerCompiler(Program.class, new ProgramCompiler());
		
		// Arbitrary
		registerCompiler(VariableDeclaration.class, new VariableDeclarationCompiler());
		registerCompiler(Assignment.class, new AssignmentCompiler());
		registerCompiler(Comparison.class, new ComparisonCompiler());
		
		// Statements
		registerCompiler(Block.class, new BlockCompiler());
		registerCompiler(AssignmentStatement.class, new AssignmentStatementCompiler());
		registerCompiler(TraceStatement.class, new TraceStatementCompiler());
		registerCompiler(ReturnStatement.class, new ReturnStatementCompiler());
		registerCompiler(IfStatement.class, new IfStatementCompiler());
		registerCompiler(WhileLoop.class, new WhileLoopCompiler());
		registerCompiler(ForLoop.class, new ForLoopCompiler());
		registerCompiler(ExpressionStatement.class, new ExpressionStatementCompiler());
		
		// Expressions
		registerCompiler(LiteralValue.class, new LiteralValueCompiler());
		//registerCompiler(OperationCall.class, new OperationCallCompiler());
		registerCompiler(VariableReference.class, new VariableReferenceCompiler());
		
		registerCompiler(AddExpression.class, new AddExpressionCompiler());
		registerCompiler(SubExpression.class, new SubExpressionCompiler());
		registerCompiler(MultExpression.class, new MultExpressionCompiler());
		registerCompiler(DivExpression.class, new DivExpressionCompiler());
		
		registerCompiler(ParenthesisExpression.class, new ParenthesisExpressionCompiler());
		registerCompiler(UnaryMinusExpression.class, new UnaryMinusExpressionCompiler());
	}
	
	private <T> void registerCompiler(Class<T> cls, ElementCompiler<T> compiler) {
		compilers.put(cls, compiler);
	}

	public MILModel compile(MPLModel mpl) {
		Compilation compilation = new Compilation(new EcoreMILCreator());
		
		compile(mpl.getProgram(), compilation);
		
		for (Operation op : mpl.getOperations()) {
			compile(op, compilation);
		}
		
		return compilation.getMILModel();
	}

	public <T> void compile(T element, Compilation compilation) {
		if (element == null) {
			out().warn("Element to compile was null.");
			return;
		}
		
		getCompiler(element).compileUntyped(this, compilation, element);
	}
	
	public void setOutput(Output output) {
		this.output = output;
	}
	
	public Output out() {
		return output;
	}
	
	public String generateUniqueName() {
		return "" + idCounter++;
	}
	
	@SuppressWarnings("unchecked")
	private <T> ElementCompiler<T> getCompiler(T element) {
		ElementCompiler<T> compiler = null;
		
		for (@SuppressWarnings("rawtypes") Entry<Class, ElementCompiler> kv : compilers.entrySet()) {
			@SuppressWarnings("rawtypes")
			Class type = kv.getKey();
			if (type.isInstance(element)) {
				if (compiler != null) {
					throw new RuntimeException(
							"[MPL2MILCompiler.getCompiler] Multiple possible compilers for type "
					+ element.getClass().getSimpleName()
					+ " found! (" + compiler.getClass().getSimpleName()
					+ " and "
					+ kv.getValue().getClass().getSimpleName()
					+ ")");
				}
				
				compiler = kv.getValue();
			}
		}
		
		if (compiler != null)
			return compiler;
		
		throw new UnsupportedOperationException("Unsupported type: " + element.getClass());
	}
}
