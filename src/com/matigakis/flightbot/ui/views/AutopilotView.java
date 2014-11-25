package com.matigakis.flightbot.ui.views;

import com.matigakis.fgcontrol.fdm.Controls;

/**
 * Interface for the any object that is responsible of rendering
 * data for an autopilot
 */
public interface AutopilotView{
	void updateAutopilotState(boolean autopilotEnabled);
	void updateAutopilotControls(Controls controls);
}
