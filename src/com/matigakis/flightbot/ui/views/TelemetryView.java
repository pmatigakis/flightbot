package com.matigakis.flightbot.ui.views;

import javax.swing.JFrame;

import java.awt.AWTEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.matigakis.flightbot.aircraft.Aircraft;

class ExitAdapter extends WindowAdapter{
	public volatile boolean running;
	
	public ExitAdapter(){
		running = true;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		super.windowClosing(e);
		running = false;
		System.out.println("CLOSING THE DAMN THING");
	}
}

public class TelemetryView extends JFrame implements AircraftDataRenderer{
	private static final long serialVersionUID = 1L;
	
	private final GPSPanel gpsPanel;
	private final AccelerometerPanel accelerometerPanel;
	private final GyroscopePanel gyroscopePanel;
	private final MagnetometerPanel magnetormeterPanel;
	private final AtmosphericPanel atmosphericpanel;
	private final ControlsPanel controlsPanel;
	private final OrientationPanel orientationPanel;
	private final InstrumentationPanel instrumentationPanel;
	//private final OperationPanel operationPanel;
	
	private final JMenuItem startMenuItem; 
	private final JMenuItem stopMenuItem; 
	
	private ExitAdapter exitAdapter;
	
	private volatile boolean autopilotActivated;
	
	public TelemetryView(){
		super();
		
		autopilotActivated = false;
		
		setTitle("Telemetry viewer");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		GridBagLayout layout = new GridBagLayout();    
		setLayout(layout);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();				
			}
		});
		
		JMenu autopilotMenu = new JMenu("Autopilot");
		startMenuItem = new JMenuItem("Start");
		stopMenuItem = new JMenuItem("Stop");
		stopMenuItem.setEnabled(false);
		JMenuItem resetMenuItem = new JMenuItem("Reset");
		autopilotMenu.add(startMenuItem);
		autopilotMenu.add(stopMenuItem);
		autopilotMenu.add(resetMenuItem);
		
		startMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enableAutopilot();
			}
		});
		
		stopMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAutopilot();
			}
		});
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutMenuItem = new JMenuItem("About");
		helpMenu.add(aboutMenuItem);
		
		menuBar.add(fileMenu);
		menuBar.add(autopilotMenu);
		menuBar.add(helpMenu);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		
		accelerometerPanel = new AccelerometerPanel();
		c.gridx = 0;
		c.gridy = 0;
		add(accelerometerPanel, c);
		
		gyroscopePanel = new GyroscopePanel();
		c.gridx = 1;
		c.gridy = 0;
		add(gyroscopePanel, c);
		
		magnetormeterPanel = new MagnetometerPanel();
		c.gridx = 2;
		c.gridy = 0;
		add(magnetormeterPanel, c);
		
		atmosphericpanel = new AtmosphericPanel();
		c.gridx = 3;
		c.gridy = 0;
		add(atmosphericpanel, c);
		
		instrumentationPanel = new InstrumentationPanel();
		c.gridx = 0;
		c.gridy = 1;
		add(instrumentationPanel, c);
		
		orientationPanel = new OrientationPanel();
		c.gridx = 1;
		c.gridy = 1;
		add(orientationPanel, c);
		
		controlsPanel = new ControlsPanel();
		c.gridx = 2;
		c.gridy = 1;
		add(controlsPanel, c);
		
		//operationPanel = new OperationPanel();
		//c.gridx = 3;
		//c.gridy = 1;
		//add(operationPanel, c);
		
		gpsPanel = new GPSPanel();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 4;
		add(gpsPanel, c);
		
		setJMenuBar(menuBar);
	
		pack();
		setResizable(false);
		
		setVisible(true);
		
		System.out.println("Window state STARTING" );
		exitAdapter = new ExitAdapter();
		addWindowListener(exitAdapter);
	}
	
	@Override
	public void updateView(Aircraft aircraft) {
		gpsPanel.updateSensorView(aircraft.getGPS());
		accelerometerPanel.updateSensorView(aircraft.getAccelerometer());
		gyroscopePanel.updateSensorView(aircraft.getGyrescope());
		magnetormeterPanel.updateSensorView(aircraft.getMagnetometer());
		atmosphericpanel.updateSensorView(aircraft.getPitotTube(), aircraft.getStaticPressureSensor(), aircraft.getTemperatureSensor());
		controlsPanel.updateFromControls(aircraft.getControls());
		orientationPanel.updateFromOrientation(aircraft.getOrientation());
		instrumentationPanel.updateFromInstrumentation(aircraft.getInstrumentation());
		
		//aircraft.setAutopilotState(operationPanel.isAutopilotActive());
		aircraft.setAutopilotState(autopilotActivated);
	}

	@Override
	public boolean rendererActive() {
		//return isVisible();
		return exitAdapter.running;
	}
	
	private void enableAutopilot(){
		startMenuItem.setEnabled(false);
		stopMenuItem.setEnabled(true);
		autopilotActivated = true;
	}
	
	private void disableAutopilot(){
		startMenuItem.setEnabled(true);
		stopMenuItem.setEnabled(false);
		autopilotActivated = false;
	}
	
	public boolean isAutopilotActivated(){
		return autopilotActivated;
	}
}
