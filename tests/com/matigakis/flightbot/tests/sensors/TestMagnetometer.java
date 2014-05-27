package com.matigakis.flightbot.tests.sensors;

import static org.junit.Assert.*;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Before;

import com.matigakis.flightbot.sensors.Magnetometer;
import com.matigakis.flightbot.sensors.SensorData;

@RunWith(JUnit4.class)
public class TestMagnetometer {
	private Magnetometer magnetometer;
	
	@Before
	public void setUp(){
		magnetometer = new Magnetometer();
	}
	
	@Test
	public void testUpdateFromSensorData(){
		SensorData sensorData = new DummySensorData();
		
		magnetometer.updateFromSensorData(sensorData);
		
		assertEquals(sensorData.xMagn, magnetometer.getXAxis(), 0.0);
		assertEquals(sensorData.yMagn, magnetometer.getYAxis(), 0.0);
		assertEquals(sensorData.zMagn, magnetometer.getZAxis(), 0.0);
	}
}
