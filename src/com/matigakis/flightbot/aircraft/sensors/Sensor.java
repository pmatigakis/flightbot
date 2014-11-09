package com.matigakis.flightbot.aircraft.sensors;

import com.matigakis.fgcontrol.fdm.FDMData;

/**
 * The Sensor interface defines the operations every sensor must provide.
 */
public interface Sensor {
	void reset();
	void updateFromFDMData(FDMData FDMData);
}
