package com.matigakis.flightbot.ui.views;

import javax.swing.JFrame;

import java.util.List;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.ui.controllers.TelemetryViewListener;

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

/**
 * The TelemetryView is a simple window that displays the data that where received
 * from Flightgear
 */
public class TelemetryView extends JFrame implements AircraftDataRenderer{
	private static final long serialVersionUID = 1L;
	
	private final SensorsPanel sensorsPanel;
	private final AircraftPanel aircraftPanel; 
	private final MapPanel mapPanel;
	
	//private final OperationPanel operationPanel;
	
	private final JMenuItem exitMenuItem;
	private final JMenuItem startMenuItem; 
	private final JMenuItem stopMenuItem; 
	
	private final JMenuItem addMarkersMenuItem;
	private final JMenuItem clearMarkersMenuItem;
	
	public TelemetryView(){
		super();
		
		setTitle("Telemetry viewer");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		GridBagLayout layout = new GridBagLayout();    
		setLayout(layout);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		
		JMenu mapMenu = new JMenu("Map");
		addMarkersMenuItem = new JMenuItem("Add markers");
		clearMarkersMenuItem = new JMenuItem("Clear markers");
		mapMenu.add(addMarkersMenuItem);
		mapMenu.add(clearMarkersMenuItem);
		
		JMenu autopilotMenu = new JMenu("Autopilot");
		startMenuItem = new JMenuItem("Start");
		stopMenuItem = new JMenuItem("Stop");
		stopMenuItem.setEnabled(false);
		JMenuItem resetMenuItem = new JMenuItem("Reset");
		autopilotMenu.add(startMenuItem);
		autopilotMenu.add(stopMenuItem);
		autopilotMenu.add(resetMenuItem);
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutMenuItem = new JMenuItem("About");
		helpMenu.add(aboutMenuItem);
		
		menuBar.add(fileMenu);
		menuBar.add(mapMenu);
		menuBar.add(autopilotMenu);
		menuBar.add(helpMenu);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets(10, 10, 10, 10);
		
		sensorsPanel = new SensorsPanel();
		c.gridx = 0;
		c.gridy = 0;
		add(sensorsPanel, c);
		
		mapPanel = new MapPanel();
		c.gridx = 1;
		c.gridy = 0;
		add(mapPanel, c);
				
		aircraftPanel = new AircraftPanel();
		c.gridx = 2;
		c.gridy = 0;
		add(aircraftPanel, c);
		
		setJMenuBar(menuBar);
	
		pack();
		setResizable(false);
		
		setVisible(true);
		
		ExitAdapter exitAdapter = new ExitAdapter();
		addWindowListener(exitAdapter);
	}
	
	@Override
	public void updateView(Aircraft aircraft) {
		sensorsPanel.updateFromAircraftData(aircraft);
		aircraftPanel.updateFromAircraftData(aircraft);
		mapPanel.updateSensorView(aircraft.getGPS());
	}
	
	/**
	 * Add map markers.
	 * 
	 * @param markers The map markers to add
	 */
	public void addMapMarkers(List<MapMarker> markers){
		mapPanel.addMarkers(markers);
	}
	
	/**
	 * Remove all the map markers. 
	 */
	public void clearMarkers(){
		mapPanel.clearAllMarkers();
	}
	
	/**
	 * Attach a controller to the view
	 * 
	 * @param telemetryViewListener The controller the attach to
	 */
	public void addTelemetryViewListener(final TelemetryViewListener telemetryViewListener){
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				telemetryViewListener.close();
			}
		});
		
		addMarkersMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				telemetryViewListener.setMapMarkers();
			}
		});
		
		clearMarkersMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				telemetryViewListener.clearMapMerkers();
			}
		});
		
		startMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startMenuItem.setEnabled(false);
				stopMenuItem.setEnabled(true);
				telemetryViewListener.setAutopilotState(true);
			}
		});
		
		stopMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startMenuItem.setEnabled(true);
				stopMenuItem.setEnabled(false);
				telemetryViewListener.setAutopilotState(false);
			}
		});
	}
}
