package com.matigakis.flightbot.aircraft.controllers.loaders;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import com.matigakis.flightbot.aircraft.controllers.Autopilot;

public class JythonAutopilotLoader implements AutopilotLoader {
	private String autopilotPackage;
	
	public JythonAutopilotLoader(String autopilotPackage){
		this.autopilotPackage = autopilotPackage;
	}
	
	public Autopilot getAutopilot(){
		PySystemState sys = Py.getSystemState();
		sys.path.append(new PyString(autopilotPackage));
		
		PythonInterpreter interpreter = new PythonInterpreter(null, sys);
		
		interpreter.exec("from autopilot import Autopilot");
		
		PyObject controllerClass = interpreter.get("Autopilot");
		
		PyObject controllerObject = controllerClass.__call__();
		
		return (Autopilot) controllerObject.__tojava__(Autopilot.class);
	}
}
