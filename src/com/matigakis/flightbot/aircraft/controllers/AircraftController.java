package com.matigakis.flightbot.aircraft.controllers;

import com.matigakis.flightbot.aircraft.Aircraft;

/**
 * The AircraftController must be implemented by any object that must be 
 * able to control the aircraft
 */
public interface AircraftController {
	void updateAircraftControls(Aircraft aircraft);
}
