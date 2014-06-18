package com.matigakis.flightbot.aircraft.sensors;

public class PitotTube extends PressureSensor {
	@Override
	public void updateFromSensorData(SensorData sensorData) {
		setPressure(sensorData.dynamicPressure);
	}
}
