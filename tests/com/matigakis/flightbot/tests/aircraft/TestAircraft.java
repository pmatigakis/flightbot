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
import com.matigakis.fgcontrol.fdm.Position;
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
		Position position = fdmData.getPosition();
		Velocities velocities = fdmData.getVelocities();
		Orientation orientation = fdmData.getOrientation();
		
		assertEquals(velocities.getCalibratedAirspeed(), gps.getAirspeed(), 0.0);
		assertEquals(position.getAltitude(), gps.getAltitude(), 0.0);
		assertEquals(orientation.getHeading(), gps.getHeading(), 0.0);
		assertEquals(position.getLatitude(), gps.getLatitude(), 0.0);
		assertEquals(position.getLongitude(), gps.getLongitude(), 0.0);
		
		Accelerometer accelerometer = aircraft.getAccelerometer();
		Accelerations accelerations = fdmData.getAccelerations();
		
		assertEquals(accelerations.getXAcceleration(), accelerometer.getXAcceleration(), 0.0);
		assertEquals(accelerations.getYAcceleration(), accelerometer.getYAcceleration(), 0.0);
		assertEquals(accelerations.getZAcceleration(), accelerometer.getZAcceleration(), 0.0);
		
		Gyroscope gyroscope = aircraft.getGyroscope();
		
		assertEquals(velocities.getRollRate(), gyroscope.getXRotation(), 0.0);
		assertEquals(velocities.getPitchRate(), gyroscope.getYRotation(), 0.0);
		assertEquals(velocities.getYawRate(), gyroscope.getZRotation(), 0.0);
		
		PitotTube pitotTube = aircraft.getPitotTube();
		Atmosphere atmosphere = fdmData.getAtmosphere();
		
		assertEquals(atmosphere.getTotalPressure(), pitotTube.getPressure(), 0.0);
		
		StaticPressureSensor staticPressureSensor = aircraft.getStaticPressureSensor();
		
		assertEquals(atmosphere.getStaticPressure(), staticPressureSensor.getPressure(), 0.0);
		
		TemperatureSensor temperatureSensor = aircraft.getTemperatureSensor();
		
		assertEquals(atmosphere.getTemperature(), temperatureSensor.getTemperature(), 0);
		
		Instrumentation instrumentation = aircraft.getInstrumentation();
		
		assertEquals(position.getAltitude(), instrumentation.getAltitude(), 0.0);
		assertEquals(velocities.getCalibratedAirspeed(), instrumentation.getAirspeed(), 0.0);
		assertEquals(orientation.getHeading(), instrumentation.getHeading(), 0.0);
		
		assertEquals(instrumentation.getRoll(), orientation.getRoll(), 0.0);
		assertEquals(instrumentation.getPitch(), orientation.getPitch(), 0.0);
	}
}
