package com.matigakis.flightbot.ui.views;

import javax.swing.JFrame;

import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
import com.matigakis.flightbot.ui.controllers.TelemetryViewController;
import com.matigakis.flightbot.ui.controllers.TelemetryViewListener;

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
 * The TelemetryView is a simple window that displays the data that where received
 * from Flightgear
 */
public class TelemetryWindow extends JFrame implements TelemetryView, JMapViewerEventListener{
	private static final long serialVersionUID = 1L;
	
	private TelemetryViewController viewController;
	
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

	private ActionListener loadAutopilotActionListener;
	private ActionListener exitActionListener;
	private ActionListener addMarkerActionListener;
	private ActionListener clearMarkerActionListener;
	private ActionListener startAutopilotActionListener;
	private ActionListener stopAutopilotActionListener;
	
	private final MapMarkerDot airplaneMarker;

	public TelemetryWindow(){
		super();
		
		setTitle("Telemetry viewer");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		GridBagLayout layout = new GridBagLayout();    
		setLayout(layout);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		loadAutopilotMenuItem = new JMenuItem("Load autopilot");
		exitMenuItem = new JMenuItem("Exit");
		
		fileMenu.add(loadAutopilotMenuItem);
		fileMenu.add(exitMenuItem);
		
		menuBar.add(fileMenu);
		
		JMenu autopilotMenu = new JMenu("autopilot");
	
		startAutopilotMenuItem = new JMenuItem("Start");
		startAutopilotMenuItem.setEnabled(false);
		autopilotMenu.add(startAutopilotMenuItem);
		
		stopAutopilotMenuItem = new JMenuItem("Stop");
		stopAutopilotMenuItem.setEnabled(false);
		autopilotMenu.add(stopAutopilotMenuItem);
		
		menuBar.add(autopilotMenu);
		
		JMenu mapMenu = new JMenu("Map");
		addMarkersMenuItem = new JMenuItem("Add markers");
		clearMarkersMenuItem = new JMenuItem("Clear markers");
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
		JTextArea debugText = new JTextArea();
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
	}
	
	@Override
	public void updateView(Aircraft aircraft) {
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
	
	/**
	 * Attach a controller to the view
	 * 
	 * @param controller The controller the attach to
	 */
	@Override
	public void attachController(TelemetryViewController controller) {
		this.viewController = controller;
		
		exitMenuItem.removeActionListener(exitActionListener);
		exitActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewController.close();
			}
		};
		exitMenuItem.addActionListener(exitActionListener);
		
		startAutopilotMenuItem.removeActionListener(startAutopilotActionListener);
		startAutopilotActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startAutopilotMenuItem.setEnabled(false);
				stopAutopilotMenuItem.setEnabled(true);
				
				viewController.setAutopilotState(true);
			}
		};
		startAutopilotMenuItem.addActionListener(startAutopilotActionListener);
		
		stopAutopilotMenuItem.removeActionListener(stopAutopilotActionListener);
		stopAutopilotActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startAutopilotMenuItem.setEnabled(true);
				stopAutopilotMenuItem.setEnabled(false);

				viewController.setAutopilotState(false);
			}
		};
		stopAutopilotMenuItem.addActionListener(stopAutopilotActionListener);
		
		addMarkersMenuItem.removeActionListener(addMarkerActionListener);
		addMarkerActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewController.addMarkers();
			}
		};
		addMarkersMenuItem.addActionListener(addMarkerActionListener);
		
		clearMarkersMenuItem.removeActionListener(clearMarkerActionListener);
		clearMarkerActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewController.clearmarkers();
			}
		};
		clearMarkersMenuItem.addActionListener(clearMarkerActionListener);
		
		loadAutopilotMenuItem.removeActionListener(loadAutopilotActionListener);
		loadAutopilotActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewController.loadAutopilot();
				//startAutopilotMenuItem.setEnabled(true);
			}
		};
		loadAutopilotMenuItem.addActionListener(loadAutopilotActionListener);
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
	
	public void activateAutopilotControls(){
		startAutopilotMenuItem.setEnabled(true);
		stopAutopilotMenuItem.setEnabled(false);
	}
	
	public void deactivateAutopilotControls(){
		startAutopilotMenuItem.setEnabled(false);
		stopAutopilotMenuItem.setEnabled(false);
	}
}
