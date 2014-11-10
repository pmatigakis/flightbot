package com.matigakis.flightbot.fdm;

public class FlightgearFDMFactory implements RemoteFDMFactory{
	private String host;
	private int sensorsPort;
	private int controlsPort;
	
	public FlightgearFDMFactory(String host, int sensorsPort, int controlsPort) {
		this.host = host;
		this.sensorsPort = sensorsPort;
		this.controlsPort = controlsPort;
	}

	@Override
	public RemoteFDM createRemoteFDM() {
		RemoteFDM fdm = new FlightGearFDM(host, sensorsPort, controlsPort);

		return fdm;
	}
}
