package com.matigakis.flightbot.fdm;

import com.matigakis.flightbot.aircraft.Aircraft;

/**
 * Interface for the flight dynamics model objects
 */
public interface FDM {
	void updateAircraftState(Aircraft aircraft);
	void runSimulation(Aircraft aircraft);
}
