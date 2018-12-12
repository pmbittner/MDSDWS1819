package edu.mdsd.mpl.compiler.mil;

import edu.mdsd.mil.MILModel;
import edu.mdsd.mil.Statement;

public class Compilation {
	private MILCreator creator;
	private MILModel mil;
	
	public Compilation(MILCreator creator) {
		this.creator = creator;
		mil = this.creator.createMILModel();
	}

	public MILModel getMILModel() {
		return mil;
	}
	
	public MILCreator getMILCreator() {
		return creator;
	}
	
	public void add(Statement statement) {
		mil.getStatements().add(statement);
	}

	public void addLoadInstruction(int rawValue) {
		add(creator.createLoadInstruction(rawValue));
	}

	public void addStoreInstruction(String address) {
		add(creator.createStoreInstruction(address));
	}
}
