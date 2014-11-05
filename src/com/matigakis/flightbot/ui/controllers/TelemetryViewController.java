package com.matigakis.flightbot.ui.controllers;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.ui.views.TelemetryView;

/**
 * The interface for the TelemetryView controller
 */
public interface TelemetryViewController {
	void updateView(Aircraft aircraft);
	void addMarkers();
	void clearmarkers();
	void close();
	void attachTelemetryView(TelemetryView telemetryView);
	void detachTelemetryView(TelemetryView telemetryView);
}
