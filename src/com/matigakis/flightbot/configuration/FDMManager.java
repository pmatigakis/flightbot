package com.matigakis.flightbot.configuration;

import org.apache.commons.configuration.Configuration;

import com.matigakis.flightbot.fdm.NetworkFDMFactory;

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
	 * Create a NetworkFDMFactory
	 * 
	 * @return
	 */
	private NetworkFDMFactory createNetworkFDMFactory(){
		String host = configuration.getString("simulation.fdm.controls.host");
		int sensorsPort = configuration.getInt("simulation.fdm.sensors.port");
		int controlsPort = configuration.getInt("simulation.fdm.controls.port");
		
		NetworkFDMFactory fdmFactory = new NetworkFDMFactory(host, sensorsPort, controlsPort);
		
		return fdmFactory;
	}
	
	/**
	 * Create an FDMFactory using the settings in the configuration object
	 * 
	 * @return
	 * @throws FDMConfigurationException
	 */
	public NetworkFDMFactory getFDMFactory() throws FDMConfigurationException{
		NetworkFDMFactory fdmFactory;
		
		String fdmType = configuration.getString("simulation.fdm.type");
		
		switch(fdmType){
		case "network":
			fdmFactory = createNetworkFDMFactory();
			break;
		default:
			throw new FDMConfigurationException();
		}
		
		return fdmFactory;
	}
}
