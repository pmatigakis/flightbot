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

import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.ui.views.FDMDataView;

/**
 * The TelemetryViewController is responsible for the rendering of sensor data
 * to the telemetry view.
 */
public class FDMDataWindowController implements FDMDataViewController{
	private static final Logger logger = LoggerFactory.getLogger(FDMDataWindowController.class);
	private Aircraft aircraft;
	private List<FDMDataView> telemetryViews;
	private List<MapMarker> markers;
	
	public FDMDataWindowController(){	
		aircraft = new Aircraft();
		
		telemetryViews = new LinkedList<FDMDataView>();
		
		markers = new LinkedList<MapMarker>();
	}

	@Override
	public void updateView(FDMData fdmData) {
		aircraft.updateFromFDMData(fdmData);
		
		for(FDMDataView telemetryView: telemetryViews){
			telemetryView.updateFDMData(fdmData);
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
							for(FDMDataView telemetryView: telemetryViews){
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
			for(FDMDataView telemetryView: telemetryViews){
				telemetryView.removeMarker(mapMarker);
			}
		}
		
		markers.clear();
	}

	@Override
	public void attachTelemetryView(FDMDataView telemetryView) {
		telemetryViews.add(telemetryView);
	}

	@Override
	public void detachTelemetryView(FDMDataView telemetryView) {
		telemetryViews.remove(telemetryView);
	}
}
