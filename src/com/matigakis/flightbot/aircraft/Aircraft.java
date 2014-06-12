package com.matigakis.flightbot.aircraft;

import com.matigakis.flightbot.sensors.Accelerometer;
import com.matigakis.flightbot.sensors.GPS;
import com.matigakis.flightbot.sensors.Gyroscope;
import com.matigakis.flightbot.sensors.Magnetometer;
import com.matigakis.flightbot.sensors.PitotTube;
import com.matigakis.flightbot.sensors.SensorData;
import com.matigakis.flightbot.sensors.StaticPressureSensor;
import com.matigakis.flightbot.sensors.TemperatureSensor;
import com.matigakis.flightbot.controls.Controls;

/**
 *  The Aircraft object contains references to the instruments and
 *  the controls of the aircraft.
 */
public class Aircraft {
	private boolean autopilotActive;
	private final GPS gps;
	private final Accelerometer accelerometer;
	private final Gyroscope gyroscope;
	private final Magnetometer magnetometer;
	private final PitotTube pitotTube;
	private final StaticPressureSensor staticPressureSensor;
	private final TemperatureSensor temperatureSensor;
	private final Controls controls;
	private final Orientation orientation;
	private final Instrumentation instrumentation;
	
	public Aircraft(){
		gps = new GPS();
		accelerometer = new Accelerometer();
		gyroscope = new Gyroscope();
		magnetometer = new Magnetometer();
		pitotTube = new PitotTube();
		staticPressureSensor = new StaticPressureSensor();
		temperatureSensor = new TemperatureSensor();
		
		controls = new Controls();
		
		orientation = new Orientation();
		instrumentation = new Instrumentation();
		
		autopilotActive = false;
	}
	
	/**
	 * Get the gps module.
	 * 
	 * @return gps module
	 */
	public GPS getGPS(){
		return gps;
	}
	
	/**
	 * Get the accelerometer module.
	 * 
	 * @return accelerometer
	 */
	public Accelerometer getAccelerometer(){
		return accelerometer;
	}
	
	/**
	 * Get the gyroscope module.
	 * 
	 * @return gyroscope
	 */
	public Gyroscope getGyrescope(){
		return gyroscope;
	}
	
	/**
	 * Get the magnetometer module.
	 * 
	 * @return magnetometer
	 */
	public Magnetometer getMagnetometer(){
		return magnetometer;
	}
	
	/**
	 * Get the pitot tube.
	 * 
	 * @return pitot tube 
	 */
	public PitotTube getPitotTube(){
		return pitotTube;
	}
	
	/**
	 * Get the static pressure sensor.
	 * 
	 * @return static pressure sensor
	 */
	public StaticPressureSensor getStaticPressureSensor(){
		return staticPressureSensor;
	}
	
	/**
	 * Get the temperature sensor.
	 * 
	 * @return temperature sensor
	 */
	public TemperatureSensor getTemperatureSensor(){
		return temperatureSensor;
	}
	
	/**
	 * Get the controls.
	 * 
	 * @return controls
	 */
	public Controls getControls(){
		return controls;
	}
	
	/**
	 * Return the calculated orientation of the aircraft.
	 * 
	 * @return orientation
	 */
	public Orientation getOrientation(){
		return orientation;
	}
	
	/**
	 * Return the airspeed, altitude and heading of the aircraft.
	 */
	public Instrumentation getInstrumentation(){
		return instrumentation;
	}
	
	/**
	 * Check if the autopilot is active.
	 * 
	 * @return
	 */
	public boolean isAutopilotActive(){
		return autopilotActive;
	}
	
	/**
	 * Enable or disable the autopilot.
	 * 
	 * @param state the state which the autopilot is to be set.
	 */
	public void setAutopilotState(boolean state){
		autopilotActive = state;
	}
	
	/**
	 * Update the aircraft state using sensor data.
	 * 
	 * @param sensorData sensor data
	 */
	public void updateFromSensorData(SensorData sensorData){
		gps.updateFromSensorData(sensorData);
		accelerometer.updateFromSensorData(sensorData);
		gyroscope.updateFromSensorData(sensorData);
		magnetometer.updateFromSensorData(sensorData);
		pitotTube.updateFromSensorData(sensorData);
		staticPressureSensor.updateFromSensorData(sensorData);
		temperatureSensor.updateFromSensorData(sensorData);
		instrumentation.updateFromSensorData(sensorData);
		orientation.updateFromSensordata(sensorData);
		controls.updateFromSensorData(sensorData);
	}
}
