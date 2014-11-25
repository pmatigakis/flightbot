package com.matigakis.flightbot.ui.views.sensors;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.matigakis.fgcontrol.fdm.Accelerations;

public class AccelerationsPanel extends JPanel{	
	private static final long serialVersionUID = 1L;
	
	private final JTextField xAccelerationText;
	private final JTextField yAccelerationText;
	private final JTextField zAccelerationText;
	
	public AccelerationsPanel(){
		super();
		
		setBorder(BorderFactory.createTitledBorder("Accelerations"));
		
		GridBagLayout layout = new GridBagLayout();  
		setLayout(layout);
		
		//setAlignmentY(TOP_ALIGNMENT);
		
		JLabel xAccelerationLabel = new JLabel("X acceleration (fps)");
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		add(xAccelerationLabel, c);
		
		xAccelerationText = new JTextField(10);
		xAccelerationText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 0;
		add(xAccelerationText, c);
		
		JLabel yAccelerationLabel = new JLabel("Y acceleration (fps)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 1;
		add(yAccelerationLabel, c);
		
		yAccelerationText = new JTextField(10);
		yAccelerationText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 1;
		add(yAccelerationText, c);
		
		JLabel zAccelerationLabel = new JLabel("Z acceleration (fps)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 2;
		add(zAccelerationLabel, c);
		
		zAccelerationText = new JTextField(10);
		zAccelerationText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 2;
		add(zAccelerationText, c);
		
		setVisible(true);
	}

	public void updateAccelerations(Accelerations accelerations) {
		String xAcceleration = String.valueOf(accelerations.getXAcceleration());
		String yAcceleration = String.valueOf(accelerations.getYAcceleration());
		String zAcceleration = String.valueOf(accelerations.getZAcceleration());
		
		xAccelerationText.setText(xAcceleration);
		yAccelerationText.setText(yAcceleration);
		zAccelerationText.setText(zAcceleration);
	}
}
