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
import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.aircraft.controllers.AutopilotLoader;
import com.matigakis.flightbot.flightgear.controllers.JythonAutopilotLoader;
import com.matigakis.flightbot.flightgear.fdm.ControlsClient;
import com.matigakis.flightbot.flightgear.fdm.SensorServer;
import com.matigakis.flightbot.ui.controllers.SensorDataRenderer;
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
		SensorServer sensorServer = new SensorServer(sensorsPort);
		
		TelemetryView telemetryView = new TelemetryView();
		SensorDataRenderer telemetryViewController = new SensorDataRenderer(telemetryView);
		
		sensorServer.addSensorDataListener(telemetryViewController);
		
		ControlsClient controlsClient = new ControlsClient(host, controlsPort);
		controlsClient.openConnection();
		
		AutopilotLoader autopilotLoader = new JythonAutopilotLoader();
		AircraftController aircraftController = autopilotLoader.getAutopilot(autopilotName);
		
		Autopilot autopilot = new Autopilot(aircraftController, controlsClient, telemetryView);
		sensorServer.addSensorDataListener(autopilot);
		
		telemetryViewController.start();
		autopilot.start();
		sensorServer.start();
		
		boolean running = true;
		while(running){			
			//TODO: I should use something better than rendererActive
			if (!telemetryView.rendererActive()){
				sensorServer.stopServer();
				telemetryViewController.stopController();
				autopilot.shutdown();
				controlsClient.closeConnection();
				
				running = false;
			}
		}
	}
}
