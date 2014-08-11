package com.matigakis.flightbot.aircraft.controllers;

import com.matigakis.flightbot.aircraft.Aircraft;

public interface Autopilot {
	void updateControls(Aircraft aircraft);
}
