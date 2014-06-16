package com.matigakis.flightbot.ui.controllers;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

import javax.swing.JFileChooser;

import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.network.SensorDataListener;
import com.matigakis.flightbot.sensors.SensorData;
import com.matigakis.flightbot.ui.views.TelemetryView;

public class TelemetryViewController extends Thread implements SensorDataListener, TelemetryViewListener{
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

	@Override
	public void close() {
		telemetryView.dispatchEvent(new WindowEvent(telemetryView, WindowEvent.WINDOW_CLOSING));
	}

	@Override
	public void setAutopilotState(boolean autopilotState) {
		aircraft.setAutopilotState(autopilotState);
	}

	@Override
	public void setMapMarkers() {
		telemetryView.clearMarkers();
		
		LinkedList<MapMarker> markers = new LinkedList<MapMarker>();
		
		JFileChooser openFileDialog = new JFileChooser();
		
		int returnValue = openFileDialog.showOpenDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION){
			File f = openFileDialog.getSelectedFile();
			
			try{
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				
				String line;
				while((line = br.readLine()) != null){
					String[] values = line.split(",");
					
					double latitude= Double.parseDouble(values[0]);
					double longitude = Double.parseDouble(values[1]);
					
					MapMarkerDot marker = new MapMarkerDot(latitude, longitude);
					
					marker.setVisible(true);
					marker.setColor(Color.blue);
					marker.setBackColor(Color.blue);
					
					markers.add(marker);
				}
				
				br.close();
				
			}catch(Exception ex){
				logger.error("Failed to load markers", ex);
			}
		}
		
		telemetryView.addMapMarkers(markers);
	}

	@Override
	public void clearMapMerkers() {
		telemetryView.clearMarkers();
	}
	
	public boolean getAutopilotState() {
		return aircraft.isAutopilotActive();
	}
}
