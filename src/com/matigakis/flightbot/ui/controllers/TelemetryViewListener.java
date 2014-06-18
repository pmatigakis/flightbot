package com.matigakis.flightbot.ui.controllers;

public interface TelemetryViewListener {
	void setAutopilotState(boolean autopilotState); 
	void setMapMarkers();
	void clearMapMerkers();
	void close();
}
