package com.matigakis.flightbot.aircraft.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.sensors.SensorData;
import com.matigakis.flightbot.network.ControlsClient;
import com.matigakis.flightbot.network.SensorDataListener;
import com.matigakis.flightbot.ui.controllers.TelemetryViewController;

/**
 * The Autopilot class is used to interface the object that is controlling the aircraft
 * with the client that sends control commands to Flightgear.
 */
public class Autopilot extends Thread implements SensorDataListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(Autopilot.class);
	
	private AircraftController aircraftController;
	private final ControlsClient controlsClient;
	private final TelemetryViewController telemetryViewController;
	private final Aircraft aircraft;
	private volatile boolean running;
	
	public Autopilot(AircraftController aircraftController, ControlsClient controlsClient, TelemetryViewController telemetryViewController){
		super();
		
		this.aircraftController = aircraftController;
		this.controlsClient = controlsClient;
		this.telemetryViewController = telemetryViewController;
		 
		aircraft = new Aircraft();
	}
	
	@Override
	public void run() {
		LOGGER.debug("Starting the autopilot");
		
		double dt = 0.05;
		long updateRate = (long) (dt * 1000000000);
		
		setAutopilotState(true);
		while(isAutopilotActive()){
			long timeSinceControlsUpdate = System.nanoTime();
			
			if(telemetryViewController.getAutopilotState()){
				synchronized(aircraft){
					aircraftController.updateAircraftControls(aircraft);
					controlsClient.transmitControls(aircraft.getControls());
				}
			}
			
			long runningTime = System.nanoTime() - timeSinceControlsUpdate;
			long diff = (updateRate - runningTime) / 1000000;
			
			if(diff > 0){
				try{
					Thread.sleep(diff);
				}catch(InterruptedException ex){
					LOGGER.error("The autopilot encountered an error", ex);
				}
			}
		}
		
		LOGGER.debug("The autopilot has terminated successfully");
	}

	/**
	 * Stop the autopilot.
	 */
	public void shutdown(){
		LOGGER.info("Shutting down the autopilot");
		
		setAutopilotState(false);
	}
	
	@Override
	public void handleSensorData(SensorData sensorData) {
		synchronized(aircraft){
			aircraft.updateFromSensorData(sensorData);
		}
	}
	
	/**
	 * Set the aircraft controller.
	 */
	public void setAircraftController(AircraftController aircraftController){
		this.aircraftController = aircraftController;
	}
	
	/**
	 * Get the aircraft controller object.
	 * 
	 * @return The current aircraft controller
	 */
	public AircraftController getAircraftController(){
		return aircraftController;
	}
	
	/**
	 * Set the autopilot state
	 * 
	 * @param state The autopilot state
	 */
	public void setAutopilotState(boolean state){
		running = state;
	}
	
	/**
	 * Check if the autopilot is active
	 * 
	 * @return The autopilot state
	 */
	public boolean isAutopilotActive(){
		return running;
	}
}
