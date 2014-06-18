package com.matigakis.flightbot.aircraft.sensors;

/**
 * The SensorData object holds the data that were received from the sensors.
 */
public class SensorData {
	public double longitude;
	public double latitude;
	public double gpsAltitude;
	public double gpsAirspeed; 
	public double gpsHeading;
	
	public double xAcceleration;
	public double yAcceleration;
	public double zAcceleration;
	
	public double xRotation;
	public double yRotation;
	public double zRotation;
	
	public double dynamicPressure;
	public double staticPressure;
	
	public double temperature;
	
	public double xMagn;
	public double yMagn;
	public double zMagn;
	
	public double roll;
	public double pitch;
	
	public double altitude;
	public double airspeed; 
	public double heading;
	
	public double elevator;
	public double aileron;
	public double rudder;
	public double throttle;
	
	public double simulationTime;
	
	public SensorData(){
		longitude = 0.0;
		latitude = 0.0;
		gpsAltitude = 0.0;
		gpsAirspeed = 0.0; 
		gpsHeading = 0.0;
		
		xAcceleration = 0.0;
		yAcceleration = 0.0;
		zAcceleration = 0.0;
		
		xRotation = 0.0;
		yRotation = 0.0;
		zRotation = 0.0;
		
		dynamicPressure = 0.0;
		staticPressure = 0.0;
		
		temperature = 0.0;
		
		xMagn = 0.0;
		yMagn = 0.0;
		zMagn = 0.0;
		
		roll = 0.0;
		pitch = 0.0;
		
		altitude = 0.0;
		airspeed = 0.0; 
		heading = 0.0;
		
		elevator = 0.0;
		aileron = 0.0;
		rudder = 0.0;
		throttle = 0.0;
		
		simulationTime = 0.0;
	}
}
