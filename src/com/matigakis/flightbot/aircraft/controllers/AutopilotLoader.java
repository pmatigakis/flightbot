package com.matigakis.flightbot.aircraft.controllers;


public interface AutopilotLoader {
	AircraftController getAutopilot(String autopilotName);
}