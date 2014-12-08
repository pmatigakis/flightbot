package com.matigakis.flightbot.configuration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * The ConfigurationManager object loads the configuration from an xml file
 * and creates a Configuration object that can be used by the rest of the
 * application.
 */
public class ConfigurationManager {
	private static final String CONFIGURATION_FILE = "config/settings.xml";
	
	private static Configuration configuration;
	
	public static Configuration getConfiguration() throws ConfigurationException{
		if(configuration == null){
			configuration = new XMLConfiguration(CONFIGURATION_FILE);
		}
		
		return configuration;
	}
}
