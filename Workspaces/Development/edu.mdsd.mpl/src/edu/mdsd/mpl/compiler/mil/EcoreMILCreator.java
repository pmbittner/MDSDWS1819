package edu.mdsd.mpl.compiler.mil;

import edu.mdsd.mil.ConstantInteger;
import edu.mdsd.mil.LoadInstruction;
import edu.mdsd.mil.MILFactory;
import edu.mdsd.mil.MILModel;
import edu.mdsd.mil.RegisterReference;
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
	public LoadInstruction createLoadInstruction(int rawValue) {
		LoadInstruction load = factory.createLoadInstruction();
		ConstantInteger value = factory.createConstantInteger();
		value.setRawValue(rawValue);
		load.setValue(value);
		return load;
	}

	@Override
	public StoreInstruction createStoreInstruction(String address) {
		StoreInstruction store = factory.createStoreInstruction();
		RegisterReference registerReference = factory.createRegisterReference();
		registerReference.setAddress(address);
		store.setRegisterReference(registerReference);
		return store;
	}
}
