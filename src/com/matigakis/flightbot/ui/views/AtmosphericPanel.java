package com.matigakis.flightbot.ui.views;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

import java.text.NumberFormat;

import com.matigakis.flightbot.sensors.PitotTube;
import com.matigakis.flightbot.sensors.StaticPressureSensor;
import com.matigakis.flightbot.sensors.TemperatureSensor;

public class AtmosphericPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final JTextField dynamicPressureText;
	private final JTextField staticPressureText;
	private final JFormattedTextField temperatureText;
	
	public AtmosphericPanel(){
		super();
		
		setBorder(BorderFactory.createTitledBorder("Atmoshperic data"));
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		
		JLabel dynamicPressureLabel = new JLabel("Dynamic pressure (inHg)");
		dynamicPressureText = new JTextField(10);
		dynamicPressureText.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		add(dynamicPressureLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		add(dynamicPressureText, c);
		
		JLabel staticPressureLabel = new JLabel("Static pressure (inHg)");
		staticPressureText = new JTextField(10);
		staticPressureText.setEditable(false);
		c.gridx = 0;
		c.gridy = 1;
		add(staticPressureLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		add(staticPressureText, c);
		
		NumberFormat temperatureFormat = NumberFormat.getNumberInstance();
		temperatureFormat.setMaximumFractionDigits(1);
		JLabel temperatureLabel = new JLabel("Temperature (C)");
		temperatureText = new JFormattedTextField(temperatureFormat);
		temperatureText.setColumns(10);
		temperatureText.setEditable(false);
		c.gridx = 0;
		c.gridy = 2;
		add(temperatureLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		add(temperatureText, c);
		
		setVisible(true);
	}

	public void updateSensorView(PitotTube pitotTube, StaticPressureSensor staticPressureSensor, TemperatureSensor temperatureSensor) {
		double dynamicPressure = pitotTube.getPressure();
		double staticPressure = staticPressureSensor.getPressure();
		double temperature = temperatureSensor.getTemperature();
		
		dynamicPressureText.setText(String.valueOf(dynamicPressure));
		staticPressureText.setText(String.valueOf(staticPressure));
		temperatureText.setValue(temperature);
	}
}
