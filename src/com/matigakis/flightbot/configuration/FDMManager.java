package com.matigakis.flightbot.configuration;

import org.apache.commons.configuration.Configuration;

import com.matigakis.flightbot.fdm.FlightgearFDMFactory;
import com.matigakis.flightbot.fdm.RemoteFDMFactory;

/**
 * FDMManager is responsible for the creation of an FDM based
 * on the settings of the configuration file.
 */
public final class FDMManager {
	private Configuration configuration;
	
	public FDMManager(Configuration configuration){
		this.configuration = configuration;
	}
	
	/**
	 * Create a FlightgearFDMFactory
	 * 
	 * @return
	 */
	private FlightgearFDMFactory createFlightgearFDMFactory(){
		String host = configuration.getString("simulation.fdm.controls.host");
		int sensorsPort = configuration.getInt("simulation.fdm.sensors.port");
		int controlsPort = configuration.getInt("simulation.fdm.controls.port");
		
		FlightgearFDMFactory fdmFactory = new FlightgearFDMFactory(host, sensorsPort, controlsPort);
		
		return fdmFactory;
	}
	
	/**
	 * Create an FDMFactory using the settings in the configuration object
	 * 
	 * @return
	 * @throws FDMConfigurationException
	 */
	public RemoteFDMFactory getFDMFactory() throws FDMConfigurationException{
		FlightgearFDMFactory fdmFactory;
		
		String fdmType = configuration.getString("simulation.fdm.type");
		
		switch(fdmType){
		case "flightgear":
			fdmFactory = createFlightgearFDMFactory();
			break;
		default:
			throw new FDMConfigurationException("Failed create network fdm factory");
		}
		
		return fdmFactory;
	}
}
