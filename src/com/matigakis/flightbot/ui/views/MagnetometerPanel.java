package com.matigakis.flightbot.ui.views;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.matigakis.flightbot.sensors.Magnetometer;

public class MagnetometerPanel extends JPanel implements SensorRenderer<Magnetometer>{	
	private static final long serialVersionUID = 1L;
	
	private final JTextField xText;
	private final JTextField yText;
	private final JTextField zText;
	
	public MagnetometerPanel(){
		super();
		
		setBorder(BorderFactory.createTitledBorder("Magnetometer"));
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		
		JLabel xLabel = new JLabel("X magnitude");
		xText = new JTextField(10);
		xText.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		add(xLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		add(xText);
		
		JLabel yLabel = new JLabel("Y magnitude");
		yText = new JTextField(10);
		yText.setEditable(false);
		c.gridx = 0;
		c.gridy = 1;
		add(yLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		add(yText, c);
		
		JLabel zLabel = new JLabel("Z magnitude");
		zText = new JTextField(10);
		zText.setEditable(false);
		c.gridx = 0;
		c.gridy = 2;
		add(zLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		add(zText, c);
		
		setVisible(true);
	}

	@Override
	public void updateSensorView(Magnetometer sensor) {
		double xMagnitude = sensor.getXAxis();
		double yMagnitude = sensor.getYAxis();
		double zMagnitude = sensor.getZAxis();
		
		xText.setText(String.valueOf(xMagnitude));
		yText.setText(String.valueOf(yMagnitude));
		zText.setText(String.valueOf(zMagnitude));
		
	}
}
