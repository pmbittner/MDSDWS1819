/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package edu.mdsd.mpl.resource.mpl.analysis;

import java.util.Map;
import org.eclipse.emf.ecore.EReference;

public class VariableReferenceVariableReferenceResolver implements edu.mdsd.mpl.resource.mpl.IMplReferenceResolver<edu.mdsd.mpl.VariableReference, edu.mdsd.mpl.Variable> {
	
	private edu.mdsd.mpl.resource.mpl.analysis.MplDefaultResolverDelegate<edu.mdsd.mpl.VariableReference, edu.mdsd.mpl.Variable> delegate = new edu.mdsd.mpl.resource.mpl.analysis.MplDefaultResolverDelegate<edu.mdsd.mpl.VariableReference, edu.mdsd.mpl.Variable>();
	
	public void resolve(String identifier, edu.mdsd.mpl.VariableReference container, EReference reference, int position, boolean resolveFuzzy, final edu.mdsd.mpl.resource.mpl.IMplReferenceResolveResult<edu.mdsd.mpl.Variable> result) {
		delegate.resolve(identifier, container, reference, position, resolveFuzzy, result);
	}
	
	public String deResolve(edu.mdsd.mpl.Variable element, edu.mdsd.mpl.VariableReference container, EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
