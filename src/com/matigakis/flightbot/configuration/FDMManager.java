package com.matigakis.flightbot.configuration;

import org.apache.commons.configuration.Configuration;

import com.matigakis.fgcontrol.fdm.NetworkFDM;
import com.matigakis.fgcontrol.fdm.RemoteFDM;
import com.matigakis.fgcontrol.network.TelemetryServer;
import com.matigakis.fgcontrol.network.UDPTelemetryServer;

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
	 * Create a NetworkFDM
	 * 
	 * @return
	 */
	private NetworkFDM createNetworkFDM(){
		String host = configuration.getString("fdm.controls.host");
		int telemetryPort = configuration.getInt("fdm.sensors.port");
		int controlsPort = configuration.getInt("fdm.controls.port");
		
		NetworkFDM networkFDM = new NetworkFDM(host, telemetryPort, controlsPort);

		return networkFDM;
	}
	
	/**
	 * Create an RemoteFDM object using the settings in the configuration object
	 * 
	 * @return
	 * @throws FDMConfigurationException
	 */
	public RemoteFDM getRemoteFDM() throws FDMConfigurationException{
		RemoteFDM fdm;
		
		String fdmType = configuration.getString("fdm.type");
		
		switch(fdmType){
		case "flightgear":
			fdm = createNetworkFDM();
			break;
		default:
			throw new FDMConfigurationException("Failed to create remote fdm factory");
		}
		
		return fdm;
	}
	
	public TelemetryServer getViewerTelemetryServer(){
		//TODO: I should check what type of fdm server to create.
		//For the moment I will always assume that a connection to flightgear will
		//be used
		
		int port = configuration.getInt("viewer.sensors.port");
		
		TelemetryServer viewerFDMServer = new UDPTelemetryServer(port);
		
		return viewerFDMServer;
	}
}
