package com.matigakis.flightbot.fdm;

import com.matigakis.fgcontrol.controls.Controls;

public interface NetworkFDMold extends FDM{
	void connect() throws InterruptedException;
	void disconnect();
	void transmitControls(Controls controls);
}