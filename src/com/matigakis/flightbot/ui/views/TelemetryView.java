package com.matigakis.flightbot.ui.views;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.matigakis.flightbot.aircraft.Aircraft;

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
	private final OperationPanel operationPanel;
	
	public TelemetryView(){
		super();
		
		setTitle("Telemetry viewer");
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		GridBagLayout layout = new GridBagLayout();    
		setLayout(layout);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		
		JMenu autopilotMenu = new JMenu("Autopilot");
		JMenuItem startMenuItem = new JMenuItem("Start");
		JMenuItem stopMenuItem = new JMenuItem("Stop");
		JMenuItem resetMenuItem = new JMenuItem("Reset");
		autopilotMenu.add(startMenuItem);
		autopilotMenu.add(stopMenuItem);
		autopilotMenu.add(resetMenuItem);
		
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
		
		operationPanel = new OperationPanel();
		c.gridx = 3;
		c.gridy = 1;
		add(operationPanel, c);
		
		gpsPanel = new GPSPanel();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 4;
		add(gpsPanel, c);
		
		setJMenuBar(menuBar);
	
		pack();
		setResizable(false);
		
		setVisible(true);
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
		
		aircraft.setAutopilotState(operationPanel.isAutopilotActive());
	}

	@Override
	public boolean rendererActive() {
		return isVisible();
	}
}
