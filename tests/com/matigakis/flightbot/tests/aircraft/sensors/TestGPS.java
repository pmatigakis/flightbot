package com.matigakis.flightbot.tests.aircraft.sensors;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Before;

import com.matigakis.flightbot.aircraft.sensors.GPS;
import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.fgcontrol.fdm.Location;

@RunWith(JUnit4.class)
public class TestGPS {
	private GPS gps;
	
	@Before
	public void setUp(){
		gps = new GPS();
	}
	
	@Test
	public void testUpdateFromSensorData(){
		FDMData fdmData = new DummyFDMData();
				
		gps.updateFromFDMData(fdmData);
		Location location = fdmData.getLocation();
		
		assertEquals(location.getLatitude(), gps.getLatitude(), 0.0);
		assertEquals(location.getLongitude(), gps.getLongitude(), 0.0);
		assertEquals(location.getAltitude(), gps.getAltitude(), 0.0);
		assertEquals(location.getAirspeed(), gps.getAirspeed(), 0.0);
	}
}
