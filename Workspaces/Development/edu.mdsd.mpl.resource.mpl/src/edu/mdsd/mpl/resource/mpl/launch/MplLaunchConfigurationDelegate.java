/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package edu.mdsd.mpl.resource.mpl.launch;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;

import edu.mdsd.mil.MILModel;
import edu.mdsd.mil.interpreter.MILInterpreter;
import edu.mdsd.mpl.MPLModel;
import edu.mdsd.mpl.compiler.mil.MPL2MILCompiler;
import utils.MessageConsoleOutput;

/**
 * A class that handles launch configurations.
 */
public class MplLaunchConfigurationDelegate extends LaunchConfigurationDelegate {
	
	/**
	 * The URI of the resource that shall be launched.
	 */
	public final static String ATTR_RESOURCE_URI = "uri";
	
	private final static String MPLConsoleName = "MPL Console";
	private MPL2MILCompiler compiler = new MPL2MILCompiler();
	private MILInterpreter interpreter = new MILInterpreter();
	
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
	}
	
	private void save(MILModel milModel, URI uri, Resource mplResource) {
		Resource milResource = mplResource.getResourceSet().createResource(uri);
		milResource.getContents().add(milModel);
		
		try {
			milResource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		MplLaunchConfigurationHelper launchHelper = new MplLaunchConfigurationHelper();
		MPLModel mplmodel = (MPLModel) launchHelper.getModelRoot(configuration);
		
		MessageConsole mplConsole = findConsole(MPLConsoleName);
		mplConsole.clearConsole();
		
		compiler.initialize();
		compiler.setOutput(new MessageConsoleOutput(mplConsole));
		MILModel milmodel = compiler.compile(mplmodel);
		
		if (milmodel != null) {
			// save to file
			URI mplPath = launchHelper.getURI(configuration);
			save(milmodel, mplPath.trimFileExtension().appendFileExtension("mil"), mplmodel.eResource());
			
			// run
			interpreter.setOutput(new MessageConsoleOutput(mplConsole, interpreter));
			interpreter.initialize();
			Map<String, Integer> output = interpreter.interpret(milmodel);
			
			interpreter.out().println("\nResults:");
			for (Entry<String, Integer> kv : output.entrySet()) {
				interpreter.out().println("  " + kv.getKey() + " = " + kv.getValue());
			}
		}
	}
	
}
