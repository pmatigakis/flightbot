package com.matigakis.flightbot.services;

import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.fgcontrol.fdm.RemoteFDM;
import com.matigakis.flightbot.ui.controllers.FDMDataViewController;

public class TelemetryViewUpdater implements Runnable{
	private FDMDataViewController telemetryViewController;
	private RemoteFDM fdm;
	
	public TelemetryViewUpdater(RemoteFDM fdm, FDMDataViewController telemetryViewController) {
		this.fdm = fdm;
		this.telemetryViewController = telemetryViewController;
	}
	
	@Override
	public void run() {
		FDMData fdmData = fdm.getFDMData();
		
		telemetryViewController.updateView(fdmData);
	}
}
