package edu.mdsd.mil.interpreter;

import java.util.Stack;

import utils.Output;

public class CallStack {
	public static class Frame {
		private VariableRegister variableRegister;
		private int returnAddress;

		public Frame(int returnAddress) {
			this.returnAddress = returnAddress;
			this.variableRegister = new VariableRegister();
			this.variableRegister.initialize();
		}

		public VariableRegister getVariableRegister() {
			return variableRegister;
		}
		
		public int getReturnAddress() {
			return returnAddress;
		}
	}
	
	private Stack<Frame> frames;
	private Output output;
	
	public CallStack(Output output) {
		frames = new Stack<>();
		this.output = output;
	}
	
	public void initialize() {
		frames.clear();
	}
	
	public void push(int returnAddress) {
		frames.push(new Frame(returnAddress));
	}
	
	public Frame pop() {
		if (frames.size() > 1) {
			return frames.pop();
		} else {
			output.err("[WARNING in CallStack] Trying to pop the last (root) element of the CallStack! Aborting and just returning top element instead!");
			return peek();
		}
	}
	
	public Frame peek() {
		return frames.peek();
	}
}
