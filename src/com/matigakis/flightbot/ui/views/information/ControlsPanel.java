package com.matigakis.flightbot.ui.views.information;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.matigakis.fgcontrol.fdm.Controls;

/**
 * The ControlsPanel panel shows the state of the aircraft's controls
 */
public class ControlsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final JFormattedTextField aileronText;
	private final JFormattedTextField elevatorText;
	private final JFormattedTextField rudderText;
	private final JFormattedTextField throttleText;
	
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
		
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMinimumFractionDigits(2);
		format.setMaximumFractionDigits(4);
		
		JLabel aileronLabel = new JLabel("Aileron");
		aileronText = new JFormattedTextField(format);
		aileronText.setValue(0.0);
		aileronText.setColumns(10);
		aileronText.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		add(aileronLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		add(aileronText, c);
		
		JLabel elevatorLabel = new JLabel("Elevator");
		elevatorText = new JFormattedTextField(format);
		elevatorText.setValue(0.0);
		elevatorText.setColumns(10);
		elevatorText.setEditable(false);
		c.gridx = 0;
		c.gridy = 1;
		add(elevatorLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		add(elevatorText, c);
		
		JLabel rudderLabel = new JLabel("Rudder");
		rudderText = new JFormattedTextField(format);
		rudderText.setValue(0.0);
		rudderText.setColumns(10);
		rudderText.setEditable(false);
		c.gridx = 0;
		c.gridy = 2;
		add(rudderLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		add(rudderText, c);
		
		JLabel throttleLabel = new JLabel("Throttle");
		throttleText = new JFormattedTextField(format);
		throttleText.setValue(0.0);
		throttleText.setColumns(10);
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
		aileronText.setValue(controls.getAileron());
		elevatorText.setValue(controls.getElevator());
		rudderText.setValue(controls.getRudder());
		throttleText.setValue(controls.getThrottle());
	}
}
