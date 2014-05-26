package com.matigakis.flightbot.tests.sensors;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Before;

import com.matigakis.flightbot.sensors.GPS;
import com.matigakis.flightbot.sensors.SensorData;

@RunWith(JUnit4.class)
public class TestGPS {
	private GPS gps;
	
	@Before
	public void setUp(){
		gps = new GPS();
	}
	
	@Test
	public void testUpdateFromSensorData(){
		SensorData sensorData = new SensorData();
		
		sensorData.latitude = 37.937056;
		sensorData.longitude = 23.94667;
		sensorData.gpsAltitude = 1000.0;
		sensorData.gpsAirspeed = 60.0;
		
		gps.updateFromSensorData(sensorData);
		
		assertEquals(37.937056, gps.getLatitude(), 0.0);
		assertEquals(23.94667, gps.getLongitude(), 0.0);
		assertEquals(1000.0, gps.getAltitude(), 0.0);
		assertEquals(60.0, gps.getAirspeed(), 0.0);
	}
}
