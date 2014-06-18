package com.matigakis.flightbot.ui.views;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.matigakis.flightbot.aircraft.sensors.Gyroscope;

public class GyroscopePanel extends JPanel implements SensorRenderer<Gyroscope>{	
	private static final long serialVersionUID = 1L;
	
	private final JTextField xRotationText;
	private final JTextField yRotationText;
	private final JTextField zRotationText;
	
	public GyroscopePanel(){
		super();
		
		setBorder(BorderFactory.createTitledBorder("Gyroscope"));
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		
		JLabel xRotationLabel = new JLabel("X rotation (dps)");
		xRotationText = new JTextField(10);
		xRotationText.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		add(xRotationLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		add(xRotationText, c);
		
		JLabel yRotationLabel = new JLabel("Y rotation (dps)");
		yRotationText = new JTextField(10);
		yRotationText.setEditable(false);
		c.gridx = 0;
		c.gridy = 1;
		add(yRotationLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		add(yRotationText, c);
		
		JLabel zRotationLabel = new JLabel("Z rotation (dps)");
		zRotationText = new JTextField(10);
		zRotationText.setEditable(false);
		c.gridx = 0;
		c.gridy = 2;
		add(zRotationLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		add(zRotationText, c);
		
		setVisible(true);
	}

	@Override
	public void updateSensorView(Gyroscope sensor) {
		double xRotation = sensor.getXRotation();
		double yRotation = sensor.getYRotation();
		double zRotation = sensor.getZRotation();
		
		xRotationText.setText(String.valueOf(xRotation));
		yRotationText.setText(String.valueOf(yRotation));
		zRotationText.setText(String.valueOf(zRotation));
	}
}
