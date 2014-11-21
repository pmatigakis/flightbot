package com.matigakis.flightbot.aircraft.controllers;

import java.io.OutputStream;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import com.matigakis.flightbot.aircraft.Aircraft;

/**
 * The JythonAutopilot loads an autopilot from a specified Jython package.
 * That package must have a module named autopilot which contains a function
 * named create_autopilot that is responsible to create and initialize the
 * autopilot and return an Autopilot object.
 */
public class JythonAutopilot implements Autopilot{
	private Autopilot autopilot;
	private PythonInterpreter interpreter;
	
	public JythonAutopilot(String autopilotPackage) {
		PySystemState sys = Py.getSystemState();
		sys.path.append(new PyString(autopilotPackage));
		
		interpreter = new PythonInterpreter(null, sys);
		
		interpreter.exec("from autopilot import create_autopilot");
		
		PyObject autopilotCreator = interpreter.get("create_autopilot");
		
		PyObject controllerObject = autopilotCreator.__call__();
		
		autopilot = (Autopilot) controllerObject.__tojava__(Autopilot.class);
	}
	
	@Override
	public void updateControls(Aircraft aircraft) {
		autopilot.updateControls(aircraft);
	}
	
	//Set the output stream of the Jython interpreter to this output stream
	public void setOutputStream(OutputStream outputStream){
		interpreter.setOut(outputStream);
	}

	@Override
	public void reset() {
		autopilot.reset();
	}
}
