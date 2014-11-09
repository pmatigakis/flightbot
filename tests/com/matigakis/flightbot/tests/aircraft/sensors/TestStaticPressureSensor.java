package com.matigakis.flightbot.tests.aircraft.sensors;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.matigakis.fgcontrol.fdm.Atmosphere;
import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.aircraft.sensors.StaticPressureSensor;

@RunWith(JUnit4.class)
public class TestStaticPressureSensor {
	private StaticPressureSensor staticPressureSensor;
	
	@Before
	public void setUp(){
		staticPressureSensor = new StaticPressureSensor();
	}
	
	@Test
	public void testUpdateFromSensorData(){		
		FDMData fdmData = new DummyFDMData();
		
		staticPressureSensor.updateFromFDMData(fdmData);
		Atmosphere atmosphere = fdmData.getAtmosphere();
		
		assertEquals(atmosphere.getStaticPressure(), staticPressureSensor.getPressure(), 0.0);
	}
}
