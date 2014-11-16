package com.matigakis.flightbot.fdm;

import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.fgcontrol.fdm.NetworkFDM;
import com.matigakis.fgcontrol.fdm.RemoteFDM;
import com.matigakis.fgcontrol.fdm.RemoteFDMStateListener;
import com.matigakis.flightbot.aircraft.Aircraft;

public class FlightGearFDM extends NetworkFDM implements FDM, RemoteFDMStateListener{
	private FDMData fdmData;
	
	public FlightGearFDM(String host, int fdmPort, int controlsPort) {
		super(host, fdmPort, controlsPort);
		
		fdmData = new FDMData();
		
		addRemoteFDMStateListener(this);
	}

	@Override
	public void updateAircraftState(Aircraft aircraft) {
		aircraft.updateFromFDMData(fdmData);
	}

	@Override
	public void runSimulation(Aircraft aircraft) {
		transmitControls(aircraft.getControls());
		
		//TODO: the current state of the aircraft controls will be overwritten
		//with the last state that was received. This could result in problems 
		aircraft.updateFromFDMData(fdmData);
	}
	
	@Override
	public void fdmUpdated(RemoteFDM remoteFdm, FDMData fdmData) {
		this.fdmData = fdmData;
	}
}
