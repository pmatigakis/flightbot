package com.matigakis.flightbot;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
import com.matigakis.flightbot.aircraft.controllers.loaders.AutopilotLoader;
import com.matigakis.flightbot.aircraft.controllers.loaders.JythonAutopilotLoader;
//import com.matigakis.flightbot.network.ControlsClient;
import com.matigakis.fgcontrol.SensorServer;
import com.matigakis.flightbot.ui.controllers.TelemetryViewController;
import com.matigakis.flightbot.ui.views.TelemetryView;

/**
 * FlightBot is an autopilot framework for flightgear.
 */
public final class FlightBot{
	private SensorServer sensorServer;
	private TelemetryViewController telemetryViewController;
	//private ControlsClient controlsClient;
	private Autopilot autopilot;
	
	public void run(CommandLine commandLine, Configuration configuration) throws Exception{
		String host = configuration.getString("protocol.host");
		int sensorsPort = configuration.getInt("protocol.sensors");
		int controlsPort = configuration.getInt("protocol.controls");
	
		String autopilotName = commandLine.getOptionValue("autopilot");
		
		sensorServer = new SensorServer(sensorsPort);
		
		TelemetryView telemetryView = new TelemetryView();
		telemetryViewController = new TelemetryViewController(telemetryView);
		telemetryView.addTelemetryViewListener(telemetryViewController);
		
		sensorServer.addSensorDataListener(telemetryViewController);
		
		//controlsClient = new ControlsClient(host, controlsPort);
		//controlsClient.openConnection();
		
		AutopilotLoader autopilotLoader = new JythonAutopilotLoader();
		AircraftController aircraftController = autopilotLoader.getAutopilot(autopilotName);
		
		//autopilot = new Autopilot(aircraftController, controlsClient, telemetryViewController);
		//sensorServer.addSensorDataListener(autopilot);
		
		telemetryViewController.start();
		autopilot.start();
		sensorServer.start();
		
		telemetryView.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				sensorServer.stopServer();
				telemetryViewController.stopController();
				autopilot.shutdown();
				//controlsClient.closeConnection();
			}
		});
	}
	
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
		
		//load the configuration
		Configuration configuration = new XMLConfiguration("config/settings.xml");
		
		//Start the simulation
		FlightBot flightBot = new FlightBot();
		
		flightBot.run(commandLine, configuration);
	}
}
