package com.matigakis.flightbot.configuration;

import org.apache.commons.configuration.Configuration;

import com.matigakis.flightbot.fdm.FDMFactory;
import com.matigakis.flightbot.fdm.NetworkFDMFactory;

public final class FDMManager {
	private Configuration configuration;
	
	public FDMManager(Configuration configuration){
		this.configuration = configuration;
	}
	
	private FDMFactory createNetworkFDMFactory(){
		String host = configuration.getString("controls.host");
		int sensorsPort = configuration.getInt("sensors.port");
		int controlsPort = configuration.getInt("controls.port");
		
		FDMFactory fdmFactory = new NetworkFDMFactory(host, sensorsPort, controlsPort);
		
		return fdmFactory;
	}
	
	public FDMFactory getFDMFactory() throws FDMConfigurationException{
		FDMFactory fdmFactory;
		
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
