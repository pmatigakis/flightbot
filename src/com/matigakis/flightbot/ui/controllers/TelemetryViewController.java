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
import com.matigakis.fgcontrol.sensors.SensorData;
import com.matigakis.fgcontrol.SensorDataListener;
import com.matigakis.flightbot.ui.views.TelemetryView;

/**
 * The TelemetryViewController is responsible for the rendering of sensor data
 * to the telemetry view.
 */
public class TelemetryViewController extends Thread implements SensorDataListener, TelemetryViewListener{
	private static final Logger logger = LoggerFactory.getLogger(TelemetryViewController.class);
	private volatile boolean running;
	private final TelemetryView telemetryView;
	private final Aircraft aircraft;
	
	public TelemetryViewController(TelemetryView telemetryView){
		super();
		
		this.telemetryView = telemetryView;
		aircraft = new Aircraft();
	}
	
	@Override
	public void handleSensorData(SensorData sensorData) {
		synchronized(aircraft){
			aircraft.updateFromSensorData(sensorData);
		}
	}

	@Override
	public void run() {
		logger.debug("The sensor data renderer has started");
		
		running = true;
		while(running){
			synchronized(aircraft){
				telemetryView.updateView(aircraft);
			}
		}
		
		logger.debug("The sensor data renderer has been terminated");
	}

	/**
	 * Stop the controller
	 */
	public void stopController(){
		logger.debug("Terminating the sensor data renderer");
		
		running = false;
	}

	/*
	public void setTelemetryView(TelemetryView telemetryView){
		this.telemetryView = telemetryView;
	}
	
	public TelemetryView getTelemetryView(){
		return telemetryView;
	}
	*/

	/**
	 * Close the view
	 */
	@Override
	public void close() {
		telemetryView.dispatchEvent(new WindowEvent(telemetryView, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * Add markers to the view
	 */
	@Override
	public void setMapMarkers() {		
		JFileChooser openFileDialog = new JFileChooser();
		
		int returnValue = openFileDialog.showOpenDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION){
			telemetryView.clearMarkers();
			
			File f = openFileDialog.getSelectedFile();
			
			LinkedList<MapMarker> markers = new LinkedList<MapMarker>();
			
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
			
			telemetryView.addMapMarkers(markers);
		}
	}

	/**
	 * Remove all markers from the view
	 */
	@Override
	public void clearMapMerkers() {
		telemetryView.clearMarkers();
	}
	
	/**
	 * Get the state of the autopilot
	 * 
	 * @return The state of the autopilot
	 */
	public boolean getAutopilotState() {
		return aircraft.isAutopilotActive();
	}
}
