package com.matigakis.flightbot.ui.controllers;

import com.matigakis.fgcontrol.fdm.FDMData;

public interface FlightbotViewController {
	void updateAircraftState(FDMData fdmData);
	void exit();
}
