package com.matigakis.flightbot.tests.sensors;

import com.matigakis.flightbot.sensors.SensorData;

public class DummySensorData extends SensorData{
	public DummySensorData(){
		super();
		
		xAcceleration = 0.5; 
		yAcceleration = 0.3;
		zAcceleration = 30.0;
		
		latitude = 37.937056;
		longitude = 23.94667;
		gpsAltitude = 1000.0;
		gpsAirspeed = 60.0;
		gpsHeading = 170.0;
		
		xRotation = 2.0;
		yRotation = 1.5;
		zRotation = 1.0;
		
		dynamicPressure = 31.5;
		staticPressure = 29.8;
		temperature = 26.5;
		
		xMagn = 1.1;
		yMagn = 2.2;
		zMagn = 3.3;
		
		roll = 10.5;
		pitch = 12.5;
		
		altitude = 1010.5;
		airspeed = 65.4;
		heading = 172.1;
	}
}
