package com.matigakis.flightbot.ui.controllers;

import java.io.IOException;

import com.matigakis.flightbot.ui.views.MapView;

public interface MapViewController {
	public void loadMapMarkersFromFile();
	public void removeAllMapMarkers();
	public void attachMapView(MapView mapView);
	public void detachMapView(MapView mapView);
}