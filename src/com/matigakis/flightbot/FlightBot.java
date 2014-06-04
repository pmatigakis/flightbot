package com.matigakis.flightbot;

import org.apache.log4j.BasicConfigurator;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.Configuration;

import com.matigakis.flightbot.aircraft.controllers.AircraftController;
import com.matigakis.flightbot.fdm.FDM;
import com.matigakis.flightbot.flightgear.controllers.FlightgearAutopilotFactory;
import com.matigakis.flightbot.flightgear.fdm.FlightgearFDMFactory;
import com.matigakis.flightbot.flightgear.simulators.FlightgearSimulator;
import com.matigakis.flightbot.simulators.Simulator;
import com.matigakis.flightbot.ui.views.AircraftDataRenderer;
import com.matigakis.flightbot.ui.views.TelemetryView;

/**
 * FlightBot is an autopilot framework for flightgear.
 */
public final class FlightBot{
	public static void main(String[] args) throws Exception{
		CommandLine commandLine;
		
		BasicConfigurator.configure();
				
		//load an autopilot
		Options options = new Options();
		Option autopilotOption = OptionBuilder
				.withLongOpt("autopilot")
				.hasArgs()
				.withArgName("NAME")
				.withDescription("The name of he autopilot to load")
				.create();
		options.addOption(autopilotOption);
		
		CommandLineParser parser = new PosixParser();
		
		try{
			commandLine = parser.parse(options, args);
		}catch(ParseException ex){
			System.out.println("Failed to parse arguments");
			return;
		}
		
		String autopilotName = commandLine.getOptionValue("autopilot");
		
		//load the configuration
		Configuration configuration = new XMLConfiguration("config/settings.xml");
		
		String host = configuration.getString("protocol.host");
		int sensorsPort = configuration.getInt("protocol.sensors");
		int controlsPort = configuration.getInt("protocol.controls");
		
		//Start the simulation
		FDM fdm = FlightgearFDMFactory.createFDM(sensorsPort);
		
		AircraftController autopilot  = FlightgearAutopilotFactory.getAutopilot(host, controlsPort, autopilotName);
		
		AircraftDataRenderer aircraftDataRenderer = new TelemetryView();
		
		Simulator simulator = new FlightgearSimulator(aircraftDataRenderer, fdm, autopilot);
		
		simulator.run(0.05);
		simulator.shutdown();
	}
}
