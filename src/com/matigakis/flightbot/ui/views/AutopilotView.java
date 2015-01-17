package com.matigakis.flightbot.ui.views;

import java.io.OutputStream;

import com.matigakis.fgcontrol.fdm.Controls;

public interface AutopilotView {
	public void updateAutopilotState(boolean autopilotState);
	public void updateControls(Controls controls);
	public OutputStream getAutopilotConsoleStream();
}
