package edu.mdsd.mil.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import edu.mdsd.mil.*;
import edu.mdsd.mil.interpreter.instruction.*;
import edu.mdsd.mil.interpreter.instruction.operation.binary.*;
import edu.mdsd.mil.interpreter.instruction.operation.binary.comparison.*;
import edu.mdsd.mil.interpreter.instruction.operation.unary.*;

public class MILInterpreter {
	@SuppressWarnings("rawtypes")
	private Map<Class, InstructionInterpreter> instructionInterpreters;
	
	// Interpretation variables
	private int programCounter = 0;
	
	List<Instruction> instructions;
	Map<JumpMarker, Integer> jumpMarkers;
	
	OperandStack operandStack;
	VariableRegister variableRegister;
	
	
	@SuppressWarnings({ "rawtypes", "serial" })
	public MILInterpreter() {
		operandStack = new OperandStack();
		variableRegister = new VariableRegister();
		instructions = null;
		jumpMarkers = null;
		
		instructionInterpreters = new HashMap<Class, InstructionInterpreter>() {
			{
				put(LoadInstruction.class, new LoadInterpreter());
				put(PrintInstruction.class, new PrintInterpreter());
				put(JumpInstruction.class, new JumpInterpreter());

				put(ConditionalJumpInstruction.class, new ConditionalJumpInterpreter());
				put(NegateInstruction.class, new NegateInterpreter());
				put(StoreInstruction.class, new StoreInterpreter());
				put(YieldInstruction.class, new YieldInterpreter());
				
				put(AddInstruction.class, new AddInterpreter());
				put(SubInstruction.class, new SubInterpreter());
				put(MultInstruction.class, new MultInterpreter());
				put(DivInstruction.class, new DivInterpreter());

				put(EqualsComparison.class, new EqualsInterpreter());
				put(GreaterEqualsComparison.class, new GreaterEqualsInterpreter());
				put(GreaterThanComparison.class, new GreaterThanInterpreter());
				put(LowerEqualsComparison.class, new LowerEqualsInterpreter());
				put(LowerThanComparison.class, new LowerThanInterpreter());
				put(NotEqualsComparison.class, new NotEqualsInterpreter());
			}
		};
	}
	
	public void initialize() {
		operandStack.initialize();
		variableRegister.initialize();
		programCounter = 0;
	}
	
	public Map<String, Integer> interpret(MILModel model) {
		parseStatements(model.getStatements());
	
		while (programCounter < instructions.size()) {
			Instruction currentInstruction = instructions.get(programCounter);
			++programCounter;
			
			interpret(currentInstruction);
		}
		
		return variableRegister.getRegister();
	}
	
	private void parseStatements(List<Statement> statements) {
		int instructionPosition = 0;
		
		instructions = new ArrayList<>();
		jumpMarkers = new HashMap<>();
		
		for (Statement statement : statements) {
			if (statement instanceof Instruction) {
				instructions.add((Instruction)statement);
				++instructionPosition;
			} else if (statement instanceof JumpMarker) {
				jumpMarkers.put((JumpMarker) statement, instructionPosition);
			} else {
				throw new UnsupportedOperationException();
			}
		}
	}
	
	private void interpret(Instruction instruction) {
		for (@SuppressWarnings("rawtypes") Entry<Class, InstructionInterpreter> kv : instructionInterpreters.entrySet()) {
			@SuppressWarnings("rawtypes")
			Class type = kv.getKey();
			if (type.isInstance(instruction)) {
				kv.getValue().interpretUntyped(this, type.cast(instruction));
				return;
			}
		}
		
		throw new UnsupportedOperationException();
	}
	
	// public
	public void jumpTo(JumpMarker label) {
		if (!jumpMarkers.containsKey(label))
			throw new IllegalArgumentException("The jump label " + label + " could not be found!");
		
		programCounter = jumpMarkers.get(label);
	}
	
	public int getRawValue(Value value) {
		if (value instanceof ConstantInteger) {
			return ((ConstantInteger) value).getRawValue();
		}
		
		if (value instanceof RegisterReference) {
			return variableRegister.get(((RegisterReference) value).getAddress());
		}
		
		throw new UnsupportedOperationException();
	}
	
	public VariableRegister getVariableRegister() {
		return variableRegister;
	}
	
	public OperandStack getOperandStack() {
		return operandStack;
	}
}
