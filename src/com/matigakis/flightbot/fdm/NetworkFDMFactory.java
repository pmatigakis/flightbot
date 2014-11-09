package com.matigakis.flightbot.fdm;

import com.matigakis.fgcontrol.fdm.NetworkFDM;

public class NetworkFDMFactory{
	private String host;
	private int sensorsPort;
	private int controlsPort;
	
	public NetworkFDMFactory(String host, int sensorsPort, int controlsPort) {
		this.host = host;
		this.sensorsPort = sensorsPort;
		this.controlsPort = controlsPort;
	}
	
	/**
	 * Create a new NetworkFDM object
	 */
	public NetworkFDM createFDM() {
		NetworkFDM fdm = new NetworkFDM(host, sensorsPort, controlsPort);
		
		/*
		try {
			fdm.connect();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		
		return fdm;
	}
}
