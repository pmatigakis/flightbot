package com.matigakis.flightbot;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.BasicConfigurator;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.matigakis.fgcontrol.ControlsClient;
import com.matigakis.fgcontrol.SensorServer;
import com.matigakis.flightbot.aircraft.controllers.AircraftController;
import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.aircraft.controllers.loaders.AutopilotLoader;
import com.matigakis.flightbot.aircraft.controllers.loaders.JythonAutopilotLoader;

public class JythonAutopilot {
	public static void main(String[] args){
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
		Configuration configuration;
		
		try {
			configuration = new XMLConfiguration("config/settings.xml");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Failed to load configuration");
			return;
		}
		
		String host = configuration.getString("controls.host");
		int sensorsPort = configuration.getInt("sensors.port");
		int controlsPort = configuration.getInt("controls.port");
	
		String autopilotPackage = commandLine.getOptionValue("autopilot");
		
		AutopilotLoader autopilotLoader = new JythonAutopilotLoader();
		AircraftController aircraftController = autopilotLoader.getAutopilot(autopilotPackage);

		final ControlsClient controlsClient = new ControlsClient(host, controlsPort);
		
		final SensorServer sensorServer = new SensorServer(sensorsPort);
		
		final Autopilot autopilot = new Autopilot(aircraftController, controlsClient);

		sensorServer.addSensorDataListener(autopilot);
		
		try{
			controlsClient.openConnection();
		}catch(InterruptedException ex){
			System.out.println("Failed to open controls connection");
			return;
		}
		
		autopilot.start();
		sensorServer.start();
		
		Runtime runtime = Runtime.getRuntime();
		
		runtime.addShutdownHook(new Thread(){
			@Override
			public void run() {
				super.run();
				
				sensorServer.stopServer();
				autopilot.setAutopilotState(false);
				controlsClient.closeConnection();
			}
		});
	}
}
