package com.matigakis.flightbot.services;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.fdm.FDM;
import com.matigakis.flightbot.ui.controllers.TelemetryViewController;

public class TelemetryViewUpdater implements Runnable{
	private TelemetryViewController telemetryViewController;
	private Aircraft aircraft;
	private FDM fdm;
	
	public TelemetryViewUpdater(FDM fdm, TelemetryViewController telemetryViewController) {
		this.aircraft = new Aircraft();
		this.fdm = fdm;
		this.telemetryViewController = telemetryViewController;
	}
	
	@Override
	public void run() {
		fdm.updateAircraftState(aircraft);
		
		telemetryViewController.updateView(aircraft);
	}
}
