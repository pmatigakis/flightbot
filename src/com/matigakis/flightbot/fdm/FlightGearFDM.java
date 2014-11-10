package com.matigakis.flightbot.fdm;

import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.fgcontrol.fdm.NetworkFDM;
import com.matigakis.fgcontrol.fdm.NetworkFDMStateListener;
import com.matigakis.flightbot.aircraft.Aircraft;

public class FlightGearFDM implements RemoteFDM, NetworkFDMStateListener{
	private FDMData fdmData;
	private NetworkFDM fdm;
	
	public FlightGearFDM(String host, int fdmPort, int controlsPort) {
		fdmData = new FDMData();
		
		fdm = new NetworkFDM(host, fdmPort, controlsPort);
		
		fdm.addFDMStateListener(this);
	}

	@Override
	public void updateAircraftState(Aircraft aircraft) {
		aircraft.updateFromFDMData(fdmData);
	}

	@Override
	public void runSimulation(Aircraft aircraft) {
		fdm.transmitControls(aircraft.getControls());
		
		//TODO: the current state of the aircraft controls will be overwritten
		//with the last state that was received. This could result in problems 
		aircraft.updateFromFDMData(fdmData);
	}

	@Override
	public void connect() throws RemoteFDMConnectionException{
		try{
			fdm.connect();
		}catch(InterruptedException ex){
			throw new RemoteFDMConnectionException("Failed to connect to Flightgear");
		}
	}

	@Override
	public void disconnect() {
		fdm.disconnect();
	}

	@Override
	public void FDMStateUpdated(NetworkFDM fdm, FDMData fdmData) {
		//TODO: I should copy the data instead
		this.fdmData = fdmData;
	}
}
