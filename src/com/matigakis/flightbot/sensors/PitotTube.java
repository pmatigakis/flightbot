package com.matigakis.flightbot.sensors;

public class PitotTube extends PressureSensor {
	@Override
	public void updateFromSensorData(SensorData sensorData) {
		setPressure(sensorData.dynamicPressure);
	}
}
