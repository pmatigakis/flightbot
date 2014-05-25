package com.matigakis.flightbot.sensors;

public abstract class PressureSensor implements Sensor{
	protected double pressure;

	@Override
	public void reset() {
		setPressure(0.0);
	}
	
	/**
	 * Set the pressure
	 * 
	 * @param pressure
	 */
	public void setPressure(double pressure){
		this.pressure = pressure;
	}
	
	/**
	 * Get the pressure.
	 * 
	 * @return pressure
	 */
	public double getPressure(){
		return pressure;
	}
}
