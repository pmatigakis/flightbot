package com.matigakis.flightbot.aircraft.sensors;

import com.matigakis.fgcontrol.sensors.SensorData;

/**
 * The Sensor interface defines the operations every sensor must provide.
 */
public interface Sensor {
	void reset();
	void updateFromSensorData(SensorData sensorData);
}
