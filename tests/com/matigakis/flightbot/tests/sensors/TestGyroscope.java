package com.matigakis.flightbot.tests.sensors;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.matigakis.flightbot.sensors.Gyroscope;
import com.matigakis.flightbot.sensors.SensorData;
@RunWith(JUnit4.class)
public class TestGyroscope {
	private Gyroscope gyroscope;
	
	@Before
	public void setUp(){
		gyroscope = new Gyroscope();
	}
	
	@Test
	public void testUpdateFromSensordata(){
		double xRotation = 2.0;
		double yRotation = 1.5;
		double zRotation = 1.0;
		
		SensorData sensorData = new SensorData();
		
		sensorData.xRotation = xRotation;
		sensorData.yRotation = yRotation;
		sensorData.zRotation = zRotation;
		
		gyroscope.updateFromSensorData(sensorData);
		
		assertEquals(xRotation, gyroscope.getXRotation(), 0.0);
		assertEquals(yRotation, gyroscope.getYRotation(), 0.0);
		assertEquals(zRotation, gyroscope.getZRotation(), 0.0);
	}
}
