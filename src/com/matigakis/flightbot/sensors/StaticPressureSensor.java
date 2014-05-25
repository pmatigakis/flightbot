package com.matigakis.flightbot.sensors;

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
