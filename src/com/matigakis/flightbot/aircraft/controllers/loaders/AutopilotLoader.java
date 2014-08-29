package com.matigakis.flightbot.aircraft.controllers.loaders;

import com.matigakis.flightbot.aircraft.controllers.Autopilot;

/**
 * the AutopilotLoader interface must be implemented by any object
 * that can be used to create new Autopilot objects.
 */
public interface AutopilotLoader {
	Autopilot getAutopilot();
}