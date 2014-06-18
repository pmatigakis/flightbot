package com.matigakis.flightbot.aircraft.sensors;

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
	public void updateFromSensorData(SensorData sensorData) {
		setTemperature(sensorData.temperature);
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
