package net.borozan.springboot;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

import org.osgi.framework.ServiceReference;

/**
 * 
 * @author jurica
 *
 * @param <T> Service to bind
 */
public class BindAdapter<T> {

	Object referenceListener;
	String bindMethodName;
	Method bindMethod = null;
	
	ServiceReference<T> serviceReference;
	T service;
	Map<String, String> map;
	
	public void apply() {
		
		// referenceListener and bind method name must be present
		Optional.ofNullable(referenceListener).ifPresent( rl -> {			
			Optional.ofNullable(bindMethodName).ifPresent( bm -> {
				
				// if method not known find it (and "cache it")
				bindMethod = Optional.ofNullable( bindMethod ).orElseGet( () -> {			
				
					// calling direct getMethod(name, ...) on class might 
					// not work considering that call parameters might be 
					// of inherited/interface type and thus will not be 
					// returned, since it does not match exactly - it is better to loop
					for(Method m : rl.getClass().getMethods() )
						if( m.getName().equals(bm) )
							return m;
					
					// actually must be found - or return null
					return null;
				});
				
				// try to execute it
				try {
					bindMethod.invoke(rl, service);
					return;
				} catch (Exception ignore) { }
				
				// try to execute it 
				try {
					bindMethod.invoke(rl, service, map);
					return;
				} catch (Exception ignore) { }
		
				// try to execute it 
				try {
					bindMethod.invoke(rl, serviceReference);
					return;
				} catch (Exception ignore) { }
			});		
		});

		return;
	}

	/**
	 * 
	 * @param referenceListener
	 */
	public void setReferenceListener(Object referenceListener) {
		
		this.referenceListener = referenceListener;
		
		// and try to apply
		apply();
	}

	/**
	 * 
	 * @param bindMethod
	 */
	public void setBindMethodName(String bindMethod) {
		
		this.bindMethodName = bindMethod;
		this.bindMethod = null;
		
		// and try to apply
		apply();
	}

	/**
	 * 
	 * @param serviceReference
	 */
	public void setServiceReference(ServiceReference<T> serviceReference) {
		
		this.serviceReference = serviceReference;
		
		// and try to apply
		apply();
	}

	/**
	 * 
	 * @param service
	 */
	public void setService(T service) {
		
		this.service = service;
		
		// and try to apply
		apply();
	}

	/**
	 * 
	 * @param map
	 */
	public void setMap(Map<String, String> map) {
		
		this.map = map;
		
		// and try to apply
		apply();
	}	
	
}
