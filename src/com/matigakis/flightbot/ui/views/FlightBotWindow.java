package com.matigakis.flightbot.ui.views;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.OsmTileLoader;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.controllers.JythonAutopilot;
import com.matigakis.flightbot.ui.controllers.AutopilotViewController;
import com.matigakis.flightbot.ui.controllers.TelemetryViewController;
import com.matigakis.flightbot.ui.views.information.AircraftPanel;

/*
class ExitAdapter extends WindowAdapter{
	public volatile boolean running;
	
	public ExitAdapter(){
		super();
		
		running = true;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		super.windowClosing(e);
		running = false;
	}
}
*/

/**
 * The main application window. Everything is controlled and displayed here.
 */
public class FlightBotWindow extends JFrame implements TelemetryView, AutopilotView, JMapViewerEventListener{
	private static final long serialVersionUID = 1L;
	
	private final SensorsPanel sensorsPanel;
	private final AircraftPanel aircraftPanel; 
	//private final MapPanel mapPanel;
	private final JMapViewer mapViewer;
	//private final OperationPanel operationPanel;
	
	private final JMenuItem loadAutopilotMenuItem;
	private final JMenuItem exitMenuItem;
	
	private final JMenuItem addMarkersMenuItem;
	private final JMenuItem clearMarkersMenuItem;

	private JMenuItem startAutopilotMenuItem;
	private JMenuItem stopAutopilotMenuItem;
	
	private final JTextArea debugText;
	private OutputStream consoleStream;
	
	private final MapMarkerDot airplaneMarker;

	private AutopilotViewController autopilotViewController;
	private TelemetryViewController telemetryViewController;
	
	public FlightBotWindow(TelemetryViewController telemetryViewController, AutopilotViewController autopilotViewController){
		super();
		
		this.autopilotViewController = autopilotViewController;
		this.telemetryViewController = telemetryViewController;
		
		setTitle("Telemetry viewer");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		GridBagLayout layout = new GridBagLayout();    
		setLayout(layout);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		loadAutopilotMenuItem = new JMenuItem("Load autopilot");
		loadAutopilotMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadNewAutopilot();
			}
		});
		
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});
		
		fileMenu.add(loadAutopilotMenuItem);
		fileMenu.add(exitMenuItem);
		
		menuBar.add(fileMenu);
		
		JMenu autopilotMenu = new JMenu("autopilot");
	
		startAutopilotMenuItem = new JMenuItem("Start");
		startAutopilotMenuItem.setEnabled(false);
		startAutopilotMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setAutopilotState(true);
			}
		});
		autopilotMenu.add(startAutopilotMenuItem);
		
		stopAutopilotMenuItem = new JMenuItem("Stop");
		stopAutopilotMenuItem.setEnabled(false);
		stopAutopilotMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setAutopilotState(false);
			}
		});
		autopilotMenu.add(stopAutopilotMenuItem);
		
		menuBar.add(autopilotMenu);
		
		JMenu mapMenu = new JMenu("Map");
		
		addMarkersMenuItem = new JMenuItem("Add markers");
		addMarkersMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addMarkers();
			}
		});
		
		clearMarkersMenuItem = new JMenuItem("Clear markers");
		clearMarkersMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearMarkers();
			}
		});
		
		mapMenu.add(addMarkersMenuItem);
		mapMenu.add(clearMarkersMenuItem);
		
		menuBar.add(mapMenu);
		
		setJMenuBar(menuBar);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill =  GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		sensorsPanel = new SensorsPanel();
		add(sensorsPanel, c);
		
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill =  GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		//mapPanel = new MapPanel();
		//add(mapPanel, c);
		mapViewer = new JMapViewer();
		mapViewer.addJMVListener(this);
		OsmTileLoader tileLoader = new OsmTileLoader(mapViewer); 
		mapViewer.setTileLoader(tileLoader);
		add(mapViewer, c);
		
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill =  GridBagConstraints.NONE;
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		aircraftPanel = new AircraftPanel();
		add(aircraftPanel, c);
		
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill =  GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.gridwidth = 3;
		c.gridheight = 1;
		debugText = new JTextArea();
		debugText.setRows(10);
		debugText.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(debugText);
		add(scrollPane, c);
		
		pack();
		setResizable(false);
		
		setVisible(true);
		
		airplaneMarker = new MapMarkerDot(0.0, 0.0);
		mapViewer.addMapMarker(airplaneMarker);
		mapViewer.setZoom(15);
		airplaneMarker.setVisible(true);
		airplaneMarker.setColor(Color.red);
		airplaneMarker.setBackColor(Color.red);
		
		consoleStream = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				debugText.append(String.valueOf((char)b));
				debugText.setCaretPosition(debugText.getDocument().getLength());
			}
		};
	}
	
	@Override
	public void updateTelemetry(Aircraft aircraft) {
		sensorsPanel.updateFromAircraftData(aircraft);
		aircraftPanel.updateFromAircraftData(aircraft);
		
		double longitude = aircraft.getGPS().getLongitude();
		double latitude = aircraft.getGPS().getLatitude();
		
		//mapPanel.updateSensorView(aircraft.getGPS());
		
		int zoom = mapViewer.getZoom();
		
		mapViewer.setDisplayPositionByLatLon(latitude, longitude, zoom);
		
		airplaneMarker.setLat(latitude);
		airplaneMarker.setLon(longitude);
	}
	
	@Override
	public void addMarker(MapMarker mapMarker) {
		mapViewer.addMapMarker(mapMarker);
		
	}

	@Override
	public void removeMarker(MapMarker mapMarker) {
		mapViewer.removeMapMarker(mapMarker);
	}
	
	@Override
	public void close() {
		//controller.close();
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	@Override
	public void processCommand(JMVCommandEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public OutputStream getDebugConsoleStream(){
		return consoleStream;
	}

	@Override
	public void setAutopilotControlsState(boolean autopilotControlsState) {
		if(autopilotControlsState){
			startAutopilotMenuItem.setEnabled(true);
			stopAutopilotMenuItem.setEnabled(false);
		}else{
			startAutopilotMenuItem.setEnabled(false);
			stopAutopilotMenuItem.setEnabled(false);
		}
	}
	
	@Override
	public void updateAutopilotState(boolean autopilotState) {
		if(autopilotState){
			startAutopilotMenuItem.setEnabled(false);
			stopAutopilotMenuItem.setEnabled(true);
		}else{
			startAutopilotMenuItem.setEnabled(true);
			stopAutopilotMenuItem.setEnabled(false);
		}	
	}
	
	private void loadNewAutopilot(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int result = fileChooser.showOpenDialog(this);
		
		if(result == JFileChooser.APPROVE_OPTION){
			setAutopilotControlsState(false); //This is necessary because the autopilot loader can crush
			
			File f = fileChooser.getSelectedFile();
			
			autopilotViewController.loadAutopilot(f.getAbsolutePath());
			
			//TODO: perhaps this should be moved to the controller
			JythonAutopilot autopilot = (JythonAutopilot) autopilotViewController.getAutopilot();
			autopilot.setOutputStream(getDebugConsoleStream());
			autopilot.reset();
			
			setAutopilotControlsState(true);
		}
	}
	
	private void setAutopilotState(boolean autopilotState){
		if(autopilotState){
			autopilotViewController.activateAutopilot();
		}else{
			autopilotViewController.deactivateAutopilot();
		}
	}
	
	private void closeWindow(){
		telemetryViewController.close();
	}
	
	private void addMarkers(){
		telemetryViewController.addMarkers();
	}
	
	private void clearMarkers(){
		telemetryViewController.clearmarkers();
	}
}
