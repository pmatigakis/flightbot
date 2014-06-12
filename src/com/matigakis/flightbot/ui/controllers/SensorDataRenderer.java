package com.matigakis.flightbot.ui.controllers;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.network.SensorDataListener;
import com.matigakis.flightbot.sensors.SensorData;
import com.matigakis.flightbot.ui.views.TelemetryView;

public class SensorDataRenderer extends Thread implements SensorDataListener{
	private static final Logger logger = LoggerFactory.getLogger(SensorDataRenderer.class);
	private LinkedBlockingQueue<SensorData> sensorDataQueue;
	private volatile boolean running;
	private TelemetryView telemetryView;
	private Aircraft aircraft;
	
	public SensorDataRenderer(TelemetryView telemetryView){
		super();
		
		sensorDataQueue = new LinkedBlockingQueue<SensorData>(10);
		this.telemetryView = telemetryView;
		aircraft = new Aircraft();
	}
	
	@Override
	public void handleSensorData(SensorData sensorData) {
		boolean result = sensorDataQueue.offer(sensorData);
		 
		if(!result){
			logger.debug("The sensor data queue is full. Ignoring last operation");
		}
	}

	@Override
	public void run() {
		SensorData sensorData;
		
		logger.info("The sensor data renderer has started");
		
		running = true;
		while(running){
			 sensorData = sensorDataQueue.poll();
			
			if(sensorData != null){
				aircraft.updateFromSensorData(sensorData);
			}
			
			telemetryView.updateView(aircraft);
		}
		
		//telemetryView.dispose();
		
		logger.info("The sensor data renderer has been terminated");
	}

	public void stopController(){
		logger.info("Terminating the sensor data renderer");
		
		running = false;
	}
}
