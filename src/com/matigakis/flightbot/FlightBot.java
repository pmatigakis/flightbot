package com.matigakis.flightbot;

import org.apache.log4j.BasicConfigurator;

import com.matigakis.flightbot.aircraft.controllers.AircraftController;
import com.matigakis.flightbot.fdm.FDM;
import com.matigakis.flightbot.flightgear.controllers.FlightgearAutopilotFactory;
import com.matigakis.flightbot.flightgear.fdm.FlightgearFDMFactory;
import com.matigakis.flightbot.flightgear.simulators.FlightgearSimulator;
import com.matigakis.flightbot.configuration.Configuration;
import com.matigakis.flightbot.simulators.Simulator;
import com.matigakis.flightbot.ui.views.AircraftDataRenderer;
import com.matigakis.flightbot.ui.views.TelemetryView;

public final class FlightBot{
	public static void main(String[] args) throws Exception{
		BasicConfigurator.configure();
				
		Configuration configuration = new Configuration();
		configuration.loadConfiguration("config/settings.xml");
		
		String host = configuration.getHost();
		int sensorsPort = configuration.getSensorPort();
		int controlsPort = configuration.getControlsPort();
		
		FDM fdm = FlightgearFDMFactory.createFDM(sensorsPort);
		
		AircraftController autopilot  = FlightgearAutopilotFactory.getAutopilot(host, controlsPort, "simple");
		
		AircraftDataRenderer aircraftDataRenderer = new TelemetryView();
		
		Simulator simulator = new FlightgearSimulator(aircraftDataRenderer, fdm, autopilot);
		
		simulator.run(0.05);
		simulator.shutdown();
	}
}
