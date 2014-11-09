package com.matigakis.flightbot.tests.aircraft.sensors;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.matigakis.flightbot.aircraft.sensors.Gyroscope;
import com.matigakis.fgcontrol.fdm.Accelerations;
import com.matigakis.fgcontrol.fdm.FDMData;

@RunWith(JUnit4.class)
public class TestGyroscope {
	private Gyroscope gyroscope;
	
	@Before
	public void setUp(){
		gyroscope = new Gyroscope();
	}
	
	@Test
	public void testUpdateFromSensordata(){
		FDMData fdmData = new DummyFDMData();
				
		gyroscope.updateFromFDMData(fdmData);
		Accelerations accelerations = fdmData.getAccelerations();
		
		assertEquals(accelerations.getRollRate(), gyroscope.getXRotation(), 0.0);
		assertEquals(accelerations.getPitchRate(), gyroscope.getYRotation(), 0.0);
		assertEquals(accelerations.getYawRate(), gyroscope.getZRotation(), 0.0);
	}
}
