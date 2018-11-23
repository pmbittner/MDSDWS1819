package edu.mdsd.mil.interpreter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VariableRegister {
	private Map<String, Integer> register;
	
	public VariableRegister() {
		register = new HashMap<>();
	}
	
	public void initialize() {
		register.clear();
	}
	
	public int get(String address) {
		// lazy initialization
		if (!register.containsKey(address)) {
			register.put(address, 0);
			return 0;
		}

		return register.get(address);	
	}
	
	public void put(String address, int rawValue) {
		register.put(address, rawValue);
	}
	
	public Map<String, Integer> toMap() {
		return Collections.unmodifiableMap(register);
	}
}
