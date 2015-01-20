package com.matigakis.flightbot.ui.controllers;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import com.matigakis.flightbot.maps.XMLMarkerLoader;
import com.matigakis.flightbot.maps.MarkerLoader;
import com.matigakis.flightbot.maps.MarkerLoadException;
import com.matigakis.flightbot.ui.views.MapView;

public class MapWindowController implements MapViewController{
	private static final Logger LOGGER = LoggerFactory.getLogger(MapWindowController.class);
	
	List<MapView> mapViews;
	
	public MapWindowController(){
		mapViews = new LinkedList<MapView>();
	}
	
	@Override
	public void loadMapMarkersFromFile(){
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML files", "xml");
		fileChooser.setFileFilter(filter);
		
		int retval = fileChooser.showOpenDialog(null);
		
		if (retval != JFileChooser.APPROVE_OPTION){
			return;
		}
		
		String filename = fileChooser.getSelectedFile().getPath();
	
		MarkerLoader markerLoader = new XMLMarkerLoader();
		
		try {
			markerLoader.loadMarkers(filename);
		} catch (IOException | MarkerLoadException e) {
			LOGGER.error("Failed to load map markers", e);
			
			JOptionPane.showMessageDialog(null, "Failed to load map markers");
			
			return;
		} 
	
		final List<MapMarker> mapMarkers = markerLoader.getMarkers();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				for(MapView mapView: mapViews){
					mapView.addMapMarkers(mapMarkers);
				}
			}
		});
	}

	@Override
	public void removeAllMapMarkers() {
		for(MapView mapView: mapViews){
			mapView.removeAllMapMarkers();
		}
	}

	@Override
	public void attachMapView(MapView mapView) {
		mapViews.add(mapView);
	}

	@Override
	public void detachMapView(MapView mapView) {
		mapViews.remove(mapView);
	}
}
