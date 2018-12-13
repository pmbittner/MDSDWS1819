package edu.mdsd.mpl.util;

import edu.mdsd.mil.AddInstruction;
import edu.mdsd.mil.CallInstruction;
import edu.mdsd.mil.ConditionalJumpInstruction;
import edu.mdsd.mil.ConstantInteger;
import edu.mdsd.mil.JumpInstruction;
import edu.mdsd.mil.JumpMarker;
import edu.mdsd.mil.LoadInstruction;
import edu.mdsd.mil.MILFactory;
import edu.mdsd.mil.MILModel;
import edu.mdsd.mil.PrintInstruction;
import edu.mdsd.mil.RegisterReference;
import edu.mdsd.mil.Statement;
import edu.mdsd.mil.StoreInstruction;

public class EcoreMILCreator implements MILCreator {
	private MILFactory factory;
	
	public EcoreMILCreator() {
		super();
		this.factory = MILFactory.eINSTANCE;
	}
	
	public MILModel createMILModel() {
		return factory.createMILModel();
	}

	@Override
	public JumpMarker createJumpMarker(String string) {
		JumpMarker jmp = factory.createJumpMarker();
		jmp.setName(string);
		return jmp;
	}

	@Override
	public Statement createJumpInstruction(JumpMarker marker) {
		JumpInstruction jmp = factory.createJumpInstruction();
		jmp.setJumpTarget(marker);
		return jmp;
	}

	@Override
	public Statement createConditionalJumpInstruction(JumpMarker marker) {
		ConditionalJumpInstruction jpc = factory.createConditionalJumpInstruction();
		jpc.setJumpTarget(marker);
		return jpc;
	}

	@Override
	public LoadInstruction createLoadInstruction(int rawValue) {
		LoadInstruction load = factory.createLoadInstruction();
		ConstantInteger value = factory.createConstantInteger();
		value.setRawValue(rawValue);
		load.setValue(value);
		return load;
	}

	@Override
	public LoadInstruction createLoadInstruction(String address) {
		LoadInstruction load = factory.createLoadInstruction();
		RegisterReference value = factory.createRegisterReference();
		value.setAddress(address);
		load.setValue(value);
		return load;
	}

	@Override
	public StoreInstruction createStoreInstruction() {
		return factory.createStoreInstruction();
	}

	@Override
	public StoreInstruction createStoreInstruction(String address) {
		StoreInstruction store = createStoreInstruction();
		RegisterReference registerReference = factory.createRegisterReference();
		registerReference.setAddress(address);
		store.setRegisterReference(registerReference);
		return store;
	}

	@Override
	public Statement createReturnInstruction() {
		return factory.createReturnInstruction();
	}

	@Override
	public Statement createNegateInstruction() {
		return factory.createNegateInstruction();
	}

	@Override
	public Statement createYieldInstruction() {
		return factory.createYieldInstruction();
	}

	@Override
	public Statement createAddInstruction() {
		AddInstruction add = factory.createAddInstruction();
		return add;
	}

	@Override
	public Statement createSubInstruction() {
		return factory.createSubInstruction();
	}

	@Override
	public Statement createMultInstruction() {
		return factory.createMultInstruction();
	}

	@Override
	public Statement createDivInstruction() {
		return factory.createDivInstruction();
	}

	@Override
	public Statement createEqualsComparison() {
		return factory.createEqualsComparison();
	}

	@Override
	public Statement createNotEqualsComparison() {
		return factory.createNotEqualsComparison();
	}

	@Override
	public Statement createLowerThanComparison() {
		return factory.createLowerThanComparison();
	}

	@Override
	public Statement createGreaterThanComparison() {
		return factory.createGreaterThanComparison();
	}

	@Override
	public Statement createLowerEqualsComparison() {
		return factory.createLowerEqualsComparison();
	}

	@Override
	public Statement createGreaterEqualsComparison() {
		return factory.createGreaterEqualsComparison();
	}

	@Override
	public Statement createPrintInstruction(String string) {
		String quote = "\"";
		
		PrintInstruction prt = factory.createPrintInstruction();
		prt.setText(quote + string + quote);
		return prt;
	}

	@Override
	public Statement createCallInstruction(JumpMarker target) {
		CallInstruction cal = factory.createCallInstruction();
		cal.setJumpTarget(target);
		return cal;
	}
}
