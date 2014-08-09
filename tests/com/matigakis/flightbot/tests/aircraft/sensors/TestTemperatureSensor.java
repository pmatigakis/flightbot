package com.matigakis.flightbot.tests.aircraft.sensors;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.matigakis.fgcontrol.sensors.SensorData;
import com.matigakis.flightbot.aircraft.sensors.TemperatureSensor;

@RunWith(JUnit4.class)
public class TestTemperatureSensor {
	private TemperatureSensor temperatureSensor;
	
	@Before
	public void setUp(){
		temperatureSensor = new TemperatureSensor();
	}
	
	@Test
	public void testUpdateFromSensorData(){
		SensorData sensorData = new DummySensorData();
		
		temperatureSensor.updateFromSensorData(sensorData);
		
		assertEquals(sensorData.temperature, temperatureSensor.getTemperature(), 0);
	}
}
