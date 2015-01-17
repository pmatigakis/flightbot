package com.matigakis.flightbot.ui.views;

import java.util.List;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

public interface MapView {

	public abstract void addMapMarkers(List<MapMarker> mapMarkers);

	public abstract void removeAllMapMarkers();

}