package com.matigakis.flightbot.aircraft.controllers;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.fdm.FDM;
import com.matigakis.flightbot.fdm.NetworkFDM;
import com.matigakis.flightbot.fdm.NetworkFDMEventListener;

/**
 * The AutopilotAdapter is a helper class that can be used to attach
 * and autopilot to an FDM and receive events raised by it.
 */
public class AutopilotAdapter implements NetworkFDMEventListener{
	private Autopilot autopilot;
	private Aircraft aircraft;
	
	public AutopilotAdapter(Autopilot autopilot) {
		this.autopilot = autopilot;
	}
	
	/**
	 * update the autopilot using data from the FDM
	 */
	@Override
	public void stateUpdated(NetworkFDM fdm) {
		fdm.updateAircraftState(aircraft);
		
		autopilot.updateControls(aircraft);
	}
}
