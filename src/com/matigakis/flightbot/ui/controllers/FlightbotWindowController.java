package com.matigakis.flightbot.ui.controllers;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.flightbot.ui.views.FlightbotView;

/**
 * The FlightbotWindowController is responsible of controlling the main FlightBot application
 * window.
 */
public class FlightbotWindowController implements FlightbotViewController{
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightbotWindowController.class);
	
	private FlightbotView flightbotView;
		
	public void attachView(FlightbotView flightbotView){
		this.flightbotView = flightbotView;
	}
	
	/**
	 * Shut down FlightBot
	 */
	@Override
	public void exit() {
		LOGGER.info("Shutting down FlightBot");
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				flightbotView.close();
			}
		});
		
		System.exit(0);
	}
}
