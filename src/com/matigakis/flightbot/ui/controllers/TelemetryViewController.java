package com.matigakis.flightbot.ui.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.network.SensorDataListener;
import com.matigakis.flightbot.sensors.SensorData;
import com.matigakis.flightbot.ui.views.TelemetryView;

public class TelemetryViewController extends Thread implements SensorDataListener{
	private static final Logger logger = LoggerFactory.getLogger(TelemetryViewController.class);
	private volatile boolean running;
	private TelemetryView telemetryView;
	private final Aircraft aircraft;
	
	private SensorData sensorData;
	
	public TelemetryViewController(TelemetryView telemetryView){
		super();
		
		this.telemetryView = telemetryView;
		aircraft = new Aircraft();
		
		sensorData = new SensorData();
	}
	
	@Override
	public void handleSensorData(SensorData sensorData) {
		this.sensorData = sensorData;
	}

	@Override
	public void run() {
		logger.info("The sensor data renderer has started");
		
		running = true;
		while(running){
			aircraft.updateFromSensorData(sensorData);
			
			telemetryView.updateView(aircraft);
		}
		
		logger.info("The sensor data renderer has been terminated");
	}

	public void stopController(){
		logger.info("Terminating the sensor data renderer");
		
		running = false;
	}
	
	public void setTelemetryView(TelemetryView telemetryView){
		this.telemetryView = telemetryView;
	}
	
	public TelemetryView getTelemetryView(){
		return telemetryView;
	}
}
