/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package edu.mdsd.mpl.resource.mpl.analysis;

import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class MplINTEGER_TOKENTokenResolver implements edu.mdsd.mpl.resource.mpl.IMplTokenResolver {
	
	private edu.mdsd.mpl.resource.mpl.analysis.MplDefaultTokenResolver defaultTokenResolver = new edu.mdsd.mpl.resource.mpl.analysis.MplDefaultTokenResolver(true);
	
	public String deResolve(Object value, EStructuralFeature feature, EObject container) {
		// By default token de-resolving is delegated to the DefaultTokenResolver.
		String result = defaultTokenResolver.deResolve(value, feature, container, null, null, null);
		return result;
	}
	
	public void resolve(String lexem, EStructuralFeature feature, edu.mdsd.mpl.resource.mpl.IMplTokenResolveResult result) {
		// By default token resolving is delegated to the DefaultTokenResolver.
		defaultTokenResolver.resolve(lexem, feature, result, null, null, null);
	}
	
	public void setOptions(Map<?,?> options) {
		defaultTokenResolver.setOptions(options);
	}
	
}
