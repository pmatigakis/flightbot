package com.matigakis.flightbot.aircraft.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.fgcontrol.sensors.SensorData;
import com.matigakis.fgcontrol.ControlsClient;
import com.matigakis.fgcontrol.SensorDataListener;

/**
 * The Autopilot class is used to interface the object that is controlling the aircraft
 * with the client that sends control commands to Flightgear.
 */
public class Autopilot extends Thread implements SensorDataListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(Autopilot.class);
	
	private AircraftController aircraftController;
	private final ControlsClient controlsClient;
	private final Aircraft aircraft;
	private volatile boolean running;
	
	public Autopilot(AircraftController aircraftController, ControlsClient controlsClient){
		super();
		
		this.aircraftController = aircraftController;
		this.controlsClient = controlsClient;
		 
		aircraft = new Aircraft();
	}
	
	@Override
	public void run() {
		LOGGER.info("Starting the autopilot");
		
		double dt = 0.05;
		long updateRate = (long) (dt * 1000000000);
		
		setAutopilotState(true);
		while(isAutopilotActive()){
			long timeSinceControlsUpdate = System.nanoTime();
			
			synchronized(aircraft){
				aircraftController.updateAircraftControls(aircraft);
				controlsClient.transmitControls(aircraft.getControls());
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
		
		LOGGER.info("The autopilot has terminated successfully");
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
