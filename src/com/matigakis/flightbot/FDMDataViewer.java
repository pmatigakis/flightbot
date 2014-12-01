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
import com.matigakis.fgcontrol.network.FDMDataListener;
import com.matigakis.fgcontrol.network.FDMDataServer;
import com.matigakis.flightbot.configuration.FDMConfigurationException;
import com.matigakis.flightbot.configuration.FDMManager;
import com.matigakis.flightbot.ui.controllers.FDMDataViewController;
import com.matigakis.flightbot.ui.controllers.FDMDataWindowController;
import com.matigakis.flightbot.ui.views.FDMDataWindow;

/**
 * The TelemetryViewer displays the data received from the flight
 * dynamics model
 */
public final class FDMDataViewer{
	private static final Logger LOGGER = LoggerFactory.getLogger(FDMDataViewer.class);
	
	private FDMDataServer fdmDataServer;
	private FDMDataViewController fdmDataViewController;
	private boolean running;
	
	public FDMDataViewer(Configuration configuration) throws FDMConfigurationException{
		//Create the FDM server object
		FDMManager fdmManager = new FDMManager(configuration);
		
		fdmDataServer = fdmManager.getViewerFDMDataServer();
		
		fdmDataServer.addFDMDataListener(new FDMDataListener() {
			@Override
			public void handleFDMData(FDMData fdmData) {
				updateUsingFDMData(fdmData);
			}
		});
		
		//Set up the fdm views views and the controller for that view
		fdmDataViewController = new FDMDataWindowController();
		
		FDMDataWindow fdmDataWindow = new FDMDataWindow(fdmDataViewController);
		
		fdmDataViewController.attachTelemetryView(fdmDataWindow);
		
		//Update the view when new data from the FDM have arrived
		fdmDataWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});
		
		running = false;
	}

	private void updateUsingFDMData(FDMData fdmData){
		fdmDataViewController.updateView(fdmData);
	}
	
	public void run() throws InterruptedException{
		if(!running){
			LOGGER.info("Starting the telemetry viewer");
			
			fdmDataServer.startServer();
				
			running = true;
		}else{
			LOGGER.info("The telemetry viewer is already running");
		}
	}
	
	public void stop(){
		if(running){
			LOGGER.info("Stopping the telemetry viewer");
			
			fdmDataServer.stopServer();
			
			running = false;
		}else{
			LOGGER.info("The telemetry viewer is not running");
		}
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
		
		//Start the fdm data viewer
		FDMDataViewer fdmDataViewer = new FDMDataViewer(configuration);
		
		fdmDataViewer.run();
	}
}
