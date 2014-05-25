package com.matigakis.flightbot.ui.views;

import com.matigakis.flightbot.aircraft.Aircraft;

public interface AircraftDataRenderer {
	void updateView(Aircraft aircraft);
	boolean rendererActive();
}
