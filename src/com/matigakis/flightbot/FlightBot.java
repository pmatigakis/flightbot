package com.matigakis.flightbot;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.configuration.FDMManager;
import com.matigakis.flightbot.fdm.NetworkFDMEventListener;
import com.matigakis.flightbot.fdm.NetworkFDM;
import com.matigakis.flightbot.fdm.NetworkFDMFactory;
import com.matigakis.flightbot.ui.controllers.TelemetryViewController;
import com.matigakis.flightbot.ui.controllers.TelemetryWindowController;
import com.matigakis.flightbot.ui.views.TelemetryWindow;

/**
 * FlightBot is an autopilot simulator application. At the momment it
 * supports autopilots written in Jython. FlightBot is using Flightgear as
 * it's flight dynamics model.
 */
public final class FlightBot{
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightBot.class);
	
	private final NetworkFDM fdm;
	
	private final TelemetryViewController controller;
	//private TelemetryView view;
	
	private final Aircraft aircraft;
	//private final Autopilot autopilot;
	
	//public FlightBot(NetworkFDM fdm, Autopilot autopilot){
	public FlightBot(NetworkFDM fdm){
		//view = new TelemetryWindow();
		//controller = new TelemetryWindowController((TelemetryWindow) view);
		
		//view.attachController(controller);
		
		aircraft = new Aircraft();
		
		TelemetryWindow telemetryWindow = new TelemetryWindow();
		controller = new TelemetryWindowController(telemetryWindow);
		
		telemetryWindow.attachController(controller);
		
		//this.autopilot = autopilot;
		this.fdm = fdm;
		
		fdm.addEventListener(new NetworkFDMEventListener() {			
			@Override
			public void stateUpdated(NetworkFDM fdm) {
				fdm.updateAircraftState(aircraft);
			
				if(controller.getAutopilotState()){
					//controller.updateAircraftControls();
					Autopilot autopilot = controller.getAutopilot();
				
					autopilot.updateControls(aircraft);
					
					fdm.transmitAircraftControls(aircraft.getControls());
				}
				
				controller.updateView(aircraft);
			}
		});
		
		telemetryWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				stop();
			}
		});
	}

	
	/*
	private void updateAutopilot(){
		autopilot.updateControls(aircraft);
	}
	*/
	
	/**
	 * Run the simulation
	 * 
	 * @throws InterruptedException
	 */
	public void run() throws InterruptedException{
		LOGGER.info("Starting the telemetry viewer");
		fdm.connect();
	}
	
	/**
	 * Stop the simulation
	 */
	public void stop(){
		LOGGER.info("Stopping the telemetry viewer");
		fdm.disconnect();
	}
	
	public static void main(String[] args) throws Exception{
		BasicConfigurator.configure();
		
		/*
		//Initialize the command line parser an read the arguments
		CommandLine commandLine;
				
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
		*/
		
		//load the configuration
		Configuration configuration;
		
		try {
			configuration = new XMLConfiguration("config/settings.xml");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		//Create a network fdm using the configuration data
		FDMManager fdmManager = new FDMManager(configuration);
		
		NetworkFDMFactory fdmFactory = (NetworkFDMFactory) fdmManager.getFDMFactory();
		
		NetworkFDM fdm = (NetworkFDM) fdmFactory.createFDM();
		
		/*
		//Load an autopilot
		String autopilotPackage = commandLine.getOptionValue("autopilot");
		
		AutopilotLoader autopilotLoader = new JythonAutopilotLoader(autopilotPackage);
		
		Autopilot autopilot = autopilotLoader.getAutopilot();
		*/
		
		//Create the telemetry window
		//FlightBot flightbot = new FlightBot(fdm, autopilot);
		FlightBot flightbot = new FlightBot(fdm);
		
		flightbot.run();
	}
}
