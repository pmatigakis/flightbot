package com.matigakis.flightbot.services;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.fdm.FDM;
import com.matigakis.flightbot.ui.controllers.AutopilotViewController;

public class AutopilotUpdater implements Runnable{
	private Aircraft aircraft;
	private FDM fdm;
	private AutopilotViewController autopilotViewController;
	
	public AutopilotUpdater(FDM fdm, AutopilotViewController autopilotViewController) {
		aircraft = new Aircraft();
		
		this.fdm = fdm;
		this.autopilotViewController = autopilotViewController;
	}
	
	@Override
	public void run() {
		if (autopilotViewController.isAutopilotActivated()){
			fdm.updateAircraftState(aircraft);
			
			Autopilot autopilot = autopilotViewController.getAutopilot();
			
			autopilot.updateControls(aircraft);
			
			fdm.runSimulation(aircraft);
		}
	}
}
