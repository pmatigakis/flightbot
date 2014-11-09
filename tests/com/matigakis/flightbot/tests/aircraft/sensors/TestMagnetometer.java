package com.matigakis.flightbot.tests.aircraft.sensors;

import static org.junit.Assert.*;

import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Before;

import com.matigakis.flightbot.aircraft.sensors.Magnetometer;
import com.matigakis.fgcontrol.fdm.FDMData;

@RunWith(JUnit4.class)
public class TestMagnetometer {
	private Magnetometer magnetometer;
	
	@Before
	public void setUp(){
		magnetometer = new Magnetometer();
	}
	
	@Test
	public void testUpdateFromSensorData(){
		FDMData fdmData = new DummyFDMData();
		
		magnetometer.updateFromFDMData(fdmData);
		
		assertEquals(0.0, magnetometer.getXAxis(), 0.0);
		assertEquals(0.0, magnetometer.getYAxis(), 0.0);
		assertEquals(0.0, magnetometer.getZAxis(), 0.0);
	}
}
