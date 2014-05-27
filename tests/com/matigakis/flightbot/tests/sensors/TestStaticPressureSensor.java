package com.matigakis.flightbot.tests.sensors;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.matigakis.flightbot.sensors.SensorData;
import com.matigakis.flightbot.sensors.StaticPressureSensor; 

@RunWith(JUnit4.class)
public class TestStaticPressureSensor {
	private StaticPressureSensor staticPressureSensor;
	
	@Before
	public void setUp(){
		staticPressureSensor = new StaticPressureSensor();
	}
	
	@Test
	public void testUpdateFromSensorData(){		
		SensorData sensorData = new DummySensorData();
		
		staticPressureSensor.updateFromSensorData(sensorData);
		
		assertEquals(sensorData.staticPressure, staticPressureSensor.getPressure(), 0.0);
	}
}
