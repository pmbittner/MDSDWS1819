package edu.mdsd.milb.compiler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

import edu.mdsd.mil.AddInstruction;
import edu.mdsd.mil.CallInstruction;
import edu.mdsd.mil.ConditionalJumpInstruction;
import edu.mdsd.mil.ConstantInteger;
import edu.mdsd.mil.DivInstruction;
import edu.mdsd.mil.EqualsComparison;
import edu.mdsd.mil.GreaterEqualsComparison;
import edu.mdsd.mil.GreaterThanComparison;
import edu.mdsd.mil.Instruction;
import edu.mdsd.mil.JumpInstruction;
import edu.mdsd.mil.LoadInstruction;
import edu.mdsd.mil.LowerEqualsComparison;
import edu.mdsd.mil.LowerThanComparison;
import edu.mdsd.mil.MultInstruction;
import edu.mdsd.mil.NegateInstruction;
import edu.mdsd.mil.NotEqualsComparison;
import edu.mdsd.mil.PrintInstruction;
import edu.mdsd.mil.RegisterReference;
import edu.mdsd.mil.ReturnInstruction;
import edu.mdsd.mil.StoreInstruction;
import edu.mdsd.mil.SubInstruction;
import edu.mdsd.mil.Value;
import edu.mdsd.mil.YieldInstruction;

public class InstructionCompiler {
	private static class Delegate<T extends Instruction> {
		BiConsumer<T, Milbe> consumer;
		
		void set(BiConsumer<T, Milbe> consumer) {
			this.consumer = consumer;
		}
		
		@SuppressWarnings("unchecked")
		void apply(Instruction i, Milbe m) {
			consumer.accept((T) i, m);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private Map<Class, Delegate> compilers;
	private StringAvoider<Integer> variableNameTranslator;
	
	private <T extends Instruction> Delegate<T> register(Class<T> cls) {
		Delegate<T> del = new Delegate<T>();
		compilers.put(cls, del);
		return del;
	}
	
	private <T extends Instruction> void register(Class<T> cls, ByteCode code) {
		register(cls).set((i, m) -> m.pushInstruction(code));
	}
	
	public InstructionCompiler() {
		compilers = new HashMap<>();
		variableNameTranslator = new StringAvoider<>(new StringAvoider.UniqueIntGenerator());
	}

	public void compileInstruction(Instruction i, Milbe milb) {
		for (@SuppressWarnings("rawtypes") Entry<Class, Delegate> kv : compilers.entrySet()) {
			@SuppressWarnings("rawtypes")
			Class type = kv.getKey();
			if (type.isInstance(i)) {
				kv.getValue().apply(i, milb);
				return;
			}
		}
	}
	
	public void initialize() {
		compilers.clear();
		
		register(ReturnInstruction.class, ByteCode.RET);
		
		register(NegateInstruction.class, ByteCode.NEG);
		
		register(AddInstruction.class, ByteCode.ADD);
		register(SubInstruction.class, ByteCode.SUB);
		register(MultInstruction.class, ByteCode.MUL);
		register(DivInstruction.class, ByteCode.DIV);
		
		register(EqualsComparison.class, ByteCode.EQ);
		register(NotEqualsComparison.class, ByteCode.NEQ);
		register(LowerThanComparison.class, ByteCode.LT);
		register(LowerEqualsComparison.class, ByteCode.LEQ);
		register(GreaterThanComparison.class, ByteCode.GT);
		register(GreaterEqualsComparison.class, ByteCode.GEQ);
		
		register(YieldInstruction.class, ByteCode.YLD);
		
		register(LoadInstruction.class).set((l, m) -> {
			Value val = l.getValue();
			
			if (val instanceof ConstantInteger) {
				m.pushInstruction(ByteCode.LOD);
				m.pushArgument(((ConstantInteger) val).getRawValue());
			} else if (val instanceof RegisterReference) {
				m.pushInstruction(ByteCode.LDV);
				String variableName = ((RegisterReference) val).getAddress();
				m.pushArgument(variableNameTranslator.translate(variableName));
			}
		});
		
		register(StoreInstruction.class).set((s, m) -> {
			RegisterReference rr = s.getRegisterReference();
			
			if (rr == null) {
				m.pushInstruction(ByteCode.STO);
			} else {
				m.pushInstruction(ByteCode.STT);
				m.pushArgument(variableNameTranslator.translate(rr.getAddress()));
			}
		});
		
		register(JumpInstruction.class).set((j, m) -> {
			m.pushInstruction(ByteCode.JMP);
			m.pushArgument(j.getJumpTarget());
		});
		
		register(ConditionalJumpInstruction.class).set((j, m) -> {
			m.pushInstruction(ByteCode.JPC);
			m.pushArgument(j.getJumpTarget());
		});
		
		register(CallInstruction.class).set((c, m) -> {
			m.pushInstruction(ByteCode.CAL);
			m.pushArgument(c.getJumpTarget());
		});
		
		register(PrintInstruction.class).set((p, m) -> {
			String msg = p.getText();
			// Remove starting end ending "
			msg = msg.substring(1, msg.length() - 1);
			
			// optimize empty prints out
			if (!msg.isEmpty()) {
				// Twice for Java
				// Twice for replaceAll expecting a regex
				msg = msg.replaceAll("\\\\n", "\n");
				
				m.pushInstruction(ByteCode.PRT);
				m.pushArgument(msg);
			}
		});
	}
}
