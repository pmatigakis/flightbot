package com.matigakis.flightbot.aircraft.sensors;

import com.matigakis.fgcontrol.fdm.Accelerations;
import com.matigakis.fgcontrol.fdm.FDMData;

/**
 * The Accelerometer object contains the accelerations measured by the sensors.
 */
public class Accelerometer implements Sensor{
	private double xAcceleration;
	private double yAcceleration;
	private double zAcceleration;
	
	/**
	 * Reset the accelerometer
	 */
	@Override
	public void reset() {
		setXAcceleration(0.0);
		setYAcceleration(0.0);
		setZAcceleration(0.0);
	}
	
	/**
	 * Update the accelerometer data using reading from the sensors.
	 */
	@Override
	public void updateFromFDMData(FDMData fdmData) {
		Accelerations accelerations = fdmData.getAccelerations();
		
		setXAcceleration(accelerations.getXAcceleration());
		setYAcceleration(accelerations.getYAcceleration());
		setZAcceleration(accelerations.getZAcceleration());
	}
	
	/**
	 * Set the acceleration on the x axis.
	 * 
	 * @param acceleration
	 */
	public void setXAcceleration(double acceleration){
		xAcceleration = acceleration;
	}
	
	/**
	 * Set the acceleration on the y axis.
	 * 
	 * @param acceleration
	 */
	public void setYAcceleration(double acceleration){
		yAcceleration = acceleration;
	}
	
	/**
	 * Set the acceleration on the z axis.
	 * 
	 * @param acceleration
	 */
	public void setZAcceleration(double acceleration){
		zAcceleration = acceleration;
	}
	
	/**
	 * Get the acceleration on the x axis.
	 * 
	 * @return acceleration on x axis
	 */
	public double getXAcceleration(){
		return xAcceleration;
	}
	
	/**
	 * Get the acceleration on the y axis.
	 * 
	 * @return acceleration on y axis
	 */
	public double getYAcceleration(){
		return yAcceleration;
	}
	
	/**
	 * Get the acceleration on the z axis.
	 * 
	 * @return acceleration on z axis
	 */
	public double getZAcceleration(){
		return zAcceleration;
	}
}
