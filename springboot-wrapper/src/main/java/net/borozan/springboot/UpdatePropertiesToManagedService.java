package net.borozan.springboot;

import java.util.Dictionary;
import java.util.Optional;
import java.util.Properties;

import org.osgi.service.cm.ManagedService;

/**
 * 
 * @author jurica
 *
 */
public class UpdatePropertiesToManagedService {

    Properties properties;
    
    ManagedService managedService;

    /**
     * Method applies properties to managed service
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void apply() {
        
    	// be cautious, and check nulls always
        Optional.ofNullable(properties)
            .ifPresent( props -> {
                
                // update service with properties
                Optional.ofNullable(managedService)
                    .ifPresent( ms -> {                       
                        try {
                            // works like this
                            ms.updated((Dictionary) props);
                        } catch (Exception ignore) {}
                    } );
            } );
    }

    public Properties getProperties() {
    	
        return properties;
    }

    public void setProperties(Properties properties) {
    	
    	// set
        this.properties = properties;
        
        // and apply
        apply();
    }

    public ManagedService getManagedService() {
    	
        return managedService;
    }

    public void setManagedService(ManagedService managedService) {
        
    	// set 
    	this.managedService = managedService;
        
        // and apply
        apply();
    }
}
