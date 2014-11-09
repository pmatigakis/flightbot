package com.matigakis.flightbot.tests.aircraft;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.Instrumentation;
import com.matigakis.fgcontrol.fdm.Accelerations;
import com.matigakis.fgcontrol.fdm.Atmosphere;
import com.matigakis.fgcontrol.fdm.Location;
import com.matigakis.fgcontrol.fdm.Orientation;
import com.matigakis.fgcontrol.fdm.Velocities;
import com.matigakis.flightbot.aircraft.sensors.Accelerometer;
import com.matigakis.flightbot.aircraft.sensors.GPS;
import com.matigakis.flightbot.aircraft.sensors.Gyroscope;
import com.matigakis.flightbot.aircraft.sensors.PitotTube;
import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.aircraft.sensors.StaticPressureSensor;
import com.matigakis.flightbot.aircraft.sensors.TemperatureSensor;
import com.matigakis.flightbot.tests.aircraft.sensors.DummyFDMData;

@RunWith(JUnit4.class)
public class TestAircraft {
	private Aircraft aircraft;
	
	@Before
	public void setUp(){
		aircraft = new Aircraft();
	}
	
	@Test
	public void testUpdateFromSensordata(){
		FDMData fdmData = new DummyFDMData();
		
		aircraft.updateFromFDMData(fdmData);
		
		GPS gps = aircraft.getGPS();
		Location location = fdmData.getLocation();
		
		assertEquals(location.getAirspeed(), gps.getAirspeed(), 0.0);
		assertEquals(location.getAltitude(), gps.getAltitude(), 0.0);
		assertEquals(location.getHeading(), gps.getHeading(), 0.0);
		assertEquals(location.getLatitude(), gps.getLatitude(), 0.0);
		assertEquals(location.getLongitude(), gps.getLongitude(), 0.0);
		
		Accelerometer accelerometer = aircraft.getAccelerometer();
		Accelerations accelerations = fdmData.getAccelerations();
		
		assertEquals(accelerations.getXAcceleration(), accelerometer.getXAcceleration(), 0.0);
		assertEquals(accelerations.getYAcceleration(), accelerometer.getYAcceleration(), 0.0);
		assertEquals(accelerations.getZAcceleration(), accelerometer.getZAcceleration(), 0.0);
		
		Gyroscope gyroscope = aircraft.getGyroscope();
		
		assertEquals(accelerations.getRollRate(), gyroscope.getXRotation(), 0.0);
		assertEquals(accelerations.getPitchRate(), gyroscope.getYRotation(), 0.0);
		assertEquals(accelerations.getYawRate(), gyroscope.getZRotation(), 0.0);
		
		PitotTube pitotTube = aircraft.getPitotTube();
		Atmosphere atmosphere = fdmData.getAtmosphere();
		
		assertEquals(atmosphere.getPitotTubePressure(), pitotTube.getPressure(), 0.0);
		
		StaticPressureSensor staticPressureSensor = aircraft.getStaticPressureSensor();
		
		assertEquals(atmosphere.getStaticPressure(), staticPressureSensor.getPressure(), 0.0);
		
		TemperatureSensor temperatureSensor = aircraft.getTemperatureSensor();
		
		assertEquals(atmosphere.getTemperature(), temperatureSensor.getTemperature(), 0);
		
		Instrumentation instrumentation = aircraft.getInstrumentation();
		Velocities velocities = fdmData.getVelocities();
		
		assertEquals(location.getAltitude(), instrumentation.getAltitude(), 0.0);
		assertEquals(velocities.getAirspeed(), instrumentation.getAirspeed(), 0.0);
		assertEquals(location.getHeading(), instrumentation.getHeading(), 0.0);
		
		Orientation orientation = aircraft.getOrientation();
		Orientation fdmOrientation = aircraft.getOrientation();
		
		assertEquals(fdmOrientation.getRoll(), orientation.getRoll(), 0.0);
		assertEquals(fdmOrientation.getPitch(), orientation.getPitch(), 0.0);
	}
}
