package edu.mdsd.mpl.util;

import edu.mdsd.mil.JumpMarker;
import edu.mdsd.mil.LoadInstruction;
import edu.mdsd.mil.MILModel;
import edu.mdsd.mil.Statement;
import edu.mdsd.mil.StoreInstruction;

public interface MILCreator {
	public MILModel createMILModel();

	public JumpMarker createJumpMarker(String string);
	public Statement createJumpInstruction(JumpMarker marker);
	public Statement createConditionalJumpInstruction(JumpMarker marker);
	
	public LoadInstruction createLoadInstruction(int rawValue);
	public LoadInstruction createLoadInstruction(String address);
	public Statement createStoreInstruction();
	public StoreInstruction createStoreInstruction(String address);

	public Statement createReturnInstruction();
	public Statement createNegateInstruction();
	public Statement createYieldInstruction();
	
	public Statement createAddInstruction();
	public Statement createSubInstruction();
	public Statement createMultInstruction();
	public Statement createDivInstruction();

	public Statement createEqualsComparison();
	public Statement createNotEqualsComparison();
	public Statement createLowerThanComparison();
	public Statement createGreaterThanComparison();
	public Statement createLowerEqualsComparison();
	public Statement createGreaterEqualsComparison();

	public Statement createPrintInstruction(String string);
	public Statement createCallInstruction(JumpMarker target);
}
