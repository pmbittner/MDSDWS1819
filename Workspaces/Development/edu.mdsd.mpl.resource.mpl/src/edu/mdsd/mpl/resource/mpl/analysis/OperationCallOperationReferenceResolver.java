/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package edu.mdsd.mpl.resource.mpl.analysis;

import java.util.Map;
import org.eclipse.emf.ecore.EReference;

public class OperationCallOperationReferenceResolver implements edu.mdsd.mpl.resource.mpl.IMplReferenceResolver<edu.mdsd.mpl.OperationCall, edu.mdsd.mpl.Operation> {
	
	private edu.mdsd.mpl.resource.mpl.analysis.MplDefaultResolverDelegate<edu.mdsd.mpl.OperationCall, edu.mdsd.mpl.Operation> delegate = new edu.mdsd.mpl.resource.mpl.analysis.MplDefaultResolverDelegate<edu.mdsd.mpl.OperationCall, edu.mdsd.mpl.Operation>();
	
	public void resolve(String identifier, edu.mdsd.mpl.OperationCall container, EReference reference, int position, boolean resolveFuzzy, final edu.mdsd.mpl.resource.mpl.IMplReferenceResolveResult<edu.mdsd.mpl.Operation> result) {
		delegate.resolve(identifier, container, reference, position, resolveFuzzy, result);
	}
	
	public String deResolve(edu.mdsd.mpl.Operation element, edu.mdsd.mpl.OperationCall container, EReference reference) {
		return delegate.deResolve(element, container, reference);
	}
	
	public void setOptions(Map<?,?> options) {
		// save options in a field or leave method empty if this resolver does not depend
		// on any option
	}
	
}
