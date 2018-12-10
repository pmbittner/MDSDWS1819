package edu.mdsd.mpl.compiler.mil;

import edu.mdsd.mil.MILFactory;
import edu.mdsd.mil.MILModel;

public class EcoreMILCreator implements MILCreator {
	private MILFactory milFactory;
	
	public EcoreMILCreator() {
		super();
		this.milFactory = MILFactory.eINSTANCE;
	}
	
	public MILModel createMILModel() {
		return milFactory.createMILModel();
	}
}
