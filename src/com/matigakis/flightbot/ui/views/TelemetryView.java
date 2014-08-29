package com.matigakis.flightbot.ui.views;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.ui.controllers.TelemetryViewController;

public interface TelemetryView {
	void updateView(Aircraft aircraft);
	void addMarker(MapMarker marker);
	void removeMarker(MapMarker marker);
	void attachController(TelemetryViewController controller);
	void close();
}
