package com.matigakis.flightbot.aircraft;

import com.matigakis.fgcontrol.fdm.Controls;
import com.matigakis.flightbot.aircraft.sensors.Accelerometer;
import com.matigakis.flightbot.aircraft.sensors.GPS;
import com.matigakis.flightbot.aircraft.sensors.Gyroscope;
import com.matigakis.flightbot.aircraft.sensors.Magnetometer;
import com.matigakis.flightbot.aircraft.sensors.PitotTube;
import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.aircraft.sensors.StaticPressureSensor;
import com.matigakis.flightbot.aircraft.sensors.TemperatureSensor;

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
	public Gyroscope getGyroscope(){
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
	 * Return the airspeed, altitude and heading of the aircraft.
	 */
	public Instrumentation getInstrumentation(){
		return instrumentation;
	}
	
	/**
	 * Check if the autopilot is active.
	 * 
	 * @return the autopilot state
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
	 * Update the aircraft state using fdm data.
	 * 
	 * @param fdmData fdm data
	 */
	public void updateFromFDMData(FDMData fdmData){
		gps.updateFromFDMData(fdmData);
		accelerometer.updateFromFDMData(fdmData);
		gyroscope.updateFromFDMData(fdmData);
		magnetometer.updateFromFDMData(fdmData);
		pitotTube.updateFromFDMData(fdmData);
		staticPressureSensor.updateFromFDMData(fdmData);
		temperatureSensor.updateFromFDMData(fdmData);
		instrumentation.updateFromFDMData(fdmData);
	}
}
