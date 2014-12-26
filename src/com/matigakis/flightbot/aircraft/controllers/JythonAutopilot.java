package com.matigakis.flightbot.aircraft.controllers;

import java.io.File;
import java.io.OutputStream;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FilenameUtils;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import com.matigakis.flightbot.aircraft.Aircraft;

/**
 * The JythonAutopilot loads an autopilot from a specified Jython module.
 * That module must contain a function named create_autopilot that is responsible to 
 * create and initialize the autopilot and return a class that has a setup, reset and run method.
 */
public class JythonAutopilot implements Autopilot{
	private PythonInterpreter interpreter;
	PyObject jyConfiguration;
	PyObject autopilot;
	
	public JythonAutopilot(File autopilotFile, Configuration configuration) {
		PySystemState sys = Py.getSystemState();
		
		String path = FilenameUtils.getPath(autopilotFile.getAbsolutePath());
		sys.path.append(new PyString("/" + path));
		
		jyConfiguration = Py.java2py(configuration);
		
		interpreter = new PythonInterpreter(null, sys);

		//Load the autopilot
		//interpreter.exec("from autopilot import create_autopilot");
		interpreter.execfile(autopilotFile.getPath());
		
		autopilot = (PyObject) interpreter.eval("create_autopilot()");
		
		autopilot.invoke("setup", jyConfiguration);
	}
	
	@Override
	public void updateControls(Aircraft aircraft) {
		PyObject jyAircraft = Py.java2py(aircraft);
		
		autopilot.invoke("run", jyAircraft);
	}
	
	//Set the output stream of the Jython interpreter to this output stream
	public void setOutputStream(OutputStream outputStream){
		interpreter.setOut(outputStream);
	}

	@Override
	public void reset() {
		autopilot.invoke("reset", jyConfiguration);
	}
}
