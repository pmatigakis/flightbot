package com.matigakis.flightbot.fdm;

import com.matigakis.flightbot.aircraft.Aircraft;

/**
 * The FDM interface should be implemented by all flight dynamics models 
 */
public interface FDM {
	void init(Aircraft aircraft);
	void updateAircraftState(Aircraft aircraft);
}
