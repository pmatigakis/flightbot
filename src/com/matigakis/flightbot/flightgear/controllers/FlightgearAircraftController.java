package com.matigakis.flightbot.flightgear.controllers;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.controllers.AircraftController;
import com.matigakis.flightbot.flightgear.fdm.ControlsClient;

public class FlightgearAircraftController implements AircraftController{
	private final AircraftController autopilot;
	private final ControlsClient controlsClient;
	
	public FlightgearAircraftController(AircraftController autopilot, ControlsClient controlsClient){
		this.autopilot = autopilot;
		this.controlsClient = controlsClient;
	}
	
	public void openConnection() throws InterruptedException{
		controlsClient.openConnection();
	}
	
	public void closeConnection(){
		controlsClient.closeConnection();
	}
	
	@Override
	public void updateAircraftControls(Aircraft aircraft) {
		autopilot.updateAircraftControls(aircraft);
		
		controlsClient.transmitControls(aircraft.getControls());
	}
}
