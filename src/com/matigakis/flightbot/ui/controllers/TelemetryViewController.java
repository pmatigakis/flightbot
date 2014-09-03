package com.matigakis.flightbot.ui.controllers;

import com.matigakis.fgcontrol.sensors.SensorData;
import com.matigakis.flightbot.aircraft.Aircraft;

public interface TelemetryViewController {
	void updateAircraft(SensorData sensorData);
	void updateAircraft(Aircraft aircraft);
	void updateView();
	void addMarkers();
	void clearmarkers();
	void close();
	void setAutopilotState(boolean state);
	boolean getAutopilotState();
	void loadAutopilot();
	void updateAircraftControls();
	Aircraft getAircraft();
}
