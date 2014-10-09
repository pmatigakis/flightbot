package com.matigakis.flightbot.ui.views.information;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.matigakis.flightbot.aircraft.Aircraft;

public class AircraftPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final ControlsPanel controlsPanel;
	private final OrientationPanel orientationPanel;
	private final InstrumentationPanel instrumentationPanel;
	
	public AircraftPanel(){
		super();
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		
		setAlignmentX(LEFT_ALIGNMENT);
		setAlignmentY(BOTTOM_ALIGNMENT);
		
		instrumentationPanel = new InstrumentationPanel();
		add(instrumentationPanel);
		
		orientationPanel = new OrientationPanel();
		add(orientationPanel);
		
		controlsPanel = new ControlsPanel();
		add(controlsPanel);
	}
	
	public void updateFromAircraftData(Aircraft aircraft){
		controlsPanel.updateFromControls(aircraft.getControls());
		orientationPanel.updateFromOrientation(aircraft.getOrientation());
		instrumentationPanel.updateFromInstrumentation(aircraft.getInstrumentation());
	}
}
