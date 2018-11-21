package edu.mdsd.mil.interpreter;

import java.util.Stack;

public class OperandStack {
	private Stack<Integer> stack;
	
	public OperandStack() {
		stack = new Stack<>();
	}
	
	public void initialize() {
		stack.clear();
	}
	
	public void push(int rawValue) {
		stack.push(rawValue);
	}

	public int pop() {
		return stack.pop();
	}

	public int size() {
		return stack.size();
	}
}
