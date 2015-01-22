package com.matigakis.flightbot.ui.views;

import java.util.List;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import com.matigakis.flightbot.aircraft.Aircraft;

public interface MapView {
	public void updateMap(Aircraft aircraft);
	public void addMapMarkers(List<MapMarker> mapMarkers);
	public void removeAllMapMarkers();
}