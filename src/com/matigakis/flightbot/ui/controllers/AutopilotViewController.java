package com.matigakis.flightbot.ui.controllers;

import java.io.OutputStream;

import com.matigakis.fgcontrol.fdm.Controls;
import com.matigakis.flightbot.ui.views.AutopilotView;

public interface AutopilotViewController {
	public void updateControls();
	public boolean isAutopilotActive();
	public void setAutopilotEnabled(boolean enabled);
	public void resetAutopilot();
	public void loadJythonAutopilot();
	public Controls runAutopilot();
	public void setAutopilotOutputStream(OutputStream outputStream);
	public void attachAutopilotView(AutopilotView autopilotView);
	public void detachAutopilotView(AutopilotView autopilotView);
}
