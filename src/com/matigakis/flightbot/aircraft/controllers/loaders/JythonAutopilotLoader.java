package com.matigakis.flightbot.aircraft.controllers.loaders;

import org.apache.commons.configuration.Configuration;

import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.aircraft.controllers.JythonAutopilot;

/**
 * The JythonAutopilotLoader is used to create a new Autopilot object
 * using a Jython package.
 */
public class JythonAutopilotLoader implements AutopilotLoader {
	private String autopilotPackage;
	private Configuration configuration;
	
	public JythonAutopilotLoader(String autopilotPackage, Configuration configuration){
		this.autopilotPackage = autopilotPackage;
		this.configuration = configuration;
	}
	
	/**
	 * Create a Jython autopilot.
	 */
	public Autopilot getAutopilot(){
		return new JythonAutopilot(autopilotPackage, configuration);
	}
}
