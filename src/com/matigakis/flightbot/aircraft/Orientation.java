package com.matigakis.flightbot.aircraft;

import com.matigakis.flightbot.sensors.SensorData;

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
	
	public void setRoll(double roll){
		this.roll = roll;
	}
	
	public void setPitch(double pitch){
		this.pitch = pitch;
	}
	
	public double getRoll(){
		return roll;
	}
	
	public double getPitch(){
		return pitch;
	}
	
	public void updateFromSensordata(SensorData sensorData){
		roll = sensorData.roll;
		pitch = sensorData.pitch;
	}
}
