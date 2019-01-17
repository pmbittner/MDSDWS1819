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
import utils.MathUtils;
import utils.Output;
import utils.SystemOutput;

public class MILInterpreter {
	// Interpreters for each instruction type
	@SuppressWarnings("rawtypes")
	private Map<Class, InstructionInterpreter> instructionInterpreters;
	
	// Interpretation variables
	private int programPosition = 0;
	
	private List<Instruction> instructions;
	private Map<JumpMarker, Integer> jumpMarkers;
	
	private OperandStack operandStack;
	private CallStack callStack;
	
	private Output output;
	
	
	@SuppressWarnings({ "rawtypes", "serial" })
	public MILInterpreter() {
		output = new SystemOutput(this);
		
		operandStack = new OperandStack();
		callStack = new CallStack(output);
		instructions = new ArrayList<>();
		jumpMarkers = new HashMap<>();
		
		instructionInterpreters = new HashMap<Class, InstructionInterpreter>() {
			{
				put(LoadInstruction.class, new LoadInterpreter());
				put(PrintInstruction.class, new PrintInterpreter());
				put(JumpInstruction.class, new JumpInterpreter());
				put(CallInstruction.class, new CallInterpreter());
				put(ReturnInstruction.class, new ReturnInterpreter());

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
		callStack.initialize();
		instructions.clear();
		jumpMarkers.clear();
		
		// We define 0 as the return address of the whole program.
		callStack.push(0);
		
		programPosition = 0;
	}
	
	public Map<String, Integer> interpret(MILModel model) {
		parseStatements(model.getStatements());
	
		while (programPosition < instructions.size()) {
			Instruction currentInstruction = instructions.get(programPosition);
			++programPosition;
			
			interpret(currentInstruction);
		}
		
		return getVariableRegister().toMap();
	}
	
	private void parseStatements(List<Statement> statements) {
		int instructionPosition = 0;
		
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
				kv.getValue().interpretUntyped(this, instruction);
				return;
			}
		}
		
		throw new UnsupportedOperationException();
	}
	
	/// public ///
	
	public void crash() {
		out().err("Aborting execution!");
		jumpTo(instructions.size());
	}
	
	public void jumpTo(int position) {
		if (position < 0)
			out().warn("A jump to position " + position + " was requested, but program position smaller than 0 do not exist.");
		else if (position > instructions.size())
			out().warn("A jump to position " + position + ", that is outside the program.");
		
		this.programPosition = MathUtils.clamp(position, 0, instructions.size());
	}
	
	public void jumpTo(JumpMarker label) {
		if (!jumpMarkers.containsKey(label))
			throw new IllegalArgumentException("The jump label " + label.getName() + " could not be found!");
		
		jumpTo(jumpMarkers.get(label));
	}
	
	public int getCurrentPosition() {
		return programPosition;
	}
	
	public int getRawValue(Value value) {
		if (value instanceof ConstantInteger) {
			return ((ConstantInteger) value).getRawValue();
		}
		
		if (value instanceof RegisterReference) {
			return getVariableRegister().get(((RegisterReference) value).getAddress());
		}
		
		throw new UnsupportedOperationException();
	}
	
	public VariableRegister getVariableRegister() {
		return callStack.peek().getVariableRegister();
	}
	
	public OperandStack getOperandStack() {
		return operandStack;
	}
	
	public CallStack getCallStack() {
		return callStack;
	}
	
	public void setOutput(Output output) {
		this.output = output;
	}
	
	public Output out() {
		return output;
	}
}
