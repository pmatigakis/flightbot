package com.matigakis.flightbot.fdm;

public class NetworkFDMFactory implements FDMFactory{
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
	@Override
	public FDM createFDM() {
		NetworkFDM fdm = new NetworkFDM(host, sensorsPort, controlsPort);
		
		try {
			fdm.connect();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return fdm;
	}
}
