/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package edu.mdsd.mil.resource.mil.launch;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;

import edu.mdsd.mil.MILModel;
import edu.mdsd.mil.interpreter.MILInterpreter;
import utils.MessageConsoleOutput;

/**
 * A class that handles launch configurations.
 */
public class MilLaunchConfigurationDelegate extends LaunchConfigurationDelegate {
	
	/**
	 * The URI of the resource that shall be launched.
	 */
	public final static String ATTR_RESOURCE_URI = "uri";
	
	private final static String MILConsoleName = "MIL Console";
	private MILInterpreter interpreter = new MILInterpreter();
	
	//*
	private MessageConsole findConsole(String name) {
	      ConsolePlugin plugin = ConsolePlugin.getDefault();
	      IConsoleManager conMan = plugin.getConsoleManager();
	      IConsole[] existing = conMan.getConsoles();
	      for (int i = 0; i < existing.length; i++)
	         if (name.equals(existing[i].getName()))
	            return (MessageConsole) existing[i];
	      //no console found, so create a new one
	      MessageConsole myConsole = new MessageConsole(name, null);
	      conMan.addConsoles(new IConsole[]{myConsole});
	      return myConsole;
	}//*/
	
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		// Set the overrideLaunchConfigurationDelegate option to <code>false</code> to
		// implement this method or disable launching support by setting
		// disableLaunchSupport to <code>true</code>.
		
		MilLaunchConfigurationHelper launchHelper = new MilLaunchConfigurationHelper();
		MILModel milmodel = (MILModel) launchHelper.getModelRoot(configuration);
		
		//new edu.mdsd.mil.resource.mil.launch.MilLaunchConfigurationHelper().launch(configuration, mode, launch, monitor);
		
		MessageConsole milConsole = findConsole(MILConsoleName);
		milConsole.clearConsole();
		
		interpreter.setOutput(new MessageConsoleOutput(milConsole, interpreter));
		interpreter.initialize();
		Map<String, Integer> output = interpreter.interpret(milmodel);
		
		interpreter.out().println("\nResults:");
		for (Entry<String, Integer> kv : output.entrySet()) {
			interpreter.out().println("  " + kv.getKey() + " = " + kv.getValue());
		}
	}
	
}
