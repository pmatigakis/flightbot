package com.matigakis.flightbot.aircraft.sensors;

import com.matigakis.fgcontrol.sensors.SensorData;

public class PitotTube extends PressureSensor {
	@Override
	public void updateFromSensorData(SensorData sensorData) {
		setPressure(sensorData.dynamicPressure);
	}
}
