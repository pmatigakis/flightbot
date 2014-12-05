package com.matigakis.flightbot;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetSocketAddress;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.flightbot.configuration.FDMConfigurationException;
import com.matigakis.flightbot.configuration.FDMManager;
import com.matigakis.flightbot.fdm.RemoteFDMAdapter;
import com.matigakis.fgcontrol.FGControl;
import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.fgcontrol.fdm.RemoteFDM;
import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.controllers.JythonAutopilot;
import com.matigakis.flightbot.aircraft.controllers.loaders.JythonAutopilotLoader;
import com.matigakis.flightbot.ui.controllers.AutopilotWindowController;
import com.matigakis.flightbot.ui.controllers.FlightgearWindowController;
import com.matigakis.flightbot.ui.views.AutopilotWindow;

public class JythonAutopilotRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(JythonAutopilotRunner.class);
	
	private long autopilotUpdateRate;
	private AutopilotWindowController autopilotWindowController;
	private FlightgearWindowController flightgearWindowController;
	private RemoteFDM fdm;
	private FGControl fgControl;
	private boolean running;
	
	public JythonAutopilotRunner(Configuration configuration, String autopilotPackage) throws FDMConfigurationException{
		JythonAutopilotLoader autopilotLoader = new JythonAutopilotLoader(autopilotPackage);
		
		JythonAutopilot autopilot = (JythonAutopilot) autopilotLoader.getAutopilot();
		
		autopilotWindowController = new AutopilotWindowController(autopilot);
		
		InetSocketAddress consoleAddress = new InetSocketAddress(configuration.getString("flightgear.console.host"), configuration.getInt("flightgear.console.port"));
		fgControl = new FGControl(consoleAddress);
		
		flightgearWindowController = new FlightgearWindowController(fgControl);
		AutopilotWindow autopilotWindow = new AutopilotWindow(autopilotWindowController, flightgearWindowController);	
		
		autopilot.setOutputStream(autopilotWindow.getDebugConsoleStream());
		
		autopilotWindowController.attachAutopilotView(autopilotWindow);
		
		FDMManager fdmManager = new FDMManager(configuration);
		
		fdm = fdmManager.getRemoteFDM();
		
		fdm.addRemoteFDMStateListener(new RemoteFDMAdapter(){
			@Override
			public void fdmDataReceived(RemoteFDM fdm, FDMData fdmData) {
				runAutopilot(fdm, fdmData);
			}
		});
		
		autopilotWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});
		
		autopilotUpdateRate = (long)(1000 * configuration.getDouble("autopilot.update_rate"));
		
		running = false;
	}
	
	private void runAutopilot(RemoteFDM fdm, FDMData fdmData){
		if (autopilotWindowController.isAutopilotActivated()){
			autopilotWindowController.run(fdmData);
			
			Aircraft aircraft = autopilotWindowController.getAircraft();
			
			fdm.transmitControls(aircraft.getControls());
		}
	}
	
	private void stop(){
		if(running){
			LOGGER.info("Shutting down the autopilot simulator");
		
			fgControl.disconnect();
			fdm.disconnect();
			
			running = false;
		}else{
			LOGGER.info("The autopilot simulator is not running");
		}
	}
	
	public void run() throws InterruptedException{
		if(!running){
			LOGGER.info("Starting the autopilot simulator");
		
			fgControl.connect();
			fdm.connect();
		
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
