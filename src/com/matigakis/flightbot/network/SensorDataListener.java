package com.matigakis.flightbot.network;

import com.matigakis.flightbot.sensors.SensorData;

/**
 * Every object that needs to be informed when new sensor data have
 * been received, needs to implement this interface.
 */
public interface SensorDataListener {
	void handleSensorData(SensorData sensorData); 
}
