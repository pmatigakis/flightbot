package com.matigakis.flightbot.services;

import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.fgcontrol.fdm.RemoteFDM;
import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.ui.controllers.AutopilotViewController;

public class AutopilotUpdater implements Runnable{
	private RemoteFDM fdm;
	private AutopilotViewController autopilotViewController;
	
	public AutopilotUpdater(RemoteFDM fdm, AutopilotViewController autopilotViewController) {
		this.fdm = fdm;
		this.autopilotViewController = autopilotViewController;
	}
	
	@Override
	public void run() {
		if (autopilotViewController.isAutopilotActivated()){
			FDMData fdmData = fdm.getFDMData();
			
			autopilotViewController.run(fdmData);
			
			Aircraft aircraft = autopilotViewController.getAircraft();
			
			fdm.transmitControls(aircraft.getControls());
		}
	}
}
