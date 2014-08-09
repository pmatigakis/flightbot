package com.matigakis.flightbot.tests.aircraft.sensors;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Before;

import com.matigakis.flightbot.aircraft.sensors.GPS;
import com.matigakis.fgcontrol.sensors.SensorData;

@RunWith(JUnit4.class)
public class TestGPS {
	private GPS gps;
	
	@Before
	public void setUp(){
		gps = new GPS();
	}
	
	@Test
	public void testUpdateFromSensorData(){
		SensorData sensorData = new DummySensorData();
				
		gps.updateFromSensorData(sensorData);
		
		assertEquals(sensorData.latitude, gps.getLatitude(), 0.0);
		assertEquals(sensorData.longitude, gps.getLongitude(), 0.0);
		assertEquals(sensorData.gpsAltitude, gps.getAltitude(), 0.0);
		assertEquals(sensorData.gpsAirspeed, gps.getAirspeed(), 0.0);
	}
}
