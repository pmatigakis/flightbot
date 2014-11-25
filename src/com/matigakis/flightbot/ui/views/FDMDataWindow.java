package com.matigakis.flightbot.ui.views;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.OsmTileLoader;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.ui.controllers.FDMDataViewController;

/**
 * This windows is used to display the fdm data.
 */
public class FDMDataWindow extends JFrame implements FDMDataView, JMapViewerEventListener{
	private static final long serialVersionUID = 1L;
	
	private final FDMDataPanel fdmDataPanel;
	private final JMapViewer mapViewer;
	
	//private final JMenuItem loadAutopilotMenuItem;
	private final JMenuItem exitMenuItem;
	
	private final JMenuItem addMarkersMenuItem;
	private final JMenuItem clearMarkersMenuItem;
	
	private final MapMarkerDot airplaneMarker;

	private FDMDataViewController telemetryViewController;
	
	public FDMDataWindow(FDMDataViewController telemetryViewController){
		super();
		
		this.telemetryViewController = telemetryViewController;
		
		setTitle("Telemetry viewer");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		GridBagLayout layout = new GridBagLayout();    
		setLayout(layout);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		/*
		loadAutopilotMenuItem = new JMenuItem("Load Jython autopilot");
		loadAutopilotMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadJythonAutopilot();
			}
		});
		*/
		
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		
		fileMenu.add(exitMenuItem);
		
		menuBar.add(fileMenu);
		
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
		fdmDataPanel = new FDMDataPanel();
		add(fdmDataPanel, c);
		
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
	public void updateFDMData(FDMData fdmData) {
		fdmDataPanel.updateFromFDMData(fdmData);
		
		double longitude = fdmData.getPosition().getLongitude();
		double latitude = fdmData.getPosition().getLatitude();
		
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
	
	private void close() {
		//controller.close();
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	@Override
	public void processCommand(JMVCommandEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void addMarkers(){
		telemetryViewController.addMarkers();
	}
	
	private void clearMarkers(){
		telemetryViewController.clearmarkers();
	}
}
