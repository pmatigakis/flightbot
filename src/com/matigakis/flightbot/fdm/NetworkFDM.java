package com.matigakis.flightbot.fdm;

import com.matigakis.fgcontrol.ControlsClient;
import com.matigakis.fgcontrol.SensorDataListener;
import com.matigakis.fgcontrol.SensorServer;
import com.matigakis.fgcontrol.sensors.SensorData;
import com.matigakis.flightbot.aircraft.Aircraft;

public class NetworkFDM implements FDM, SensorDataListener{
	private SensorData sensorData;
	private SensorServer sensorServer;
	private ControlsClient controlsClient;
	
	public NetworkFDM(String host, int sensorsPort, int controlsPort) {		
		this.sensorData = new SensorData();
		
		sensorServer = new SensorServer(sensorsPort);
		sensorServer.addSensorDataListener(this);
		
		controlsClient = new ControlsClient(host, controlsPort);
	}

	@Override
	public void run(Aircraft aircraft) {
		controlsClient.transmitControls(aircraft.getControls());
		aircraft.updateFromSensorData(sensorData);
	}

	@Override
	public void handleSensorData(SensorData sensorData) {
		this.sensorData = sensorData;
	}
	
	public void connect() throws InterruptedException{		
		sensorServer.start();
		
		controlsClient.openConnection();
	}
	
	public void disconnect(){
		sensorServer.stopServer();
		controlsClient.closeConnection();
	}
}
