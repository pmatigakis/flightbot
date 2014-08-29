package com.matigakis.flightbot.ui.views;

import com.matigakis.flightbot.aircraft.Aircraft;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

public interface TelemetryRenderer {
	void updateView(Aircraft aircraft);
	void addMarker(MapMarker mapMarker);
	void removeMarker(MapMarker mapMarker);
}
