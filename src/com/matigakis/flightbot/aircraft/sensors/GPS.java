package com.matigakis.flightbot.aircraft.sensors;

import com.matigakis.fgcontrol.sensors.SensorData;

/**
 * The GPS object provides information about the aircraft's position and altitude.
 */
public class GPS implements Sensor{
	private double longitude;
	private double latitude;
	private double altitude;
	private double airspeed;
	private double heading;
	
	/**
	 * Reset the GPS module.
	 */
	@Override
	public void reset(){
		longitude = 0.0;
		latitude = 0.0;
		altitude = 0.0;
		airspeed = 0.0;
		heading = 0.0;
	}
	
	/**
	 * Update the GPS data using information from the SensorData object.
	 */
	@Override
	public void updateFromSensorData(SensorData sensorData){
		setLongitude(sensorData.longitude);
		setLatitude(sensorData.latitude);
		setAltitude(sensorData.gpsAltitude);
		setAirspeed(sensorData.gpsAirspeed);
		setHeading(sensorData.gpsHeading);
	}
	
	/**
	 * Get the longitude.
	 * 
	 * @return longitude
	 */
	public double getLongitude(){
		return longitude;
	}
	
	/**
	 * Get the latitude
	 * 
	 * @return latitude
	 */
	public double getLatitude(){
		return latitude;
	}
	
	/**
	 * Get the altitude
	 * 
	 * @return altitude
	 */
	public double getAltitude(){
		return altitude;
	}
	
	/**
	 * Get the airspeed
	 * 
	 * @return airspeed
	 */
	public double getAirspeed(){
		return airspeed;
	}
	
	public double getHeading(){
		return heading;
	}
	
	/**
	 * Set the current latitude.
	 * 
	 * @param latitude
	 */
	public void setLatitude(double latitude){
		if (latitude > 90.0 || latitude < -90.0){
			throw new IllegalArgumentException("Latitude was " + latitude);
		}
		
		this.latitude = latitude;
	}
	
	/**
	 * Set the current longitude.
	 * 
	 * @param longitude
	 */
	public void setLongitude(double longitude){
		if (longitude > 180.0 || longitude < -180.0){
			throw new IllegalArgumentException("Longituda was " + longitude);
		}
		
		this.longitude = longitude;
	}
	
	/**
	 * Set the current altitude.
	 * 
	 * @param altitude
	 */
	public void setAltitude(double altitude){
		if (altitude < 0.0){
			throw new IllegalArgumentException("Altitude was " + altitude);
		}
		
		this.altitude = altitude;
	}
	
	/**
	 * Set the airspeed.
	 * 
	 * @param airspeed
	 */
	public void setAirspeed(double airspeed){
		this.airspeed = airspeed;
	}
	
	public void setHeading(double heading){
		if(heading < 0.0 || heading > 360.0){
			throw new IllegalArgumentException("Heading was " + heading); 
		}
		
		this.heading = heading;
	}
}
