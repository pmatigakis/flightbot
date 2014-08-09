package com.matigakis.flightbot.network;

import com.matigakis.fgcontrol.sensors.SensorData;

/**
 * The SensorDataFactory is used to convert a telemetry String that was received from
 * flightgear into a SensorData object.
 */
public class SensorDataFactory {
	
	/**
	 * Create a SensorData object from a telemetry String.
	 * 
	 * @param data
	 * @return SensorData object
	 */
	public SensorData fromString(String data){
		String[] values = data.split("\t");
		
		SensorData sensorData = new SensorData();
		
		//GPS data
		sensorData.gpsAirspeed = Double.parseDouble(values[1]);
		sensorData.gpsAltitude = Double.parseDouble(values[0]);
		sensorData.latitude = Double.parseDouble(values[2]);
		sensorData.longitude = Double.parseDouble(values[3]);
		sensorData.gpsHeading = Double.parseDouble(values[4]);
		
		//Accelerometer data
		sensorData.xAcceleration = Double.parseDouble(values[5]);
		sensorData.yAcceleration = Double.parseDouble(values[6]);
		sensorData.zAcceleration = Double.parseDouble(values[7]);
		
		//Gyroscope data
		sensorData.xRotation = Double.parseDouble(values[8]);
		sensorData.yRotation = Double.parseDouble(values[9]);
		sensorData.zRotation = Double.parseDouble(values[10]);
		
		//Magnetometer data
		//TODO: add this
		
		//Atmospheric data
		sensorData.dynamicPressure = Double.parseDouble(values[12]);
		sensorData.staticPressure = Double.parseDouble(values[11]);
		sensorData.temperature = Double.parseDouble(values[13]);
		
		//orientation
		sensorData.roll = Double.parseDouble(values[14]);
		sensorData.pitch = Double.parseDouble(values[15]);
		
		//instrumentation
		sensorData.altitude = Double.parseDouble(values[16]);
		sensorData.airspeed = Double.parseDouble(values[17]);
		sensorData.heading = Double.parseDouble(values[18]);
		
		//simulation
		sensorData.simulationTime = Double.parseDouble(values[19]);
		
		//controls
		sensorData.elevator = Double.parseDouble(values[20]);
		sensorData.aileron = Double.parseDouble(values[21]);
		sensorData.rudder = Double.parseDouble(values[22]);
		sensorData.throttle = Double.parseDouble(values[23]);
		
		return sensorData;
	}
}
