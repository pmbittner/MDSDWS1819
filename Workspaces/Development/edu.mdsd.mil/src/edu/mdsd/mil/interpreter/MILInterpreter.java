package edu.mdsd.mil.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import edu.mdsd.mil.AddInstruction;
import edu.mdsd.mil.ConstantInteger;
import edu.mdsd.mil.Instruction;
import edu.mdsd.mil.LoadInstruction;
import edu.mdsd.mil.MILModel;
import edu.mdsd.mil.RegisterReference;
import edu.mdsd.mil.StoreInstruction;
import edu.mdsd.mil.Value;
import edu.mdsd.mil.interpreter.instruction.AddInterpreter;
import edu.mdsd.mil.interpreter.instruction.LoadInterpreter;
import edu.mdsd.mil.interpreter.instruction.StoreInterpreter;

public class MILInterpreter {
	private int programCounter = 0;
	
	OperandStack operandStack;
	VariableRegister variableRegister;
	
	@SuppressWarnings("rawtypes")
	private Map<Class, InstructionInterpreter> instructionInterpreters;
	
	@SuppressWarnings("rawtypes")
	public MILInterpreter() {
		operandStack = new OperandStack();
		variableRegister = new VariableRegister();
		
		instructionInterpreters = new HashMap<Class, InstructionInterpreter>();
		instructionInterpreters.put(AddInstruction.class, new AddInterpreter());
		instructionInterpreters.put(StoreInstruction.class, new StoreInterpreter());
		instructionInterpreters.put(LoadInstruction.class, new LoadInterpreter());
	}
	
	public void initialize() {
		operandStack.initialize();
		variableRegister.initialize();
		programCounter = 0;
	}
	
	public Map<String, Integer> interpret(MILModel model) {
		List<Instruction> instructions = model.getInstructions();
	
		while (programCounter < instructions.size()) {
			Instruction currentInstruction = instructions.get(programCounter);
			interpret(currentInstruction);
			++programCounter;
		}
		
		return variableRegister.getRegister();
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
