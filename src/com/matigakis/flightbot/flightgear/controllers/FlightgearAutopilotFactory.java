package com.matigakis.flightbot.flightgear.controllers;

import com.matigakis.flightbot.aircraft.controllers.AircraftController;
import com.matigakis.flightbot.aircraft.controllers.AutopilotLoader;
import com.matigakis.flightbot.flightgear.fdm.ControlsClient;

public class FlightgearAutopilotFactory {
	public static AircraftController getAutopilot(ControlsClient controlsClient, String autopilotname) throws InterruptedException{
		AutopilotLoader autopilotLoader = new JythonAutopilotLoader();
		
		AircraftController autopilotController = autopilotLoader.getAutopilot(autopilotname);
		
		FlightgearAircraftController autopilot = new FlightgearAircraftController(autopilotController, controlsClient);
		
		return autopilot;
	}
}
