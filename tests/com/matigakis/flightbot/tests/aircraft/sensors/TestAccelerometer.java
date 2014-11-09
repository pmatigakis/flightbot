package com.matigakis.flightbot.tests.aircraft.sensors;

import static org.junit.Assert.*;

import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Before;

import com.matigakis.flightbot.aircraft.sensors.Accelerometer;
import com.matigakis.fgcontrol.fdm.Accelerations;
import com.matigakis.fgcontrol.fdm.FDMData;

@RunWith(JUnit4.class)
public class TestAccelerometer {
	private Accelerometer accelerometer;
	
	@Before
	public void setUp(){
		accelerometer = new Accelerometer();
	}
	
	@Test
	public void testUpdateFromSensorData(){		
		FDMData fdmData = new DummyFDMData();
				
		accelerometer.updateFromFDMData(fdmData);
		Accelerations accelerations = fdmData.getAccelerations();
		
		assertEquals(accelerations.getXAcceleration(), accelerometer.getXAcceleration(), 0.0);
		assertEquals(accelerations.getYAcceleration(), accelerometer.getYAcceleration(), 0.0);
		assertEquals(accelerations.getZAcceleration(), accelerometer.getZAcceleration(), 0.0);
	}
}
