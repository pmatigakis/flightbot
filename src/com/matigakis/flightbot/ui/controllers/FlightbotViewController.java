package com.matigakis.flightbot.ui.controllers;

import com.matigakis.fgcontrol.fdm.Controls;
import com.matigakis.fgcontrol.fdm.FDMData;

public interface FlightbotViewController {
	void pauseFlightgear();
	void resetFlightgear();
	void loadMapMarkersFromFile();
	void removeAllMapMarkers();
	void updateFDMData(FDMData fdmData);
	void updateControls(Controls controls);
	boolean isAutopilotActive();
	void setAutopilotEnabled(boolean enabled);
	void updateAircraftState(FDMData fdmData);
	void resetAutopilot();
	Controls runAutopilot();
	void loadJythonAutopilot();
	void exit();
}
