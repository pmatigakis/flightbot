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

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.aircraft.controllers.loaders.AutopilotLoader;
import com.matigakis.flightbot.aircraft.controllers.loaders.JythonAutopilotLoader;
import com.matigakis.flightbot.fdm.NetworkFDM;

public final class FlightBot{	
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
		
		double dt = configuration.getDouble("simulation.dt");
		
		String host = configuration.getString("controls.host");
		int sensorsPort = configuration.getInt("sensors.port");
		int controlsPort = configuration.getInt("controls.port");
	
		String autopilotPackage = commandLine.getOptionValue("autopilot");
		
		AutopilotLoader autopilotLoader = new JythonAutopilotLoader(autopilotPackage);
		Autopilot autopilot = autopilotLoader.getAutopilot();

		Aircraft aircraft = new Aircraft();
		
		NetworkFDM fdm = new NetworkFDM(host, sensorsPort, controlsPort);
		
		try {
			fdm.connect();
		} catch (InterruptedException e) {
			System.out.println("Failed to connect to fdm");
			return;
		}
		
		int waitTime = (int)(1000 * dt);
		
		try{
			while(true){
				fdm.run(aircraft);
				autopilot.updateControls(aircraft);
				
				Thread.sleep(waitTime);
			}
		}catch(InterruptedException ex){
			System.out.println("Shutting down");
		}
		
		fdm.disconnect();
	}
}
