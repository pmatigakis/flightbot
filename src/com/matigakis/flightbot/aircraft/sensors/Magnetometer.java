package com.matigakis.flightbot.aircraft.sensors;

import com.matigakis.fgcontrol.sensors.SensorData;

/**
 * The Magnetometer objects contains measurements about the magnetic field arount the aircraft
 */
public class Magnetometer implements Sensor{
	protected double xMagn;
	protected double yMagn;
	protected double zMagn;
	
	/**
	 * Reset the magnetometer.
	 */
	@Override
	public void reset() {
		setXAxis(0.0);
		setYAxis(0.0);
		setZAxis(0.0);
	}

	/**
	 * Update the magnetometer using data from the sensors.
	 */
	@Override
	public void updateFromSensorData(SensorData sensorData) {
		setXAxis(sensorData.xMagn);
		setYAxis(sensorData.yMagn);
		setZAxis(sensorData.zMagn);
	}

	/**
	 * Update the value of the x axis
	 * 
	 * @param measurement
	 */
	public void setXAxis(double measurement){
		xMagn = measurement;
	}
	
	/**
	 * Update the value of the y axis
	 * 
	 * @param measurement
	 */
	public void setYAxis(double measurement){
		yMagn = measurement;
	}
	
	/**
	 * Update the value of the z axis
	 * 
	 * @param measurement
	 */
	public void setZAxis(double measurement){
		zMagn = measurement;
	}
	
	/**
	 * Get the measurement on x axis
	 * 
	 * @return magnetic field measurement
	 */
	public double getXAxis(){
		return xMagn;
	}
	
	/**
	 * Get the measurement on y axis
	 * 
	 * @return magnetic field measurement
	 */
	public double getYAxis(){
		return yMagn;
	}
	
	/**
	 * Get the measurement on z axis
	 * 
	 * @return magnetic field measurement
	 */
	public double getZAxis(){
		return zMagn;
	}
}