package com.matigakis.flightbot.ui.views;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.matigakis.fgcontrol.fdm.Accelerations;
import com.matigakis.fgcontrol.fdm.Atmosphere;
import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.fgcontrol.fdm.Orientation;
import com.matigakis.fgcontrol.fdm.Position;
import com.matigakis.fgcontrol.fdm.Velocities;
import com.matigakis.flightbot.ui.views.information.OrientationPanel;
import com.matigakis.flightbot.ui.views.sensors.AccelerationsPanel;
import com.matigakis.flightbot.ui.views.sensors.AtmosphericPanel;
import com.matigakis.flightbot.ui.views.sensors.PositionPanel;
import com.matigakis.flightbot.ui.views.sensors.VelocitiesPanel;

public class FDMDataPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private PositionPanel positionPanel;
	private VelocitiesPanel velocitiesPanel;
	private AccelerationsPanel accelerationsPanel;
	private OrientationPanel orientationPanel;
	private AtmosphericPanel atmosphericpanel;
	
	public FDMDataPanel(){
		super();
		
		//GridBagLayout layout = new GridBagLayout();
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		
		setAlignmentX(LEFT_ALIGNMENT);
		setAlignmentY(BOTTOM_ALIGNMENT);
		
		positionPanel = new PositionPanel();
		add(positionPanel);
		
		velocitiesPanel = new VelocitiesPanel();
		add(velocitiesPanel);
		
		accelerationsPanel = new AccelerationsPanel();
		add(accelerationsPanel);
		
		orientationPanel = new OrientationPanel();
		add(orientationPanel);
		
		atmosphericpanel = new AtmosphericPanel();
		add(atmosphericpanel);
	}
	
	public void updateFromFDMData(FDMData fdmData){
		Position position = fdmData.getPosition();
		positionPanel.updatePosition(position);
		
		Accelerations accelerations = fdmData.getAccelerations();
		accelerationsPanel.updateAccelerations(accelerations);
		
		Velocities velocities = fdmData.getVelocities();
		velocitiesPanel.updateVelocities(velocities);
		
		Orientation orientation = fdmData.getOrientation();
		orientationPanel.updateFromOrientation(orientation);
		
		Atmosphere atmosphere = fdmData.getAtmosphere();
		atmosphericpanel.updateAtmosphere(atmosphere);
	}
}
