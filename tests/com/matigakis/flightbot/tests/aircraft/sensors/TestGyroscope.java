package com.matigakis.flightbot.tests.aircraft.sensors;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.matigakis.flightbot.aircraft.sensors.Gyroscope;
import com.matigakis.flightbot.aircraft.sensors.SensorData;
@RunWith(JUnit4.class)
public class TestGyroscope {
	private Gyroscope gyroscope;
	
	@Before
	public void setUp(){
		gyroscope = new Gyroscope();
	}
	
	@Test
	public void testUpdateFromSensordata(){
		SensorData sensorData = new DummySensorData();
				
		gyroscope.updateFromSensorData(sensorData);
		
		assertEquals(sensorData.xRotation, gyroscope.getXRotation(), 0.0);
		assertEquals(sensorData.yRotation, gyroscope.getYRotation(), 0.0);
		assertEquals(sensorData.zRotation, gyroscope.getZRotation(), 0.0);
	}
}
