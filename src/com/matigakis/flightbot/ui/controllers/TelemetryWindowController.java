package com.matigakis.flightbot.ui.controllers;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;

import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.ui.views.TelemetryView;

/**
 * The TelemetryViewController is responsible for the rendering of sensor data
 * to the telemetry view.
 */
public class TelemetryWindowController implements TelemetryViewController{
	private static final Logger logger = LoggerFactory.getLogger(TelemetryWindowController.class);
	//private final Aircraft aircraft;
	private List<TelemetryView> telemetryViews;
	private List<MapMarker> markers;
	
	public TelemetryWindowController(){	
		//aircraft = new Aircraft();
		telemetryViews = new LinkedList<TelemetryView>();
		
		markers = new LinkedList<MapMarker>();
	}

	/**
	 * Close the view
	 */
	@Override
	public void close() {
		//telemetryView.dispatchEvent(new WindowEvent(telemetryView, WindowEvent.WINDOW_CLOSING));
		for(TelemetryView telemetryView: telemetryViews){
			telemetryView.close();
		}
	}

	@Override
	public void updateView(Aircraft aircraft) {
		for(TelemetryView telemetryView: telemetryViews){
			telemetryView.updateTelemetry(aircraft);
		}
	}

	@Override
	public void addMarkers() {
		JFileChooser fileChooser = new JFileChooser();
		
		int selection = fileChooser.showOpenDialog(null);
		
		if(selection == fileChooser.APPROVE_OPTION){
			try {
				FileReader fr = new FileReader(fileChooser.getSelectedFile());
				
				BufferedReader br = new BufferedReader(fr);
				
				while(true){
					String line = br.readLine();
					
					if(line != null){
						String[] data = line.split(",");
						if(data.length == 2){
							MapMarkerDot marker = new MapMarkerDot(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
							marker.setVisible(true);
							marker.setColor(Color.blue);
							marker.setBackColor(Color.blue);
							markers.add(marker);
							for(TelemetryView telemetryView: telemetryViews){
								telemetryView.addMarker(marker);
							}
						}else{
							logger.warn("Invalid marker data");
						}
					}else{
						break;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void clearmarkers() {
		for(MapMarker mapMarker: markers){
			for(TelemetryView telemetryView: telemetryViews){
				telemetryView.removeMarker(mapMarker);
			}
		}
		
		markers.clear();
	}

	@Override
	public void attachTelemetryView(TelemetryView telemetryView) {
		telemetryViews.add(telemetryView);
	}

	@Override
	public void detachTelemetryView(TelemetryView telemetryView) {
		telemetryViews.remove(telemetryView);
	}
}
