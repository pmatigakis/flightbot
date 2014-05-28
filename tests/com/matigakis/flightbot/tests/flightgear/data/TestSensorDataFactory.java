package com.matigakis.flightbot.tests.flightgear.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.matigakis.flightbot.flightgear.data.SensorDataFactory;
import com.matigakis.flightbot.sensors.SensorData;
import com.matigakis.flightbot.tests.sensors.DummySensorData;

@RunWith(JUnit4.class)
public class TestSensorDataFactory {	
	@Test
	public void testCreateSensorDataFromString(){
		DummySensorData dummySensorData = new DummySensorData();
		
		String telemetryString =  
				dummySensorData.gpsAltitude + "\t" +
				dummySensorData.gpsAirspeed + "\t" +
				dummySensorData.latitude + "\t" +
				dummySensorData.longitude + "\t" +
				dummySensorData.gpsHeading + "\t" +
				dummySensorData.xAcceleration + "\t" +
				dummySensorData.yAcceleration + "\t" +
				dummySensorData.zAcceleration + "\t" +
				dummySensorData.xRotation + "\t" +
				dummySensorData.yRotation + "\t" +
				dummySensorData.zRotation + "\t" +
				dummySensorData.staticPressure + "\t" +
				dummySensorData.dynamicPressure + "\t" +
				dummySensorData.temperature + "\t" +
				dummySensorData.roll + "\t" + 
				dummySensorData.pitch + "\t" +
				dummySensorData.altitude + "\t" +
				dummySensorData.airspeed + "\t" +
				dummySensorData.heading;
		
		SensorData sensorData = SensorDataFactory.fromString(telemetryString);
		
		assertEquals(dummySensorData.gpsAltitude, sensorData.gpsAltitude, 0.0);
		assertEquals(dummySensorData.gpsAirspeed, sensorData.gpsAirspeed, 0.0);
		assertEquals(dummySensorData.latitude, sensorData.latitude, 0.0);
		assertEquals(dummySensorData.longitude, sensorData.longitude, 0.0);
		assertEquals(dummySensorData.gpsHeading, sensorData.gpsHeading, 0.0);
		assertEquals(dummySensorData.xAcceleration, sensorData.xAcceleration, 0.0);
		assertEquals(dummySensorData.yAcceleration, sensorData.yAcceleration, 0.0);
		assertEquals(dummySensorData.zAcceleration, sensorData.zAcceleration, 0.0);
		assertEquals(dummySensorData.xRotation, sensorData.xRotation, 0.0);
		assertEquals(dummySensorData.yRotation, sensorData.yRotation, 0.0);
		assertEquals(dummySensorData.zRotation, sensorData.zRotation, 0.0);
		assertEquals(dummySensorData.staticPressure, sensorData.staticPressure, 0.0);
		assertEquals(dummySensorData.dynamicPressure, sensorData.dynamicPressure, 0.0);
		assertEquals(dummySensorData.temperature, sensorData.temperature, 0.0);
		assertEquals(dummySensorData.roll, sensorData.roll, 0.0);
		assertEquals(dummySensorData.pitch, sensorData.pitch, 0.0);
		assertEquals(dummySensorData.altitude, sensorData.altitude, 0.0);
		assertEquals(dummySensorData.airspeed, sensorData.airspeed, 0.0);
		assertEquals(dummySensorData.heading, sensorData.heading, 0.0);
	}
}
