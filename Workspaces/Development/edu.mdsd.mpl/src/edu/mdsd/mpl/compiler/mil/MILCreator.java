package edu.mdsd.mpl.compiler.mil;

import edu.mdsd.mil.LoadInstruction;
import edu.mdsd.mil.MILModel;
import edu.mdsd.mil.StoreInstruction;

public interface MILCreator {
	public MILModel createMILModel();
	
	public LoadInstruction createLoadInstruction(int rawValue);
	public StoreInstruction createStoreInstruction(String address);
}
