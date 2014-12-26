package com.matigakis.flightbot;

import java.net.InetSocketAddress;

import javax.swing.JOptionPane;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.BasicConfigurator;

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
import com.matigakis.flightbot.ui.controllers.FlightbotWindowController;
import com.matigakis.flightbot.ui.views.FlightBotWindow;

public class FlightBot {
	private FDMData fdmData;
	private ScheduledExecutorService updater;
	
	private final FlightbotWindowController controller;
	
	private Aircraft aircraft;
	
	private long autopilotUpdateRate;
	private long dataViewerUpdateRate;
	
	FGControl fgControl;
	NetworkFDM fdm;
	
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
		
		controller = new FlightbotWindowController(configuration, fgControl, aircraft);
		
		FlightBotWindow window = new FlightBotWindow(controller);
		controller.attachView(window);
		
		updater = Executors.newScheduledThreadPool(2);
		
		updater.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				controller.updateFDMData(getFDMData());
			}
		}, 0, dataViewerUpdateRate, TimeUnit.MILLISECONDS);
		
		updater.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				controller.updateAircraftState(fdmData);
				
				if (controller.isAutopilotActive()){
					Controls controls = controller.runAutopilot();
				
					fdm.transmitControls(controls );
				
					controller.updateControls(controls);
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
	
	public void setFDMData(FDMData fdmData){
		this.fdmData = fdmData;
	}
	
	public FDMData getFDMData(){
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
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Failed to connect to Flightgear");
			System.exit(-1);
		}
	}
}
