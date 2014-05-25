package com.matigakis.flightbot.flightgear.fdm;

import com.matigakis.flightbot.sensors.SensorData;

/**
 * This interface must be implemented by any object that is able to
 * receive sensor data.
 */
public interface SensorDataReceiver {
	void addSensorDataListener(SensorDataListener sensordataListener);
	void removeSensorDataListener(SensorDataListener sensordataListener);
	void notifyListeners(SensorData sensorData);
}
