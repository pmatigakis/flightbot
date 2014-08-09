package com.matigakis.flightbot;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.BasicConfigurator;

import com.matigakis.flightbot.ui.views.TelemetryView;
import com.matigakis.flightbot.ui.controllers.TelemetryViewController;
import com.matigakis.fgcontrol.SensorServer;

public class TelemetryViewer {
	public static void main(String[] args){
		BasicConfigurator.configure();
		
		Configuration configuration;
		
		try {
			configuration = new XMLConfiguration("settings.xml");
		} catch (ConfigurationException e) {
			//e.printStackTrace();
			System.out.println("Failed to load configuration");
			return;
		}
		
		int sensorsPort = configuration.getInt("telemetryviewer.port");
		
		final SensorServer sensorServer = new SensorServer(sensorsPort);
		
		TelemetryView view = new TelemetryView();
		
		final TelemetryViewController controller = new TelemetryViewController(view);
		
		view.addTelemetryViewListener(controller);
		
		sensorServer.addSensorDataListener(controller);
		
		view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				sensorServer.stopServer();
				controller.stopController();
			}
		});
		
		sensorServer.start();
		controller.start();
	}
}
