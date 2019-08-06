package net.borozan.bundleone;

import net.borozan.bundleapi.Service;

/**
 * 
 * @author jurica
 *
 */
public class ServiceImpl implements Service {

	// message will be set from place holder property
	String greetingMessage;
	
	/**
	 * Implemented method from service
	 */
	public String greetings() {
		
		return greetingMessage;
	}

	public void setGreetingMessage(String greetingMessage) {
		this.greetingMessage = greetingMessage;
	}

}
