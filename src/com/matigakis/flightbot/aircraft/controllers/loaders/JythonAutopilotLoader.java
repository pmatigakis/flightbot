package com.matigakis.flightbot.aircraft.controllers.loaders;

import java.io.OutputStream;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.aircraft.controllers.JythonAutopilot;

/**
 * The JythonAutopilotLoader is used to create a new Autopilot object
 * using a Jython package.
 */
public class JythonAutopilotLoader implements AutopilotLoader {
	private String autopilotPackage;
	
	public JythonAutopilotLoader(String autopilotPackage){
		this.autopilotPackage = autopilotPackage;
	}
	
	/**
	 * Create a Jython autopilot.
	 */
	public Autopilot getAutopilot(){
		return new JythonAutopilot(autopilotPackage);
	}
}
