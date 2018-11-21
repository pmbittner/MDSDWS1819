package edu.mdsd.mil.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import edu.mdsd.mil.AddInstruction;
import edu.mdsd.mil.ConstantInteger;
import edu.mdsd.mil.Instruction;
import edu.mdsd.mil.JumpInstruction;
import edu.mdsd.mil.JumpMarker;
import edu.mdsd.mil.LoadInstruction;
import edu.mdsd.mil.MILModel;
import edu.mdsd.mil.RegisterReference;
import edu.mdsd.mil.Statement;
import edu.mdsd.mil.StoreInstruction;
import edu.mdsd.mil.Value;
import edu.mdsd.mil.interpreter.instruction.JumpInterpreter;
import edu.mdsd.mil.interpreter.instruction.LoadInterpreter;
import edu.mdsd.mil.interpreter.instruction.operation.AddInterpreter;
import edu.mdsd.mil.interpreter.instruction.operation.StoreInterpreter;

public class MILInterpreter {
	@SuppressWarnings("rawtypes")
	private Map<Class, InstructionInterpreter> instructionInterpreters;
	
	// Interpretation variables
	private int programCounter = 0;
	
	List<Instruction> instructions;
	Map<String, Integer> jumpMarkers;
	
	OperandStack operandStack;
	VariableRegister variableRegister;
	
	
	@SuppressWarnings("rawtypes")
	public MILInterpreter() {
		operandStack = new OperandStack();
		variableRegister = new VariableRegister();
		instructions = null;
		jumpMarkers = null;
		
		instructionInterpreters = new HashMap<Class, InstructionInterpreter>();
		instructionInterpreters.put(AddInstruction.class, new AddInterpreter());
		instructionInterpreters.put(StoreInstruction.class, new StoreInterpreter());
		instructionInterpreters.put(LoadInstruction.class, new LoadInterpreter());
		instructionInterpreters.put(JumpInstruction.class, new JumpInterpreter());
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
			interpret(currentInstruction);
			++programCounter;
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
				jumpMarkers.put(((JumpMarker) statement).getName(), instructionPosition);
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
	public void jumpTo(String label) {
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