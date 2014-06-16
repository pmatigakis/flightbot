package com.matigakis.flightbot.ui.controllers;

public interface TelemetryViewListener {
	public void setAutopilotState(boolean autopilotState); 
	public void setMapMarkers();
	public void clearMapMerkers();
	public void close();
}
