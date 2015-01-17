package com.matigakis.flightbot.ui.views;

import javax.swing.JFrame;

import com.matigakis.fgcontrol.fdm.Controls;
import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.ui.controllers.AutopilotViewController;
import com.matigakis.flightbot.ui.controllers.FlightbotViewController;
import com.matigakis.flightbot.ui.controllers.MapViewController;
import com.matigakis.flightbot.ui.controllers.SimulatorControlViewController;
import com.matigakis.flightbot.ui.views.information.ControlsPanel;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.OsmTileLoader;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class FlightBotWindow extends JFrame implements FlightbotView, MapView, FDMDataView, AutopilotView, JMapViewerEventListener{
	private static final long serialVersionUID = 1L;

	private final FlightbotViewController flightbotViewController;
	private final MapViewController mapViewController;
	private final SimulatorControlViewController simulatorControlViewController;
	private final AutopilotViewController autopilotViewController;
	
	private FDMDataTableModel fdmDataTableModel;
	private JMapViewer mapViewer;
	private MapMarkerDot aircraftMarker;
	
	private JMenuItem loadAutopilotMenuItem;
	private JMenuItem exitMenuItem;
	
	private JMenuItem loadMarkersMenuItem;
	private JMenuItem clearMarkersMenuItem;
	
	private JMenuItem pauseSimulatorMenuItem;
	private JMenuItem resetSimulatorMenuItem;
	
	private JMenuItem startAutopilotMenuItem;
	private JMenuItem stopAutopilotMenuItem;
	private JMenuItem resetAutopilotMenuItem;
	
	private JTextArea logTextArea;
	
	private OutputStream consoleStream;
	
	private ControlsPanel controlsPanel;
	
	public FlightBotWindow(FlightbotViewController flightbotViewController, MapViewController mapViewController, SimulatorControlViewController simulatorControlViewController, AutopilotViewController autopilotViewController){
		this.flightbotViewController = flightbotViewController;
		this.mapViewController = mapViewController;
		this.simulatorControlViewController = simulatorControlViewController;
		this.autopilotViewController = autopilotViewController;
		
		setTitle("FlightBot");
		
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		createMenuBar();
		createControls();
		
		addActionListeners();
		
		pack();
		setMinimumSize(getSize());
		
		aircraftMarker = new MapMarkerDot(0.0, 0.0);
		mapViewer.addMapMarker(aircraftMarker);
		mapViewer.setZoom(15);
		aircraftMarker.setVisible(true);
		aircraftMarker.setColor(Color.red);
		aircraftMarker.setBackColor(Color.red);
		
		setVisible(true);
	}

	private void createControls(){
		setLayout(new GridBagLayout());
		
		fdmDataTableModel = new FDMDataTableModel();
		FDMDataTable fdmDataTable = new FDMDataTable(fdmDataTableModel);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		JScrollPane p = new JScrollPane(fdmDataTable);
		add(p, c);
		
		mapViewer = new JMapViewer();
		mapViewer.addJMVListener(this);
		OsmTileLoader tileLoader = new OsmTileLoader(mapViewer); 
		mapViewer.setTileLoader(tileLoader);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(mapViewer, c);
		
		controlsPanel = new ControlsPanel();
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(controlsPanel, c);
		
		logTextArea = new JTextArea();
		logTextArea.setRows(10);
		logTextArea.setEditable(false);
		JScrollPane logTextScrollPane = new JScrollPane(logTextArea);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(logTextScrollPane, c);
		
		consoleStream = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				logTextArea.append(String.valueOf((char)b));
				logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
			}
		};
	}
	
	private void createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		loadAutopilotMenuItem = new JMenuItem("Load autopilot");
		fileMenu.add(loadAutopilotMenuItem);
		
		exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		
		JMenu flightgearMenu = new JMenu("Flightgear");
		menuBar.add(flightgearMenu);
		
		pauseSimulatorMenuItem = new JMenuItem("Pause/Upause");
		flightgearMenu.add(pauseSimulatorMenuItem);
		
		resetSimulatorMenuItem = new JMenuItem("Reset");
		flightgearMenu.add(resetSimulatorMenuItem);
		
		JMenu autopilotMenu = new JMenu("Autopilot");
		menuBar.add(autopilotMenu);
		
		startAutopilotMenuItem = new JMenuItem("Start");
		autopilotMenu.add(startAutopilotMenuItem);
		
		stopAutopilotMenuItem = new JMenuItem("Stop");
		stopAutopilotMenuItem.setEnabled(false);
		autopilotMenu.add(stopAutopilotMenuItem);
		
		resetAutopilotMenuItem = new JMenuItem("Reset");
		autopilotMenu.add(resetAutopilotMenuItem);
		
		JMenu mapMenu = new JMenu("Map");
		menuBar.add(mapMenu);
		
		loadMarkersMenuItem = new JMenuItem("Load markers");
		mapMenu.add(loadMarkersMenuItem);
		
		clearMarkersMenuItem = new JMenuItem("Clear markers");
		mapMenu.add(clearMarkersMenuItem);
	}
	
	private void addActionListeners(){
		loadAutopilotMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				autopilotViewController.loadJythonAutopilot();
			}
		});
		
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				flightbotViewController.exit();
			}
		});
		
		loadMarkersMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapViewController.loadMapMarkersFromFile();
			}
		});
		
		clearMarkersMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapViewController.removeAllMapMarkers();
			}
		});
		
		pauseSimulatorMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simulatorControlViewController.pause();
			}
		});
		
		resetSimulatorMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simulatorControlViewController.reset();
			}
		});
		
		startAutopilotMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				autopilotViewController.setAutopilotEnabled(true);
			}
		});
		
		stopAutopilotMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				autopilotViewController.setAutopilotEnabled(false);
			}
		});
		
		resetAutopilotMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				autopilotViewController.resetAutopilot();
			}
		});
	}
	
	@Override
	public void processCommand(JMVCommandEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateFDMData(FDMData fdmData) {
		fdmDataTableModel.updateFromFDMData(fdmData);
		
		final double longitude = fdmData.getPosition().getLongitude();
		final double latitude = fdmData.getPosition().getLatitude();
		
		final int zoom = mapViewer.getZoom();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mapViewer.setDisplayPositionByLatLon(latitude, longitude, zoom);
				
				aircraftMarker.setLat(latitude);
				aircraftMarker.setLon(longitude);		
			}
		});	
	}

	@Override
	public void close() {
		dispose();
	}

	@Override
	public void addMapMarkers(final List<MapMarker> mapMarkers) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				for(MapMarker mapMarker: mapMarkers){
					mapViewer.addMapMarker(mapMarker);
				}	
			}
		});
	}

	@Override
	public void removeAllMapMarkers() {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				mapViewer.removeAllMapMarkers();
			}
		});	
	}

	@Override
	public void updateControls(final Controls controls) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				controlsPanel.updateControls(controls);	
			}
		});
	}

	@Override
	public OutputStream getAutopilotConsoleStream() {
		return consoleStream;
	}

	@Override
	public void updateAutopilotState(boolean autopilotState) {
		if (autopilotState){
			startAutopilotMenuItem.setEnabled(false);
			stopAutopilotMenuItem.setEnabled(true);
		}else{
			startAutopilotMenuItem.setEnabled(true);
			stopAutopilotMenuItem.setEnabled(false);
		}
	}
}
