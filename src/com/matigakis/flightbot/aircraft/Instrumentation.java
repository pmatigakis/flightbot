package com.matigakis.flightbot.aircraft;

import com.matigakis.flightbot.sensors.SensorData;

/**
 * The Instrumentation object holds information about like altitude, airspeed and 
 * heading of the aircraft.
 */
public class Instrumentation {
	private double airspeed;
	private double altitude;
	private double heading;
	
	public Instrumentation(){
		airspeed = 0.0;
		altitude = 0.0;
		heading = 0.0;
	}
	
	public void setAirspeed(double airspeed){
		this.airspeed = airspeed;
	}
	
	public void setAltitude(double altitude){
		this.altitude = altitude;
	}
	
	public void setHeading(double heading){
		this.heading = heading;
	}
	
	public double getAirspeed(){
		return airspeed;
	}
	
	public double getAltitude(){
		return altitude;
	}
	
	public double getHeading(){
		return heading;
	}
	
	public void updateFromSensorData(SensorData sensorData){
		setAltitude(sensorData.altitude);
		setAirspeed(sensorData.airspeed);
		setHeading(sensorData.heading);
	}
}
