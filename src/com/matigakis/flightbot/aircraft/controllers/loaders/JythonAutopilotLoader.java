package com.matigakis.flightbot.aircraft.controllers.loaders;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import com.matigakis.flightbot.aircraft.controllers.AircraftController;

public class JythonAutopilotLoader implements AutopilotLoader {
	public AircraftController getAutopilot(String autopilotName){
		PySystemState sys = Py.getSystemState();
		sys.path.append(new PyString(autopilotName));
		
		PythonInterpreter interpreter = new PythonInterpreter(null, sys);
		
		interpreter.exec("from autopilot import Autopilot");
		
		PyObject controllerClass = interpreter.get("Autopilot");
		
		PyObject controllerObject = controllerClass.__call__();
		
		return (AircraftController) controllerObject.__tojava__(AircraftController.class);
	}
}
