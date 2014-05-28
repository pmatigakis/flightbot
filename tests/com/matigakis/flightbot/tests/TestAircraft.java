package com.matigakis.flightbot.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.Instrumentation;
import com.matigakis.flightbot.aircraft.Orientation;
import com.matigakis.flightbot.sensors.Accelerometer;
import com.matigakis.flightbot.sensors.GPS;
import com.matigakis.flightbot.sensors.Gyroscope;
import com.matigakis.flightbot.sensors.PitotTube;
import com.matigakis.flightbot.sensors.SensorData;
import com.matigakis.flightbot.sensors.StaticPressureSensor;
import com.matigakis.flightbot.sensors.TemperatureSensor;
import com.matigakis.flightbot.tests.sensors.DummySensorData;

@RunWith(JUnit4.class)
public class TestAircraft {
	private Aircraft aircraft;
	
	@Before
	public void setUp(){
		aircraft = new Aircraft();
	}
	
	@Test
	public void testUpdateFromSensordata(){
		SensorData sensorData = new DummySensorData();
		
		aircraft.updateFromSensorData(sensorData);
		
		GPS gps = aircraft.getGPS();
		
		assertEquals(sensorData.gpsAirspeed, gps.getAirspeed(), 0.0);
		assertEquals(sensorData.gpsAltitude, gps.getAltitude(), 0.0);
		assertEquals(sensorData.gpsHeading, gps.getHeading(), 0.0);
		assertEquals(sensorData.latitude, gps.getLatitude(), 0.0);
		assertEquals(sensorData.longitude, gps.getLongitude(), 0.0);
		
		Accelerometer accelerometer = aircraft.getAccelerometer();
		
		assertEquals(sensorData.xAcceleration, accelerometer.getXAcceleration(), 0.0);
		assertEquals(sensorData.yAcceleration, accelerometer.getYAcceleration(), 0.0);
		assertEquals(sensorData.zAcceleration, accelerometer.getZAcceleration(), 0.0);
		
		Gyroscope gyroscope = aircraft.getGyrescope();
		
		assertEquals(sensorData.xRotation, gyroscope.getXRotation(), 0.0);
		assertEquals(sensorData.yRotation, gyroscope.getYRotation(), 0.0);
		assertEquals(sensorData.zRotation, gyroscope.getZRotation(), 0.0);
		
		PitotTube pitotTube = aircraft.getPitotTube();
		
		assertEquals(sensorData.dynamicPressure, pitotTube.getPressure(), 0.0);
		
		StaticPressureSensor staticPressureSensor = aircraft.getStaticPressureSensor();
		
		assertEquals(sensorData.staticPressure, staticPressureSensor.getPressure(), 0.0);
		
		TemperatureSensor temperatureSensor = aircraft.getTemperatureSensor();
		
		assertEquals(sensorData.temperature, temperatureSensor.getTemperature(), 0);
		
		Instrumentation instrumentation = aircraft.getInstrumentation();
		
		assertEquals(sensorData.altitude, instrumentation.getAltitude(), 0.0);
		assertEquals(sensorData.airspeed, instrumentation.getAirspeed(), 0.0);
		assertEquals(sensorData.heading, instrumentation.getHeading(), 0.0);
		
		Orientation orientation = aircraft.getOrientation();
		
		assertEquals(sensorData.roll, orientation.getRoll(), 0.0);
		assertEquals(sensorData.pitch, orientation.getPitch(), 0.0);
	}
}
