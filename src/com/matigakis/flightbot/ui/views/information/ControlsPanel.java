package com.matigakis.flightbot.ui.views.information;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.matigakis.fgcontrol.fdm.Controls;

/**
 * The ControlsPanel panel shows the state of the aircraft's controls
 */
public class ControlsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final JTextField aileronText;
	private final JTextField elevatorText;
	private final JTextField rudderText;
	private final JTextField throttleText;
	
	public ControlsPanel(){
		super();
		
		Border border = BorderFactory.createTitledBorder("Controls");
		setBorder(border);
		
		LayoutManager layout = new GridBagLayout();
		
		setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;

		c.weightx = 1.0;
		c.weighty = 1.0;
		
		JLabel aileronLabel = new JLabel("Aileron");
		aileronText = new JTextField(10);
		aileronText.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		add(aileronLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		add(aileronText, c);
		
		JLabel elevatorLabel = new JLabel("Elevator");
		elevatorText = new JTextField(10);
		elevatorText.setEditable(false);
		c.gridx = 0;
		c.gridy = 1;
		add(elevatorLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		add(elevatorText, c);
		
		JLabel rudderLabel = new JLabel("Rudder");
		rudderText = new JTextField(10);
		rudderText.setEditable(false);
		c.gridx = 0;
		c.gridy = 2;
		add(rudderLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		add(rudderText, c);
		
		JLabel throttleLabel = new JLabel("Throttle");
		throttleText = new JTextField(10);
		throttleText.setEditable(false);
		c.gridx = 0;
		c.gridy = 3;
		add(throttleLabel, c);
		c.gridx = 1;
		c.gridy = 3;
		add(throttleText, c);
	}
	
	/**
	 * Update the panel
	 * 
	 * @param controls
	 */
	public void updateControls(Controls controls){
		aileronText.setText(String.valueOf(controls.getAileron()));
		elevatorText.setText(String.valueOf(controls.getElevator()));
		rudderText.setText(String.valueOf(controls.getRudder()));
		throttleText.setText(String.valueOf(controls.getThrottle()));
	}
}
