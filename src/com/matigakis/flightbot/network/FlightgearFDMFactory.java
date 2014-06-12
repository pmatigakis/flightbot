package com.matigakis.flightbot.network;

/**
 * The FlightgearFDMFactory class is a Factory class that can be used to create
 * an FDM object that is using flightgear as backend. 
 */
public class FlightgearFDMFactory {
	
	/**
	 * Create an FDM object.
	 * 
	 * @param sensorsPort the port that flightgear is listening.
	 * @return FDM object
	 * @throws Exception
	 */
	public static FlightgearFDM createFDM(int sensorsPort){		
		FlightgearFDM fdm = new FlightgearFDM(sensorsPort);
		
		fdm.startFDM();
		
		return fdm;
	} 
}
