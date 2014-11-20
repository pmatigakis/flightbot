package com.matigakis.flightbot.ui.views;

import com.matigakis.fgcontrol.fdm.Controls;

/**
 * Interface for the any object that is responsible of rendering
 * data for an autopilot
 */
public interface AutopilotView {
	void setAutopilotControlsState(boolean autopilotControlsState);
	void updateAutopilotState(boolean autopilotState);
	void updateAutopilotControls(Controls controls);
}
