package com.matigakis.flightbot.aircraft.controllers;

import java.util.concurrent.LinkedBlockingQueue;

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
	private ControlsClient controlsClient;
	private TelemetryView telemetryView;
	private LinkedBlockingQueue<SensorData> sensorDataQueue;
	private volatile boolean running;
	
	private SensorData sensorData;
	
	public Autopilot(AircraftController aircraftController, ControlsClient controlsClient, TelemetryView telemetryView){
		super();
		
		this.aircraftController = aircraftController;
		this.controlsClient = controlsClient;
		this.telemetryView = telemetryView;
		sensorDataQueue = new LinkedBlockingQueue<SensorData>(10);
		
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
			
			/*
			SensorData sensorData = sensorDataQueue.poll();
			
			if(sensorData != null && telemetryView.isAutopilotActivated()){
				aircraft.updateFromSensorData(sensorData);
				aircraftController.updateAircraftControls(aircraft);
				controlsClient.transmitControls(aircraft.getControls());
			}else{
				//This must be done in order to make sure that if the autopilot is paused
				//the sensor data that are saved in the queue are the most current data available.
				sensorDataQueue.clear();
			}
			*/
			
			if(telemetryView.isAutopilotActivated()){
				aircraft.updateFromSensorData(sensorData);
				aircraftController.updateAircraftControls(aircraft);
				controlsClient.transmitControls(aircraft.getControls());
			}
			
			long diff = (updateRate - (System.nanoTime() - timeSinceControlsUpdate)) / 1000000;
			
			if(diff > 0){
				try{
					//System.out.println("Sleeping for " + diff);
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
}
