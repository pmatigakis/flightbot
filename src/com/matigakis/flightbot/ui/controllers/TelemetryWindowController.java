package com.matigakis.flightbot.ui.controllers;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.text.View;

import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.Instrumentation;
import com.matigakis.flightbot.aircraft.Orientation;
import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.aircraft.controllers.JythonAutopilot;
import com.matigakis.flightbot.aircraft.controllers.loaders.AutopilotLoader;
import com.matigakis.flightbot.aircraft.controllers.loaders.JythonAutopilotLoader;
import com.matigakis.flightbot.aircraft.sensors.Accelerometer;
import com.matigakis.flightbot.aircraft.sensors.GPS;
import com.matigakis.flightbot.aircraft.sensors.Gyroscope;
import com.matigakis.flightbot.aircraft.sensors.Magnetometer;
import com.matigakis.flightbot.aircraft.sensors.PitotTube;
import com.matigakis.flightbot.aircraft.sensors.StaticPressureSensor;
import com.matigakis.flightbot.aircraft.sensors.TemperatureSensor;
import com.matigakis.fgcontrol.controls.Controls;
import com.matigakis.fgcontrol.sensors.SensorData;
import com.matigakis.fgcontrol.SensorDataListener;
import com.matigakis.flightbot.ui.views.TelemetryView;
import com.matigakis.flightbot.ui.views.TelemetryWindow;

/**
 * The TelemetryViewController is responsible for the rendering of sensor data
 * to the telemetry view.
 */
public class TelemetryWindowController implements TelemetryViewController{
	private static final Logger logger = LoggerFactory.getLogger(TelemetryWindowController.class);
	private final TelemetryWindow telemetryView;
	//private final Aircraft aircraft;
	private List<MapMarker> markers;
	private Autopilot autopilot;
	
	private boolean autopilotRunning;
	
	public TelemetryWindowController(TelemetryWindow telemetryView){	
		this.telemetryView = telemetryView;
		//aircraft = new Aircraft();
		markers = new LinkedList<MapMarker>();
		
		autopilotRunning = false;
	}

	/**
	 * Close the view
	 */
	@Override
	public void close() {
		//telemetryView.dispatchEvent(new WindowEvent(telemetryView, WindowEvent.WINDOW_CLOSING));
		telemetryView.close();
	}

	/**
	 * Get the state of the autopilot
	 * 
	 * @return The state of the autopilot
	 */
	public boolean getAutopilotState() {
		//return aircraft.isAutopilotActive();
		return autopilotRunning;
	}

	@Override
	public void updateView(Aircraft aircraft) {
		telemetryView.updateView(aircraft);
	}

	@Override
	public void addMarkers() {
		JFileChooser fileChooser = new JFileChooser();
		
		int selection = fileChooser.showOpenDialog(telemetryView);
		
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
							telemetryView.addMarker(marker);
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
			telemetryView.removeMarker(mapMarker);
		}
		
		markers.clear();
	}

	@Override
	public void setAutopilotState(boolean state) {
		//aircraft.setAutopilotState(state);
		autopilotRunning = state;
	}

	@Override
	public void loadAutopilot() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int result = fileChooser.showOpenDialog(telemetryView);
		
		if(result == JFileChooser.APPROVE_OPTION){
			//aircraft.setAutopilotState(false);
			autopilotRunning = false;
			telemetryView.deactivateAutopilotControls(); //This is necessary because the autopilot loader can crush
			
			File f = fileChooser.getSelectedFile();
			
			JythonAutopilotLoader autopilotLoader = new JythonAutopilotLoader(f.getAbsolutePath());
			
			JythonAutopilot jythonAutopilot = (JythonAutopilot) autopilotLoader.getAutopilot();
			
			jythonAutopilot.setOutputStream(telemetryView.getDebugConsoleStream());
			
			autopilot = jythonAutopilot;
			
			telemetryView.activateAutopilotControls();
		}
	}

	@Override
	public Autopilot getAutopilot() {
		return autopilot;
	}
}
