package com.matigakis.flightbot.tests.aircraft.sensors;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.matigakis.flightbot.aircraft.sensors.PitotTube;
import com.matigakis.fgcontrol.fdm.Atmosphere;
import com.matigakis.fgcontrol.fdm.FDMData;

@RunWith(JUnit4.class)
public class TestPitotTube {
	private PitotTube pitotTube;
	
	@Before
	public void setUp(){
		pitotTube = new PitotTube(); 
	}
	
	@Test
	public void testUpdateFromSensorData(){
		FDMData fdmData = new DummyFDMData();
		
		pitotTube.updateFromFDMData(fdmData);
		Atmosphere atmosphere = fdmData.getAtmosphere();
		
		assertEquals(atmosphere.getTotalPressure(), pitotTube.getPressure(), 0.0);
	}
}
