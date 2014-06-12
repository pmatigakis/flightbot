package com.matigakis.flightbot.aircraft.controllers.loaders;

import com.matigakis.flightbot.aircraft.controllers.AircraftController;


public interface AutopilotLoader {
	AircraftController getAutopilot(String autopilotName);
}