package com.matigakis.flightbot.ui.views;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.OsmTileLoader;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;

import com.matigakis.flightbot.sensors.GPS;

public class MapPanel extends JPanel implements JMapViewerEventListener, SensorRenderer<GPS>{
	private static final long serialVersionUID = 1L;
	
	private final JMapViewer map;
	
	private final MapMarkerDot airplaneMarker;
	private final LinkedList<MapMarkerDot> markers;
	
	public MapPanel(){
		map = new JMapViewer();
		map.addJMVListener(this);
		OsmTileLoader tileLoader = new OsmTileLoader(map); 
		map.setTileLoader(tileLoader);
		
		add(map);
		
		airplaneMarker = new MapMarkerDot(0.0, 0.0);
		map.addMapMarker(airplaneMarker);
		map.setZoom(15);
		airplaneMarker.setVisible(true);
		airplaneMarker.setColor(Color.red);
		airplaneMarker.setBackColor(Color.red);
		
		markers = new LinkedList<MapMarkerDot>();
	}
	
	@Override
	public void updateSensorView(GPS sensor) {
		double longitude = sensor.getLongitude();
		double latitude = sensor.getLatitude();
		
		int zoom = map.getZoom();
		//map.setDisplayPositionByLatLon(latitude, longitude, 15);
		map.setDisplayPositionByLatLon(latitude, longitude, zoom);
		
		airplaneMarker.setLat(latitude);
		airplaneMarker.setLon(longitude);
	}

	@Override
	public void processCommand(JMVCommandEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addMarkers(List<MapMarkerDot> markers){
		for(MapMarkerDot marker: markers){
			this.markers.add(marker);
			map.addMapMarker(marker);
		}
	}
	
	public void clearAllMarkers(){
		for(MapMarkerDot marker: markers){
			map.removeMapMarker(marker);
		}
		
		this.markers.clear();
	}
}
