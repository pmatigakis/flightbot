package com.matigakis.flightbot.ui.controllers;

import com.matigakis.fgcontrol.sensors.SensorData;
import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.controllers.Autopilot;

public interface TelemetryViewController {
	void updateView(Aircraft aircraft);
	void addMarkers();
	void clearmarkers();
	void close();
	void setAutopilotState(boolean state);
	boolean getAutopilotState();
	void loadAutopilot();
	Autopilot getAutopilot();
}
