package com.matigakis.flightbot;

import java.net.InetSocketAddress;

import javax.swing.JOptionPane;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.fgcontrol.fdm.Controls;
import com.matigakis.fgcontrol.fdm.NetworkFDM;
import com.matigakis.fgcontrol.fdm.RemoteFDM;
import com.matigakis.fgcontrol.fdm.RemoteFDMConnectionException;
import com.matigakis.fgcontrol.fdm.RemoteFDMStateAdapter;
import com.matigakis.fgcontrol.FGControl;
import com.matigakis.fgcontrol.console.ConsoleConnectionException;
import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.configuration.ConfigurationManager;
import com.matigakis.flightbot.ui.controllers.AutopilotWindowController;
import com.matigakis.flightbot.ui.controllers.FDMDataViewController;
import com.matigakis.flightbot.ui.controllers.FDMDataWindowController;
import com.matigakis.flightbot.ui.controllers.FlightbotWindowController;
import com.matigakis.flightbot.ui.controllers.MapViewController;
import com.matigakis.flightbot.ui.controllers.MapWindowController;
import com.matigakis.flightbot.ui.controllers.SimulatorControlViewController;
import com.matigakis.flightbot.ui.controllers.SimulatorControlWindowController;
import com.matigakis.flightbot.ui.views.FlightBotWindow;

public class FlightBot {
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightBot.class);
	
	private FDMData fdmData;
	private ScheduledExecutorService updater;
	
	private final FlightbotWindowController flightbotViewController;
	
	private Aircraft aircraft;
	
	private long autopilotUpdateRate;
	private long dataViewerUpdateRate;
	
	private FGControl fgControl;
	private NetworkFDM fdm;
	
	public FlightBot(Configuration configuration){
		String flightgearAddress = configuration.getString("flightgear.address");
		int consolePort = configuration.getInt("flightgear.console.port");
		int controlsPort = configuration.getInt("flightgear.controls.port");
		int fdmPort = configuration.getInt("flightgear.fdm.port");
		
		fgControl = new FGControl(new InetSocketAddress(flightgearAddress, consolePort));
		fdm = new NetworkFDM("localhost", fdmPort, controlsPort);
		
		aircraft = new Aircraft();
		aircraft.setAutopilotState(false);
		
		fdmData = new FDMData();
		
		autopilotUpdateRate = (long) (1000 * configuration.getDouble("autopilot.update_rate"));
		dataViewerUpdateRate = (long) (1000 * configuration.getDouble("fdmviewer.update_rate"));
		
		flightbotViewController = new FlightbotWindowController(aircraft);
		
		MapViewController mapViewController = new MapWindowController();
		SimulatorControlViewController simulatorViewController = new SimulatorControlWindowController(fgControl);
		final FDMDataViewController fdmDataViewController = new FDMDataWindowController();
		final AutopilotWindowController autopilotWindowController = new AutopilotWindowController(configuration, aircraft);
		
		FlightBotWindow window = new FlightBotWindow(flightbotViewController, mapViewController, simulatorViewController, autopilotWindowController);
		
		flightbotViewController.attachView(window);
		mapViewController.attachMapView(window);
		fdmDataViewController.attachFDMDataView(window);
		autopilotWindowController.attachAutopilotView(window);
		
		autopilotWindowController.setAutopilotOutputStream(window.getAutopilotConsoleStream());
		
		updater = Executors.newScheduledThreadPool(2);
		
		//Add a background thread that updates the fdm data on the gui at the specified interval
		//defined in the configuration file.
		updater.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				fdmDataViewController.updateFDMData(getFDMData());
			}
		}, 0, dataViewerUpdateRate, TimeUnit.MILLISECONDS);
		
		//Run the autopilot at the specified time interval
		updater.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				flightbotViewController.updateAircraftState(fdmData);
				
				if (aircraft.isAutopilotActive()){
					Controls controls = autopilotWindowController.runAutopilot();
				
					fdm.transmitControls(controls );
				
					autopilotWindowController.updateControls(controls);
				}
			}
		}, 0, autopilotUpdateRate, TimeUnit.MILLISECONDS);
		
		fdm.addRemoteFDMStateListener(new RemoteFDMStateAdapter() {
			@Override
			public void fdmDataReceived(RemoteFDM fdm, FDMData fdmData) {
				setFDMData(fdmData);
			}
		});
	}
	
	public void run() throws ConsoleConnectionException, RemoteFDMConnectionException{
		fgControl.connect();
		fdm.connect();
	}
	
	public void stop(){
		fgControl.disconnect();
		fdm.disconnect();
		updater.shutdown();
	}
	
	private void setFDMData(FDMData fdmData){
		this.fdmData = fdmData;
	}
	
	private FDMData getFDMData(){
		return fdmData;
	}
	
	public static void main(String[] args) throws Exception{
		BasicConfigurator.configure();
		
		Configuration configuration = ConfigurationManager.getConfiguration();
		
		final FlightBot flightbot = new FlightBot(configuration);
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				flightbot.stop();
			}
		});
		
		try{
			flightbot.run();
		}catch(ConsoleConnectionException | RemoteFDMConnectionException e){
			LOGGER.error("Failed to connect to Flightgear", e);
			
			JOptionPane.showMessageDialog(null, "Failed to connect to Flightgear");
			
			System.exit(-1);
		}catch(Exception e){
			LOGGER.error("FlightBot encountered a critical error", e);
			
			JOptionPane.showMessageDialog(null, "FlightBot encountered a critical error");
			
			System.exit(-1);
		}
	}
}
