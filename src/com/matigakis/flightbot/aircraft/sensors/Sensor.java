package com.matigakis.flightbot.aircraft.sensors;

/**
 * The Sensor interface defines the operations every sensor must provide.
 */
public interface Sensor {
	void reset();
	void updateFromSensorData(SensorData sensorData);
}
