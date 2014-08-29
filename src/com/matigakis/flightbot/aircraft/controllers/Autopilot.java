package com.matigakis.flightbot.aircraft.controllers;

import com.matigakis.flightbot.aircraft.Aircraft;

/**
 * All autopilots should implement the Autopilot interface
 */
public interface Autopilot {
	void updateControls(Aircraft aircraft);
}
