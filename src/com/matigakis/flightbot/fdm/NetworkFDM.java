package com.matigakis.flightbot.fdm;

import java.util.List;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.fgcontrol.ControlsClient;
import com.matigakis.fgcontrol.SensorDataListener;
import com.matigakis.fgcontrol.SensorServer;
import com.matigakis.fgcontrol.controls.Controls;
import com.matigakis.fgcontrol.sensors.SensorData;
import com.matigakis.flightbot.aircraft.Aircraft;

/**
 * The NetworkFDM object is used to connect to a flight dynamics model
 * via an UDP connection
 */
public class NetworkFDM implements FDM, SensorDataListener, NetworkFDMEventNotifier{
	protected static final Logger LOGGER = LoggerFactory.getLogger(NetworkFDM.class);
	
	protected List<NetworkFDMEventListener> fdmEventListeners;
	
	protected SensorData sensorData;;
	
	protected String host;
	protected int sensorsPort;
	protected int controlsPort;
	
	protected SensorServer sensorServer;
	protected ControlsClient controlsClient;
	
	public NetworkFDM(String host, int sensorsPort, int controlsPort) {
		this.host = host;
		this.sensorsPort = sensorsPort;
		this.controlsPort = controlsPort;
		
		sensorData = new SensorData();
		
		sensorServer = new SensorServer(sensorsPort);
		sensorServer.addSensorDataListener(this);
		
		controlsClient = new ControlsClient(host, controlsPort);
		
		fdmEventListeners = new LinkedList<NetworkFDMEventListener>();
	}
	
	/**
	 * Connect to the flight dynamics model
	 * 
	 * @throws InterruptedException
	 */
	public void connect() throws InterruptedException {
			sensorServer.start();
			controlsClient.openConnection();
			LOGGER.debug("Connecting to the FDM");
	}
	
	/**
	 * Disconnect
	 */
	public void disconnect() {
			sensorServer.stopServer();
			controlsClient.closeConnection();
			
			LOGGER.debug("Disconnection from the FDM");
	}
	
	@Override
	public void handleSensorData(SensorData sensorData) {
		this.sensorData = sensorData;
		
		stateUpdated();
	}
	
	/**
	 * Initialize an aircraft
	 */
	@Override
	public void init(Aircraft aircraft) {
		aircraft.updateFromSensorData(sensorData);
	}
	
	/**
	 * Update an aircraft
	 */
	@Override
	public void updateAircraftState(Aircraft aircraft) {
		//controlsClient.transmitControls(aircraft.getControls());
		aircraft.updateFromSensorData(sensorData);
	}

	@Override
	public void addEventListener(NetworkFDMEventListener fdmEventListener) {
		fdmEventListeners.add(fdmEventListener);
	}

	@Override
	public void stateUpdated() {
		for(NetworkFDMEventListener fdmEventListener: fdmEventListeners){
			fdmEventListener.stateUpdated(this);
		}
	}
	
	public void transmitAircraftControls(Controls controls){
		controlsClient.transmitControls(controls);
	}
}
