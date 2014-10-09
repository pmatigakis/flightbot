package com.matigakis.flightbot.ui.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.ui.views.sensors.AccelerometerPanel;
import com.matigakis.flightbot.ui.views.sensors.AtmosphericPanel;
import com.matigakis.flightbot.ui.views.sensors.GPSPanel;
import com.matigakis.flightbot.ui.views.sensors.GyroscopePanel;
import com.matigakis.flightbot.ui.views.sensors.MagnetometerPanel;

public class SensorsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final GPSPanel gpsPanel;
	private final AccelerometerPanel accelerometerPanel;
	private final GyroscopePanel gyroscopePanel;
	private final MagnetometerPanel magnetormeterPanel;
	private final AtmosphericPanel atmosphericpanel;
	
	public SensorsPanel(){
		super();
		
		//GridBagLayout layout = new GridBagLayout();
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		
		setAlignmentX(LEFT_ALIGNMENT);
		setAlignmentY(BOTTOM_ALIGNMENT);
		
		gpsPanel = new GPSPanel();
		add(gpsPanel);
		
		accelerometerPanel = new AccelerometerPanel();
		add(accelerometerPanel);
		
		gyroscopePanel = new GyroscopePanel();
		add(gyroscopePanel);
		
		magnetormeterPanel = new MagnetometerPanel();
		add(magnetormeterPanel);
		
		atmosphericpanel = new AtmosphericPanel();
		add(atmosphericpanel);
		
		//add(Box.createGlue());
	}
	
	public void updateFromAircraftData(Aircraft aircraft){
		gpsPanel.updateSensorView(aircraft.getGPS());
		accelerometerPanel.updateSensorView(aircraft.getAccelerometer());
		gyroscopePanel.updateSensorView(aircraft.getGyroscope());
		magnetormeterPanel.updateSensorView(aircraft.getMagnetometer());
		atmosphericpanel.updateSensorView(aircraft.getPitotTube(), aircraft.getStaticPressureSensor(), aircraft.getTemperatureSensor());
	}
}
