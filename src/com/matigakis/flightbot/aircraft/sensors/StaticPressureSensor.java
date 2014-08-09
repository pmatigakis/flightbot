package com.matigakis.flightbot.aircraft.sensors;

import com.matigakis.fgcontrol.sensors.SensorData;

/**
 * The StaticPressureSensor holds the pressure of the air around the aircraft.
 */
public class StaticPressureSensor extends PressureSensor{

	/**
	 * Update the pressure using data from the sensors. 
	 */
	@Override
	public void updateFromSensorData(SensorData sensorData) {
		setPressure(sensorData.staticPressure);
	}
}
