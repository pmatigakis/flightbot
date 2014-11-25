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

import com.matigakis.flightbot.configuration.FDMConfigurationException;
import com.matigakis.flightbot.configuration.FDMManager;
import com.matigakis.fgcontrol.fdm.RemoteFDM;
import com.matigakis.flightbot.aircraft.controllers.JythonAutopilot;
import com.matigakis.flightbot.aircraft.controllers.loaders.JythonAutopilotLoader;
import com.matigakis.flightbot.services.AutopilotUpdater;
import com.matigakis.flightbot.ui.controllers.AutopilotWindowController;
import com.matigakis.flightbot.ui.views.AutopilotWindow;

public class JythonAutopilotRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(JythonAutopilotRunner.class);
	
	private long autopilotUpdateRate;
	private AutopilotWindowController autopilotWindowController;
	private ScheduledThreadPoolExecutor updateService;
	private RemoteFDM fdm;
	private boolean running;
	
	public JythonAutopilotRunner(Configuration configuration, String autopilotPackage) throws FDMConfigurationException{
		JythonAutopilotLoader autopilotLoader = new JythonAutopilotLoader(autopilotPackage);
		
		JythonAutopilot autopilot = (JythonAutopilot) autopilotLoader.getAutopilot();
		
		autopilotWindowController = new AutopilotWindowController(autopilot);
		AutopilotWindow autopilotWindow = new AutopilotWindow(autopilotWindowController);	
		
		autopilot.setOutputStream(autopilotWindow.getDebugConsoleStream());
		
		autopilotWindowController.attachAutopilotView(autopilotWindow);
		
		FDMManager fdmManager = new FDMManager(configuration);
		
		fdm = fdmManager.getRemoteFDM();
		
		autopilotWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});
		
		autopilotUpdateRate = (long)(1000 * configuration.getDouble("autopilot.update_rate"));
		
		updateService = new ScheduledThreadPoolExecutor(1);
		
		running = false;
	}
	
	private void stop(){
		if(running){
			LOGGER.info("Shutting down the autopilot simulator");
		
			fdm.disconnect();
		
			updateService.shutdown();
			
			running = false;
		}else{
			LOGGER.info("The autopilot simulator is not running");
		}
	}
	
	public void run() throws InterruptedException{
		if(!running){
			LOGGER.info("Starting the autopilot simulator");
		
			fdm.connect();
		
			AutopilotUpdater updater = new AutopilotUpdater(fdm, autopilotWindowController);
		
			updateService.scheduleAtFixedRate(updater, 0, autopilotUpdateRate, TimeUnit.MILLISECONDS);
		
			running = true;
		}else{
			LOGGER.info("The autopilot simulator is already running");
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
		
		//JythonAutopilotRunner jythonAutopilotRunner = new JythonAutopilotRunner(configuration, "autopilots/simple");
		JythonAutopilotRunner jythonAutopilotRunner = new JythonAutopilotRunner(configuration, args[0]);

		jythonAutopilotRunner.run();
	}
}
