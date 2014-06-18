package com.matigakis.flightbot.ui.views;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.matigakis.flightbot.aircraft.sensors.Accelerometer;

public class AccelerometerPanel extends JPanel implements SensorRenderer<Accelerometer>{	
	private static final long serialVersionUID = 1L;
	
	private final JTextField xAccelerationText;
	private final JTextField yAccelerationText;
	private final JTextField zAccelerationText;
	
	public AccelerometerPanel(){
		super();
		
		setBorder(BorderFactory.createTitledBorder("Accelerometer"));
		
		GridBagLayout layout = new GridBagLayout();  
		setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		
		JLabel xAccelerationLabel = new JLabel("X acceleration (fps)");
		xAccelerationText = new JTextField(10);
		xAccelerationText.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		add(xAccelerationLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		add(xAccelerationText, c);
		
		JLabel yAccelerationLabel = new JLabel("Y acceleration (fps)");
		yAccelerationText = new JTextField(10);
		yAccelerationText.setEditable(false);
		c.gridx = 0;
		c.gridy = 1;
		add(yAccelerationLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		add(yAccelerationText, c);
		
		JLabel zAccelerationLabel = new JLabel("Z acceleration (fps)");
		zAccelerationText = new JTextField(10);
		zAccelerationText.setEditable(false);
		c.gridx = 0;
		c.gridy = 2;
		add(zAccelerationLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		add(zAccelerationText, c);
		
		setVisible(true);
	}

	@Override
	public void updateSensorView(Accelerometer sensor) {
		// TODO Auto-generated method stub
		String xAcceleration = String.valueOf(sensor.getXAcceleration());
		String yAcceleration = String.valueOf(sensor.getYAcceleration());
		String zAcceleration = String.valueOf(sensor.getZAcceleration());
		
		xAccelerationText.setText(xAcceleration);
		yAccelerationText.setText(yAcceleration);
		zAccelerationText.setText(zAcceleration);
	}
}
