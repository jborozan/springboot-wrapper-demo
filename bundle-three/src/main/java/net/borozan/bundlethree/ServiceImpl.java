package net.borozan.bundlethree;

import java.io.IOException;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Optional;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import net.borozan.bundleapi.Service;

/**
 * 
 * @author jurica
 *
 */
public class ServiceImpl extends HttpServlet  implements ManagedService  {

	private static final long serialVersionUID = 1L;

	// service reference to bundle one
	Service service;
	
	// read properties - will be set by OSGI framework, so create initial ones
	Properties properties = new Properties();
	
	/**
	 * Servlet service method to print out greetings message and those read properties
	 */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        
        StringBuilder sb = new StringBuilder();
        
        // print greetings first
        Optional.ofNullable(service).ifPresent(s -> sb.append("\nGreetings from: ").append(s.greetings()).append("\n"));

        // then prepare properties
        properties.forEach( (key, value) -> {
                sb.append("\n").append( key.toString() ).append(" : ").append( value.toString() );
        });
        
        // and print them
        res.getWriter().write( "BundleThree: " + sb.toString() );
        
        return;
    }

    /**
     * OSGI managed service callback to update properties
     */
	@Override
	public void updated(Dictionary<String, ?> dict) throws ConfigurationException {
		
		Optional.ofNullable(dict)
	        .ifPresent( d -> {
	            
	            // copy dictionary to properties
	            Collections.list(d.keys()).stream()
	                .forEach( key -> properties.put(key, d.get(key).toString()) );
	        });

		return;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	/* OSGI reference listeners */
	
	public void addService( Service service ) {
		
		this.service = service;
	}
	
	public void removeService( Service service ) {
		
		this.service = null;
	}
	
}
