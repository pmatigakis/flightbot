package com.matigakis.flightbot;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.fgcontrol.fdm.RemoteFDM;
import com.matigakis.flightbot.configuration.FDMConfigurationException;
import com.matigakis.flightbot.configuration.FDMManager;
import com.matigakis.flightbot.fdm.FDM;
import com.matigakis.flightbot.fdm.RemoteFDMFactory;
import com.matigakis.flightbot.ui.controllers.AutopilotViewController;
import com.matigakis.flightbot.ui.controllers.JythonAutopilotViewController;
import com.matigakis.flightbot.ui.controllers.TelemetryViewController;
import com.matigakis.flightbot.ui.controllers.TelemetryWindowController;
import com.matigakis.flightbot.ui.views.FlightBotWindow;
import com.matigakis.flightbot.services.TelemetryViewUpdater;
import com.matigakis.flightbot.services.AutopilotUpdater;

/**
 * FlightBot is an autopilot simulator application. At the moment it
 * supports autopilots written in Jython. FlightBot is using Flightgear as
 * it's flight dynamics model.
 */
public final class FlightBot extends WindowAdapter{
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightBot.class);
	
	private final RemoteFDM fdm;
	
	private final TelemetryViewController telemetryViewController;
	private final AutopilotViewController autopilotViewController;
	
	private final ScheduledThreadPoolExecutor backgroundServices;
	
	private boolean running;
	
	private long guiUpdateRate;
	private long autopilotUpdateRate;
	
	private FlightBot(Configuration configuration) throws FDMConfigurationException{
		//Create the FDM object
		FDMManager fdmManager = new FDMManager(configuration);
		
		RemoteFDMFactory fdmFactory = fdmManager.getFDMFactory();
				
		fdm = fdmFactory.createRemoteFDM();
		
		//Set up the views and the controllers for those views
		autopilotViewController = new JythonAutopilotViewController();
		telemetryViewController = new TelemetryWindowController();
		
		FlightBotWindow FlightBotWindow = new FlightBotWindow(telemetryViewController, autopilotViewController);
		
		autopilotViewController.attachAutopilotView(FlightBotWindow);
		telemetryViewController.attachTelemetryView(FlightBotWindow);
		
		FlightBotWindow.addWindowListener(this);
		
		//Set up the update rates for the telemetry window and the autopilot.
		//The update rates have to be converted to long type in milliseconds
		//because in the configuration file they are given in seconds.
		guiUpdateRate = (long)(1000 * configuration.getDouble("viewer.update_rate"));
		autopilotUpdateRate = (long)(1000 * configuration.getDouble("autopilot.update_rate"));
		
		backgroundServices = new ScheduledThreadPoolExecutor(2);
		
		running = false;
	}

	/**
	 * Run the simulation
	 * 
	 * @throws InterruptedException 
	 */
	public void run() throws InterruptedException{
		if(!running){
			LOGGER.info("Starting FlightBot");
			
			fdm.connect();
			
			TelemetryViewUpdater telemetryViewUpdater = new TelemetryViewUpdater((FDM) fdm, telemetryViewController);
			
			backgroundServices.scheduleAtFixedRate(telemetryViewUpdater, 0, guiUpdateRate, TimeUnit.MILLISECONDS);
			
			AutopilotUpdater autopilotUpdater = new AutopilotUpdater((FDM) fdm, autopilotViewController);
			
			backgroundServices.scheduleAtFixedRate(autopilotUpdater, 0, autopilotUpdateRate, TimeUnit.MILLISECONDS);
			
			running = true;
		}else{
			LOGGER.info("FlightBot is already running");
		}
	}
	
	/**
	 * Stop the simulation
	 */
	public void stop(){
		if(running){
			LOGGER.info("Stopping FlightBot");
			
			fdm.disconnect();
			
			backgroundServices.shutdown();
			
			running = false;
		}else{
			LOGGER.info("FlightBot is not running");
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		stop();
	}
	
	public static void main(String[] args) throws Exception{
		BasicConfigurator.configure();
		
		//load the configuration
		Configuration configuration;
		
		try {
			configuration = new XMLConfiguration("config/settings.xml");
		} catch (ConfigurationException e) {
			e.printStackTrace();
			return;
		}
		
		//Start FlightBot
		FlightBot flightbot = new FlightBot(configuration);
		
		flightbot.run();
	}
}
