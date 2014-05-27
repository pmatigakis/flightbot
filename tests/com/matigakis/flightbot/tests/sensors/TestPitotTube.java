package com.matigakis.flightbot.tests.sensors;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.matigakis.flightbot.sensors.PitotTube;
import com.matigakis.flightbot.sensors.SensorData;

@RunWith(JUnit4.class)
public class TestPitotTube {
	private PitotTube pitotTube;
	
	@Before
	public void setUp(){
		pitotTube = new PitotTube(); 
	}
	
	@Test
	public void testUpdateFromSensorData(){
		SensorData sensorData = new DummySensorData();
		
		pitotTube.updateFromSensorData(sensorData);
		
		assertEquals(sensorData.dynamicPressure, pitotTube.getPressure(), 0.0);
	}
}
