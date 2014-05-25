package com.matigakis.flightbot.fdm;

import com.matigakis.flightbot.aircraft.Aircraft;

/**
 * The FDM interface is the base for every implementation of a
 * flight dynamics model.
 */
public interface FDM {
	void run(Aircraft aircraft);
	void init(Aircraft aircraft);
}
