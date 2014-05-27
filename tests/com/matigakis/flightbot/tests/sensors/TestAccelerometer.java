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
		SensorData sensorData = new DummySensorData();
				
		accelerometer.updateFromSensorData(sensorData);
		
		assertEquals(sensorData.xAcceleration, accelerometer.getXAcceleration(), 0.0);
		assertEquals(sensorData.yAcceleration, accelerometer.getYAcceleration(), 0.0);
		assertEquals(sensorData.zAcceleration, accelerometer.getZAcceleration(), 0.0);
	}
}
