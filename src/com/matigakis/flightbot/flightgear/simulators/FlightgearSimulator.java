package com.matigakis.flightbot.flightgear.simulators;

import com.matigakis.flightbot.aircraft.controllers.AircraftController;
import com.matigakis.flightbot.fdm.FDM;
import com.matigakis.flightbot.flightgear.controllers.FlightgearAircraftController;
import com.matigakis.flightbot.flightgear.fdm.FlightgearFDM;
import com.matigakis.flightbot.simulators.Simulator;
import com.matigakis.flightbot.ui.views.AircraftDataRenderer;

public class FlightgearSimulator extends Simulator{
	public FlightgearSimulator(AircraftDataRenderer aircraftDataRenderer, FDM fdm, AircraftController aircraftController){
		super();
		
		this.aircraftDataRenderer = aircraftDataRenderer;
		this.fdm = fdm;
		this.aircraftController = aircraftController;
	}
	
	@Override
	public void shutdown() {
		FlightgearFDM flightgearFDM = (FlightgearFDM) fdm;
		flightgearFDM.shutdownFDM();
		
		FlightgearAircraftController flightgearAircraftController = (FlightgearAircraftController) aircraftController; 
		flightgearAircraftController.closeConnection();
	}
}
