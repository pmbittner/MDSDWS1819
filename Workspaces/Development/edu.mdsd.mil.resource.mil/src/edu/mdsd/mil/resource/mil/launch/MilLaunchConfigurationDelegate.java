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

import edu.mdsd.mil.MILModel;
import edu.mdsd.mil.interpreter.MILInterpreter;

/**
 * A class that handles launch configurations.
 */
public class MilLaunchConfigurationDelegate extends LaunchConfigurationDelegate {
	
	/**
	 * The URI of the resource that shall be launched.
	 */
	public final static String ATTR_RESOURCE_URI = "uri";
	
	private MILInterpreter interpreter = new MILInterpreter();
	
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		// Set the overrideLaunchConfigurationDelegate option to <code>false</code> to
		// implement this method or disable launching support by setting
		// disableLaunchSupport to <code>true</code>.
		
		MilLaunchConfigurationHelper launchHelper = new MilLaunchConfigurationHelper();
		MILModel milmodel = (MILModel) launchHelper.getModelRoot(configuration);
		
		//new edu.mdsd.mil.resource.mil.launch.MilLaunchConfigurationHelper().launch(configuration, mode, launch, monitor);
		
		interpreter.initialize();
		Map<String, Integer> output = interpreter.interpret(milmodel);
		
		System.out.println("\nResults:");
		for (Entry<String, Integer> kv : output.entrySet()) {
			System.out.println("  " + kv.getKey() + " = " + kv.getValue());
		}
	}
	
}
