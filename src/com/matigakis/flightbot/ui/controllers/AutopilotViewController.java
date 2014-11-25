package com.matigakis.flightbot.ui.controllers;

import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.ui.views.AutopilotView;

/**
 * Interface for the AutopilotView controller
 */
public interface AutopilotViewController {
	void activateAutopilot();
	void deactivateAutopilot();
	void attachAutopilotView(AutopilotView autopilotView);
	void detachAutopilotView(AutopilotView autopilotView);
	boolean isAutopilotActivated();
	Autopilot getAutopilot();
	Aircraft getAircraft();
	void run(FDMData fdmData);
}
