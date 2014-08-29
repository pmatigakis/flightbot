package com.matigakis.flightbot.fdm;

import com.matigakis.flightbot.aircraft.Aircraft;

public interface FDM {
	void init(Aircraft aircraft);
	void updateAircraftState(Aircraft aircraft);
}
