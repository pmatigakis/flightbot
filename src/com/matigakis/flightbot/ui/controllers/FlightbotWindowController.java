package com.matigakis.flightbot.ui.controllers;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.configuration.Configuration;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.fgcontrol.FGControl;
import com.matigakis.fgcontrol.fdm.Controls;
import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.aircraft.controllers.JythonAutopilot;
import com.matigakis.flightbot.ui.views.FlightbotView;

public class FlightbotWindowController implements FlightbotViewController{
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightbotWindowController.class);
	
	private FlightbotView flightbotView;
	private FGControl fgControl;
	private Aircraft aircraft;
	private Autopilot autopilot;
	
	private Configuration configuration;
	
	private FlightbotWindowController(){
	}
	
	public FlightbotWindowController(Configuration configuration, FGControl fgControl, Aircraft aircraft){
		this.fgControl = fgControl;
		this.aircraft = aircraft;
		this.configuration = configuration;
	}
	
	public void attachView(FlightbotView flightbotView){
		this.flightbotView = flightbotView;
	}
	
	@Override
	public void loadMapMarkersFromFile() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
		fileChooser.setFileFilter(filter);
		
		int retval = fileChooser.showOpenDialog(null);
		
		if (retval != JFileChooser.APPROVE_OPTION){
			return;
		}
		
		String filename = fileChooser.getSelectedFile().getPath();
		final List<MapMarker> mapMarkers = new LinkedList<MapMarker>();
		
		try {
			FileReader fr = new FileReader(filename);
			
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
						mapMarkers.add(marker);
					}else{
						LOGGER.error("Invalid marker data");
					}
				}else{
					break;
				}
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("File " + filename + "does not exist", e);
		} catch (IOException e) {
			LOGGER.error("Failed to load map markers", e);
		}
	
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				flightbotView.addMapMarkers(mapMarkers);	
			}
		});
	}

	@Override
	public void removeAllMapMarkers() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				flightbotView.removeAllMapMarkers();
			}
		});
	}

	@Override
	public void exit() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				flightbotView.close();
			}
		});
		
		System.exit(0);
	}

	@Override
	public void pauseFlightgear() {
		fgControl.pause();
	}

	@Override
	public void resetFlightgear() {
		fgControl.reset();
	}

	@Override
	public void updateFDMData(FDMData fdmData) {
		flightbotView.updateFDMData(fdmData);
	}

	@Override
	public void updateControls(Controls controls) {
		flightbotView.updateControls(controls);
	}

	@Override
	public boolean isAutopilotActive() {
		return aircraft.isAutopilotActive();
	}

	@Override
	public void updateAircraftState(FDMData fdmData) {
		aircraft.updateFromFDMData(fdmData);
	}

	@Override
	public Controls runAutopilot() {
		autopilot.updateControls(aircraft);
		
		return aircraft.getControls();
	}

	@Override
	public void setAutopilotEnabled(boolean enabled) {
		if(autopilot != null){
			aircraft.setAutopilotState(enabled);
			flightbotView.updateAircraftState(aircraft);
		}else{
			JOptionPane.showMessageDialog(null, "No autopilot is loaded");
		}
	}

	@Override
	public void resetAutopilot() {
		if(autopilot != null){
			autopilot.reset();
		}else{
			JOptionPane.showMessageDialog(null, "No autopilot is loaded");
		}
	}

	@Override
	public void loadJythonAutopilot() {
		aircraft.setAutopilotState(false);
		flightbotView.updateAircraftState(aircraft);
		
		try{
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("python module", "py");
			fileChooser.setFileFilter(filter);
			
			
			int retval = fileChooser.showOpenDialog(null);
			if (retval == JFileChooser.APPROVE_OPTION){
				File file = fileChooser.getSelectedFile();
				autopilot = new JythonAutopilot(file, configuration);
				JythonAutopilot jythonAutopilot = (JythonAutopilot) autopilot;
				jythonAutopilot.setOutputStream(flightbotView.getConsoleStream());
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Failed to load the new autopilot");
			LOGGER.error("Failed to load the new autopilot", e);
			autopilot = null;
		}
	}
}
