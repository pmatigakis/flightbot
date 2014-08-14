package com.matigakis.flightbot.fdm;

public class NetworkFDMFactory implements FDMFactory{
	private String host;
	private int sensorsPort;
	private int controlsPort;
	
	public NetworkFDMFactory(){
		//TODO: make sure this is corrent
		this.host = "localhost";
		this.sensorsPort = 10501;
		this.controlsPort = 10502;
	}
	
	public NetworkFDMFactory(String host, int sensorsPort, int controlsPort) {
		this.host = host;
		this.sensorsPort = sensorsPort;
		this.controlsPort = controlsPort;
	}
	
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
