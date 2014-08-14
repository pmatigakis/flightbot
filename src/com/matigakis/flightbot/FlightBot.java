package com.matigakis.flightbot;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
import com.matigakis.flightbot.configuration.FDMConfigurationException;
import com.matigakis.flightbot.configuration.FDMManager;
import com.matigakis.flightbot.fdm.FDM;
import com.matigakis.flightbot.fdm.FDMFactory;
import com.matigakis.flightbot.fdm.NetworkFDMFactory;

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
			e.printStackTrace();
			System.out.println("Failed to load configuration");
			return;
		}
		
		double dt = configuration.getDouble("simulation.dt");
	
		FDMManager fdmManager = new FDMManager(configuration);
		
		FDMFactory fdmFactory;
		try{
			fdmFactory = fdmManager.getFDMFactory();
		}catch(FDMConfigurationException ex){
			ex.printStackTrace();
			System.out.println("Failed to load the FDM");
			return;
		}
		
		final FDM fdm = fdmFactory.createFDM();
		
		String autopilotPackage = commandLine.getOptionValue("autopilot");
		
		AutopilotLoader autopilotLoader = new JythonAutopilotLoader(autopilotPackage);
		
		final Autopilot autopilot = autopilotLoader.getAutopilot();
		
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		
		Runnable updateSimulation = new Runnable() {
			private Aircraft aircraft = new Aircraft();
			
			@Override
			public void run() {
				fdm.run(aircraft);
				autopilot.updateControls(aircraft);
			}
		};
		
		executor.scheduleAtFixedRate(updateSimulation, 0, (long)(dt*1000), TimeUnit.MILLISECONDS);
	}
}
