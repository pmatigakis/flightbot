package com.matigakis.flightbot.ui.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import com.matigakis.flightbot.aircraft.Aircraft;

public class SensorsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final GPSPanel gpsPanel;
	private final AccelerometerPanel accelerometerPanel;
	private final GyroscopePanel gyroscopePanel;
	private final MagnetometerPanel magnetormeterPanel;
	private final AtmosphericPanel atmosphericpanel;
	
	public SensorsPanel(){
		super();
		
		GridBagLayout layout = new GridBagLayout();    
		setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets(0, 0, 10, 0);
		
		gpsPanel = new GPSPanel();
		c.gridx = 0;
		c.gridy = 0;
		add(gpsPanel, c);
		
		accelerometerPanel = new AccelerometerPanel();
		c.gridx = 0;
		c.gridy = 1;
		add(accelerometerPanel, c);
		
		gyroscopePanel = new GyroscopePanel();
		c.gridx = 0;
		c.gridy = 2;
		add(gyroscopePanel, c);
		
		magnetormeterPanel = new MagnetometerPanel();
		c.gridx = 0;
		c.gridy = 3;
		add(magnetormeterPanel, c);
		
		atmosphericpanel = new AtmosphericPanel();
		c.gridx = 0;
		c.gridy = 4;
		add(atmosphericpanel, c);
	}
	
	public void updateFromAircraftData(Aircraft aircraft){
		gpsPanel.updateSensorView(aircraft.getGPS());
		accelerometerPanel.updateSensorView(aircraft.getAccelerometer());
		gyroscopePanel.updateSensorView(aircraft.getGyrescope());
		magnetormeterPanel.updateSensorView(aircraft.getMagnetometer());
		atmosphericpanel.updateSensorView(aircraft.getPitotTube(), aircraft.getStaticPressureSensor(), aircraft.getTemperatureSensor());
	}
}
