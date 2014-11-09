package com.matigakis.flightbot.tests.aircraft.sensors;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.matigakis.fgcontrol.fdm.Atmosphere;
import com.matigakis.fgcontrol.fdm.FDMData;
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
		FDMData fdmData = new DummyFDMData();
		
		temperatureSensor.updateFromFDMData(fdmData);
		Atmosphere atmosphere = fdmData.getAtmosphere();
		
		assertEquals(atmosphere.getTemperature(), temperatureSensor.getTemperature(), 0);
	}
}
