package com.matigakis.flightbot.aircraft.sensors;

import com.matigakis.fgcontrol.fdm.FDMData;;

/**
 * The TemperatureSensor measures the air temperature.
 */
public class TemperatureSensor implements Sensor{
	protected double temperature;
	
	/**
	 * Reset the temperature sensor, 
	 */
	@Override
	public void reset() {
		setTemperature(0.0);
	}

	/**
	 * Update the temperature sensor using data from the sensors.
	 */
	@Override
	public void updateFromFDMData(FDMData fdmData) {
		setTemperature(fdmData.getAtmosphere().getTemperature());
	}

	/**
	 * Set the temperature
	 * 
	 * @param temperature
	 */
	public void setTemperature(double temperature){
		this.temperature = temperature;
	}
	
	/**
	 * Get the temperature
	 * 
	 * @return temperature
	 */
	public double getTemperature(){
		return temperature;
	}
}
