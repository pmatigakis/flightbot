package com.matigakis.flightbot.aircraft.sensors;

import com.matigakis.fgcontrol.fdm.Accelerations;
import com.matigakis.fgcontrol.fdm.FDMData;

public class Gyroscope implements Sensor{
	protected double xRot;
	protected double yRot;
	protected double zRot;
	
	/**
	 * Reset the gyroscope 
	 */
	@Override
	public void reset() {
		setXRotation(0.0);
		setYRotation(0.0);
		setZRotation(0.0);
	}

	/**
	 * Update the gyroscope using data from the sensors.
	 * 
	 */
	@Override
	public void updateFromFDMData(FDMData fdmData) {
		Accelerations accelerations = fdmData.getAccelerations();
		
		setXRotation(accelerations.getRollRate());
		setYRotation(accelerations.getPitchRate());
		setZRotation(accelerations.getYawRate());
	}

	/**
	 * Set the rotation in the x axis.
	 * 
	 * @param rotation
	 */
	public void setXRotation(double rotation){
		xRot = rotation;
	}
	
	/**
	 * Set the rotation in the y axis.
	 * 
	 * @param rotation
	 */
	public void setYRotation(double rotation){
		yRot = rotation;
	}
	
	/**
	 * Set the rotation in the z axis.
	 * 
	 * @param rotation
	 */
	public void setZRotation(double rotation){
		zRot = rotation;
	}
	
	/**
	 * Get the rotation on x axis.
	 * 
	 * @return rotation
	 */
	public double getXRotation(){
		return xRot;
	}
	
	/**
	 * Get the rotation on y axis.
	 * 
	 * @return rotation
	 */
	public double getYRotation(){
		return yRot;
	}
	
	/**
	 * Get the rotation on z axis.
	 * 
	 * @return rotation
	 */
	public double getZRotation(){
		return zRot;
	}
}
