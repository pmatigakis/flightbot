package com.matigakis.flightbot.flightgear.fdm;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.fdm.FDM;
import com.matigakis.flightbot.sensors.SensorData;

/**
 * The FlightgearFDM object is a proxy to Flightgear's flight dynamic model
 */
public class FlightgearFDM implements FDM, SensorDataListener{
	private final SensorServer sensorServer; 
	private SensorData sensorData;
	
	public FlightgearFDM(int sensorPort){
		sensorData = new SensorData();
		
		sensorServer = new SensorServer(sensorPort);
		
		sensorServer.addSensorDataListener(this);
	}
	
	public void startFDM(){
		sensorServer.startServer();
	}
	
	public void shutdownFDM(){
		sensorServer.stopServer();
	}
	
	@Override
	public void run(Aircraft aircraft) {
		aircraft.updateFromSensorData(sensorData);
	}

	@Override
	public void init(Aircraft aircraft) {
		aircraft.updateFromSensorData(sensorData);
	}
	
	@Override
	public void handleSensorData(SensorData sensorData) {
		this.sensorData = sensorData;
	}
}
