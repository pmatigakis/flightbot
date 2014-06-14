package com.matigakis.flightbot.aircraft.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.network.ControlsClient;
import com.matigakis.flightbot.network.SensorDataListener;
import com.matigakis.flightbot.sensors.SensorData;
import com.matigakis.flightbot.ui.views.TelemetryView;

public class Autopilot extends Thread implements SensorDataListener{
	private static final Logger logger = LoggerFactory.getLogger(Autopilot.class);
	private AircraftController aircraftController;
	private final ControlsClient controlsClient;
	private final TelemetryView telemetryView;
	private volatile boolean running;
	
	private SensorData sensorData;
	
	public Autopilot(AircraftController aircraftController, ControlsClient controlsClient, TelemetryView telemetryView){
		super();
		
		this.aircraftController = aircraftController;
		this.controlsClient = controlsClient;
		this.telemetryView = telemetryView;
		
		sensorData = new SensorData(); 
	}
	
	@Override
	public void run() {
		logger.info("Starting the autopilot");
		
		Aircraft aircraft = new Aircraft();
		
		double dt = 0.05;
		long updateRate = (long) (dt * 1000000000);
		
		running = true;
		while(running){
			long timeSinceControlsUpdate = System.nanoTime();
			
			if(telemetryView.isAutopilotActivated()){
				aircraft.updateFromSensorData(sensorData);
				aircraftController.updateAircraftControls(aircraft);
				controlsClient.transmitControls(aircraft.getControls());
			}
			
			long diff = (updateRate - (System.nanoTime() - timeSinceControlsUpdate)) / 1000000;
			
			if(diff > 0){
				try{
					Thread.sleep(diff);
				}catch(InterruptedException ex){
					logger.error("The autopilot encountered an error", ex);
				}
			}
		}
		
		logger.info("The autopilot has terminated successfully");
	}

	public void shutdown(){
		logger.info("Shutting down the autopilot");
		
		running = false;
	}
	
	@Override
	public void handleSensorData(SensorData sensorData) {
		//sensorDataQueue.offer(sensorData);
		this.sensorData = sensorData;
	}
	
	public void setAircraftController(AircraftController aircraftController){
		this.aircraftController = aircraftController;
	}
	
	public AircraftController getAircraftController(){
		return aircraftController;
	}
}
