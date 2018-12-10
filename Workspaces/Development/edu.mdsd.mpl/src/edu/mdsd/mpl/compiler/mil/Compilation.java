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
	
	public void append(Statement statement) {
		mil.getStatements().add(statement);
	}

	public MILModel getMILModel() {
		return mil;
	}
	
	public MILCreator getMILCreator() {
		return creator;
	}
}
