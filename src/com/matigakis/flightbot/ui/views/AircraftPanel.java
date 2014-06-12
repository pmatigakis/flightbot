package com.matigakis.flightbot.ui.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import com.matigakis.flightbot.aircraft.Aircraft;

public class AircraftPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final ControlsPanel controlsPanel;
	private final OrientationPanel orientationPanel;
	private final InstrumentationPanel instrumentationPanel;
	
	public AircraftPanel(){
		GridBagLayout layout = new GridBagLayout();    
		setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets(0, 0, 10, 0);
		
		instrumentationPanel = new InstrumentationPanel();
		c.gridx = 0;
		c.gridy = 0;
		add(instrumentationPanel, c);
		
		orientationPanel = new OrientationPanel();
		c.gridx = 0;
		c.gridy = 1;
		add(orientationPanel, c);
		
		controlsPanel = new ControlsPanel();
		c.gridx = 0;
		c.gridy = 2;
		add(controlsPanel, c);
	}
	
	public void updateFromAircraftData(Aircraft aircraft){
		controlsPanel.updateFromControls(aircraft.getControls());
		orientationPanel.updateFromOrientation(aircraft.getOrientation());
		instrumentationPanel.updateFromInstrumentation(aircraft.getInstrumentation());
	}
}
