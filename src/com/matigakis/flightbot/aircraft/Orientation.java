package com.matigakis.flightbot.aircraft;

import com.matigakis.fgcontrol.sensors.SensorData;

/**
 * The Orientation object holds information about the orientation of the aircraft.
 */
public class Orientation {
	private double roll;
	private double pitch;
	
	public Orientation(){
		roll = 0.0;
		pitch = 0.0;
	}
	
	/**
	 * Set the aircraft's roll angle;
	 * 
	 * @param roll
	 */
	public void setRoll(double roll){
		this.roll = roll;
	}
	
	/**
	 * Set the aircraft's pitch angle.
	 * 
	 * @param pitch
	 */
	public void setPitch(double pitch){
		this.pitch = pitch;
	}
	
	/**
	 * Get the aircraft's roll angle. 
	 * 
	 * @return roll angle
	 */
	public double getRoll(){
		return roll;
	}
	
	/**
	 * Get aircraft's pitch angle.
	 * 
	 * @return pitch angle
	 */
	public double getPitch(){
		return pitch;
	}
	
	/**
	 * Update the instrument using data from the SensorData object.
	 * 
	 * @param sensorData
	 */
	public void updateFromSensordata(SensorData sensorData){
		roll = sensorData.roll;
		pitch = sensorData.pitch;
	}
}
