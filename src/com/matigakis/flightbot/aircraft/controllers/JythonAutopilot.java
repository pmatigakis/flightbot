package com.matigakis.flightbot.aircraft.controllers;

import java.io.OutputStream;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import com.matigakis.flightbot.aircraft.Aircraft;

/**
 * The JythonAutopilot load an autopilot from specified Jython package.
 */
public class JythonAutopilot implements Autopilot{
	private Autopilot autopilot;
	private PythonInterpreter interpreter;
	
	public JythonAutopilot(String autopilotPackage) {
		PySystemState sys = Py.getSystemState();
		sys.path.append(new PyString(autopilotPackage));
		
		interpreter = new PythonInterpreter(null, sys);
		
		interpreter.exec("from autopilot import Autopilot");
		
		PyObject controllerClass = interpreter.get("Autopilot");
		
		PyObject controllerObject = controllerClass.__call__();
		
		autopilot = (Autopilot) controllerObject.__tojava__(Autopilot.class);
	}
	
	@Override
	public void updateControls(Aircraft aircraft) {
		autopilot.updateControls(aircraft);
	}
	
	public void setOutputStream(OutputStream outputStream){
		interpreter.setOut(outputStream);
	}
}
