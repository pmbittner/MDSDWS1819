package edu.mdsd.mpl.compiler.mil;

import java.util.HashMap;
import java.util.Map;

import edu.mdsd.mil.JumpMarker;
import edu.mdsd.mil.MILModel;
import edu.mdsd.mil.Statement;
import edu.mdsd.mpl.Operation;
import edu.mdsd.mpl.Variable;
import edu.mdsd.mpl.util.MILCreator;

public class Compilation {
	private MILCreator creator;
	private MILModel mil;
	
	private Map<String, JumpMarker> jumpMarkers;
	
	public Compilation(MILCreator creator) {
		this.creator = creator;
		this.jumpMarkers = new HashMap<>();
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

	public void addLoadInstruction(Variable variable) {
		add(creator.createLoadInstruction(variable.getName()));
	}

	public void addStoreInstruction() {
		add(creator.createStoreInstruction());
	}

	public void addStoreInstruction(Variable variable) {
		addStoreInstruction(variable.getName());
	}

	private void addStoreInstruction(String address) {
		add(creator.createStoreInstruction(address));
	}
	
	public JumpMarker getOrCreateJumpMarker(Operation operation) {
		return getOrCreateJumpMarker(operation.getClass().getSimpleName() + "_" + operation.getName());
	}
	
	public JumpMarker getOrCreateJumpMarker(String name) {
		JumpMarker marker = jumpMarkers.get(name);
		
		if (marker == null)
			jumpMarkers.put(name, marker = creator.createJumpMarker(name));
		
		return marker;
	}
}
