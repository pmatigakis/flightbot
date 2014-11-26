package com.matigakis.flightbot.ui.views.information;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.matigakis.fgcontrol.fdm.Controls;
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
		//orientationPanel.updateFromInstrumentation(aircraft.getInstrumentation());
		//instrumentationPanel.updateFromInstrumentation(aircraft.getInstrumentation());
	}
	
	public void updateControls(Controls controls){
		controlsPanel.updateControls(controls);
	}
}
