package com.matigakis.flightbot;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.fgcontrol.fdm.NetworkFDMStateListener;
import com.matigakis.fgcontrol.fdm.NetworkFDM;
import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.configuration.FDMConfigurationException;
import com.matigakis.flightbot.configuration.FDMManager;
import com.matigakis.flightbot.fdm.NetworkFDMFactory;
import com.matigakis.flightbot.ui.controllers.AutopilotViewController;
import com.matigakis.flightbot.ui.controllers.JythonAutopilotViewController;
import com.matigakis.flightbot.ui.controllers.TelemetryViewController;
import com.matigakis.flightbot.ui.controllers.TelemetryWindowController;
import com.matigakis.flightbot.ui.views.FlightBotWindow;

/**
 * FlightBot is an autopilot simulator application. At the moment it
 * supports autopilots written in Jython. FlightBot is using Flightgear as
 * it's flight dynamics model.
 */
public final class FlightBot extends WindowAdapter implements NetworkFDMStateListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightBot.class);
	
	private final NetworkFDM fdm;
	
	private final TelemetryViewController telemetryViewController;
	private final AutopilotViewController autopilotViewController;
	
	private final Aircraft aircraft;
	
	private FlightBot(NetworkFDM fdm){
		this.fdm = fdm;
		
		aircraft = new Aircraft();
		
		autopilotViewController = new JythonAutopilotViewController();
		telemetryViewController = new TelemetryWindowController();
		
		FlightBotWindow FlightBotWindow = new FlightBotWindow(telemetryViewController, autopilotViewController);
		
		autopilotViewController.attachAutopilotView(FlightBotWindow);
		telemetryViewController.attachTelemetryView(FlightBotWindow);
		
		fdm.addFDMStateListener(this);
		
		FlightBotWindow.addWindowListener(this);
	}


	/**
	 * Create a FlightBot object using the configuration data
	 * 
	 * @param configuration the configuration object
	 * @return a FlightBot object
	 * @throws FDMConfigurationException
	 */
	public static FlightBot fromConfiguration(Configuration configuration) throws FDMConfigurationException{
		FDMManager fdmManager = new FDMManager(configuration);
				
		NetworkFDMFactory fdmFactory = fdmManager.getFDMFactory();
				
		NetworkFDM fdm = (NetworkFDM) fdmFactory.createFDM();
		
		FlightBot flightbot = new FlightBot(fdm);
		
		return flightbot;
	}
	
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
	
	@Override
	public void windowClosing(WindowEvent e) {
		stop();
	}
	
	/**
	 * Update the aircraft controls and transmit the new values
	 * to the FDM if the autopilot is activated
	 */
	private void updateAircraftControls(){
		if(autopilotViewController.isAutopilotActivated()){
			Autopilot autopilot = autopilotViewController.getAutopilot();
						
			autopilot.updateControls(aircraft);
			
			fdm.transmitControls(aircraft.getControls());
		}
	}
	
	/**
	 * Update the views that are active
	 */
	private void updateViews(){		
		telemetryViewController.updateView(aircraft);
	}
	
	@Override
	public void FDMStateUpdated(NetworkFDM fdm, FDMData fdmData) {
		aircraft.updateFromFDMData(fdmData);
		
		updateAircraftControls();
		
		updateViews();
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
		
		FlightBot flightbot = FlightBot.fromConfiguration(configuration);
		
		flightbot.run();
	}
}
