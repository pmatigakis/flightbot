package com.matigakis.flightbot.ui.views;

import javax.swing.JFrame;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

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
	
	private final SensorsPanel sensorsPanel;
	private final AircraftPanel aircraftPanel; 
	private final MapPanel mapPanel;
	
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
			public void actionPerformed(ActionEvent e) {
				close();
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
		c.insets = new Insets(10, 10, 10, 10);
		
		sensorsPanel = new SensorsPanel();
		c.gridx = 0;
		c.gridy = 0;
		add(sensorsPanel, c);
		
		mapPanel = new MapPanel();
		c.gridx = 1;
		c.gridy = 0;
		add(mapPanel, c);
				
		aircraftPanel = new AircraftPanel();
		c.gridx = 2;
		c.gridy = 0;
		add(aircraftPanel, c);
		
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
		sensorsPanel.updateFromAircraftData(aircraft);
		aircraftPanel.updateFromAircraftData(aircraft);
		mapPanel.updateSensorView(aircraft.getGPS());
		
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
	
	public void close(){
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}
