package com.matigakis.flightbot.aircraft;

import com.matigakis.fgcontrol.sensors.SensorData;

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
	
	/**
	 * Set the aircraft speed.
	 * 
	 * @param airspeed
	 */
	public void setAirspeed(double airspeed){
		this.airspeed = airspeed;
	}
	
	/**
	 * Set the aircraft altitude.
	 * 
	 * @param altitude
	 */
	public void setAltitude(double altitude){
		this.altitude = altitude;
	}
	
	/**
	 * Set the aircraft heading.
	 * 
	 * @param heading
	 */
	public void setHeading(double heading){
		this.heading = heading;
	}
	
	/**
	 * Get the aircraft's airspeed.
	 * 
	 * @return the aircraft's airspeed
	 */
	public double getAirspeed(){
		return airspeed;
	}
	
	/**
	 * Get the aircraft's altitude.
	 * 
	 * @return the aircraft's altitude
	 */
	public double getAltitude(){
		return altitude;
	}
	
	/**
	 * Get the aircraft's heading.
	 * 
	 * @return the aircraft's heading
	 */
	public double getHeading(){
		return heading;
	}
	
	/**
	 * Update the instrument using data from the SensorData object.
	 * 
	 * @param sensorData
	 */
	public void updateFromSensorData(SensorData sensorData){
		setAltitude(sensorData.altitude);
		setAirspeed(sensorData.airspeed);
		setHeading(sensorData.heading);
	}
}
