package com.matigakis.flightbot.configuration;

import org.apache.commons.configuration.Configuration;
import org.python.core.PyObject;

/**
 * This class allows a Configuration object to be used inside the jython
 * interpreter.
 */
public class JythonConfigurationAdapter extends PyObject{
	private static final long serialVersionUID = 1L;
	
	private Configuration configuration;
	
	public JythonConfigurationAdapter(Configuration configuration){
		this.configuration = configuration;
	}
	
	public String getProperty(String property){
		return configuration.getString(property);
	}
}
