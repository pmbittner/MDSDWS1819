package edu.mdsd.mpl.compiler.mil;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.mdsd.mil.MILModel;
import edu.mdsd.mpl.Block;
import edu.mdsd.mpl.MPLModel;
import edu.mdsd.mpl.Operation;
import edu.mdsd.mpl.Program;
import edu.mdsd.mpl.VariableDeclaration;
import edu.mdsd.mpl.compiler.mil.compilers.BlockCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ElementCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.ProgramCompiler;
import edu.mdsd.mpl.compiler.mil.compilers.VariableDeclarationCompiler;
import utils.Output;


public class MPL2MILCompiler {
	private Output output;
	
	@SuppressWarnings("rawtypes")
	private Map<Class, ElementCompiler> compilers;
	
	public void initialize() {
		compilers = new HashMap<>();
		compilers.put(Program.class, new ProgramCompiler());
		compilers.put(VariableDeclaration.class, new VariableDeclarationCompiler());
		compilers.put(Block.class, new BlockCompiler());
	}

	public MILModel compile(MPLModel mpl) {
		Compilation compilation = new Compilation(new EcoreMILCreator());
		
		compile(mpl.getProgram(), compilation);
		
		for (Operation op : mpl.getOperations()) {
			compile(op, compilation);
		}
		
		return compilation.getMILModel();
	}

	public <T> void compile(T element, Compilation compilation) {
		if (element == null) {
			out().warn("Element to compile was null.");
			return;
		}
		
		getCompiler(element).compileUntyped(this, compilation, element);
	}
	
	public void setOutput(Output output) {
		this.output = output;
	}
	
	public Output out() {
		return output;
	}
	
	@SuppressWarnings("unchecked")
	private <T> ElementCompiler<T> getCompiler(T element) {
		/*
		Class<? super T> elementClass = (Class<? super T>) element.getClass();
		
		while (elementClass != Object.class) {
			System.out.println("Mark1 " + elementClass.getSimpleName());
			ElementCompiler<T> compi = compilers.get(elementClass);
			
			if (compi == null) {
				System.out.println("compi == null");
				elementClass = elementClass.getSuperclass();
			} else {
				System.out.println("compi != null");
				return compi;
			}
		}
		*/
		
		ElementCompiler<T> compiler = null;
		
		for (@SuppressWarnings("rawtypes") Entry<Class, ElementCompiler> kv : compilers.entrySet()) {
			@SuppressWarnings("rawtypes")
			Class type = kv.getKey();
			if (type.isInstance(element)) {
				if (compiler != null) {
					throw new RuntimeException(
							"[MPL2MILCompiler.getCompiler] Multiple possible compilers for type "
					+ element.getClass().getSimpleName()
					+ " found! (" + compiler.getClass().getSimpleName()
					+ " and "
					+ kv.getValue().getClass().getSimpleName()
					+ ")");
				}
				
				compiler = kv.getValue();
			}
		}
		
		if (compiler != null)
			return compiler;
		
		throw new UnsupportedOperationException("Unsupported type: " + element.getClass());
	}
}
