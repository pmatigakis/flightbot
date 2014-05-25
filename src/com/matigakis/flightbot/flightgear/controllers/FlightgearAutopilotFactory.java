package com.matigakis.flightbot.flightgear.controllers;

import com.matigakis.flightbot.aircraft.controllers.AircraftController;
import com.matigakis.flightbot.aircraft.controllers.AutopilotLoader;
import com.matigakis.flightbot.flightgear.fdm.ControlsClient;

public class FlightgearAutopilotFactory {
	public static AircraftController getAutopilot(String host, int controlsPort, String autopilotname) throws InterruptedException{
		AutopilotLoader autopilotLoader = new JythonAutopilotLoader();
		
		AircraftController autopilotController = autopilotLoader.getAutopilot(autopilotname);
		
		ControlsClient controlsClient = new ControlsClient(host, controlsPort);
		
		FlightgearAircraftController autopilot = new FlightgearAircraftController(autopilotController, controlsClient);
		
		autopilot.openConnection();
		
		return autopilot;
	}
}
