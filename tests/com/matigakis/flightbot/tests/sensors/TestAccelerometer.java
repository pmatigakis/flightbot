package com.matigakis.flightbot.tests.sensors;

import static org.junit.Assert.*;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Before;

import com.matigakis.flightbot.sensors.Accelerometer;
import com.matigakis.flightbot.sensors.SensorData;

@RunWith(JUnit4.class)
public class TestAccelerometer {
	private Accelerometer accelerometer;
	
	@Before
	public void setUp(){
		accelerometer = new Accelerometer();
	}
	
	@Test
	public void testUpdateFromSensorData(){
		double xAcceleration = 0.5; 
		double yAcceleration = 0.3;
		double zAcceleration = 30.0;
		
		SensorData sensorData = new SensorData();
		
		sensorData.xAcceleration = xAcceleration;
		sensorData.yAcceleration = yAcceleration;
		sensorData.zAcceleration = zAcceleration;
		
		accelerometer.updateFromSensorData(sensorData);
		
		assertEquals(xAcceleration, accelerometer.getXAcceleration(), 0.0);
		assertEquals(yAcceleration, accelerometer.getYAcceleration(), 0.0);
		assertEquals(zAcceleration, accelerometer.getZAcceleration(), 0.0);
	}
}
