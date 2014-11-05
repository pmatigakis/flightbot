package com.matigakis.flightbot.ui.views;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import com.matigakis.flightbot.aircraft.Aircraft;

/**
 * Interface for every object that is responsible of rendering
 * telemetry data
 */
public interface TelemetryView {
	void updateTelemetry(Aircraft aircraft);
	void addMarker(MapMarker marker);
	void removeMarker(MapMarker marker);
	void close();
}
