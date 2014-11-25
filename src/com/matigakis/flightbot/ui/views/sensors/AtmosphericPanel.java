package com.matigakis.flightbot.ui.views.sensors;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.matigakis.fgcontrol.fdm.Atmosphere;

public class AtmosphericPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final JTextField dynamicPressureText;
	private final JTextField staticPressureText;
	private final JTextField temperatureText;
	
	public AtmosphericPanel(){
		super();
		
		setBorder(BorderFactory.createTitledBorder("Atmoshperic data"));
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		JLabel dynamicPressureLabel = new JLabel("Total pressure (inHg)");
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		add(dynamicPressureLabel, c);
		
		dynamicPressureText = new JTextField(10);
		dynamicPressureText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 0;
		add(dynamicPressureText, c);
		
		JLabel staticPressureLabel = new JLabel("Static pressure (inHg)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 1;
		add(staticPressureLabel, c);
		
		staticPressureText = new JTextField(10);
		staticPressureText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 1;
		add(staticPressureText, c);
		
		JLabel temperatureLabel = new JLabel("Temperature (C)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 2;
		add(temperatureLabel, c);
		
		temperatureText = new JTextField();
		temperatureText.setColumns(10);
		temperatureText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 2;
		add(temperatureText, c);
		
		setVisible(true);
	}

	public void updateAtmosphere(Atmosphere atmosphere) {
		double totalPressure = atmosphere.getTotalPressure();
		double staticPressure = atmosphere.getStaticPressure();
		double temperature = atmosphere.getTemperature();
		
		dynamicPressureText.setText(String.valueOf(totalPressure));
		staticPressureText.setText(String.valueOf(staticPressure));
		temperatureText.setText(String.valueOf(temperature));
	}
}
