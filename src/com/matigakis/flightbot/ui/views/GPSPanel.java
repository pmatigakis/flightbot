package com.matigakis.flightbot.ui.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.matigakis.flightbot.sensors.GPS;


/**
 * The GPSPanel shows information received from the gps.
 */
public class GPSPanel extends JPanel implements SensorRenderer<GPS>{
	private static final long serialVersionUID = 1L;
	
	private final JTextField longitudeText;
	private final JTextField latitudeText;
	private final JTextField altitudeText;
	private final JTextField airspeedText;
	private final JTextField headingText;
	
	public GPSPanel(){
		super();
		
		Border border = BorderFactory.createTitledBorder("GPS");
		setBorder(border);
		
		LayoutManager layout = new GridBagLayout();
		
		setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		
		JLabel longitudeLabel = new JLabel("Longitude (deg)");
		longitudeText = new JTextField(10);
		longitudeText.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		add(longitudeLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		add(longitudeText, c);
		
		JLabel latitudeLabel = new JLabel("Latitude (deg)");
		latitudeText = new JTextField(10);
		latitudeText.setEditable(false);
		c.gridx = 0;
		c.gridy = 1;
		add(latitudeLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		add(latitudeText, c);
		
		JLabel altitudeLabel = new JLabel("Altitude (ft)");
		altitudeText = new JTextField(10);
		altitudeText.setEditable(false);
		c.gridx = 0;
		c.gridy = 2;
		add(altitudeLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		add(altitudeText, c);
		
		JLabel airspeedLabel = new JLabel("Airspeed (kt)");
		airspeedText = new JTextField(10);
		airspeedText.setEditable(false);
		c.gridx = 0;
		c.gridy = 3;
		add(airspeedLabel, c);
		c.gridx = 1;
		c.gridy = 3;
		add(airspeedText, c);
		
		JLabel headingLabel = new JLabel("Heading (deg)");
		headingText = new JTextField(10);
		headingText.setEditable(false);
		c.gridx = 0;
		c.gridy = 4;
		add(headingLabel, c);
		c.gridx = 1;
		c.gridy = 4;
		add(headingText, c);
		
		setVisible(true);
	}
	
	@Override
	public void updateSensorView(GPS sensor) {
		double longitude = sensor.getLongitude();
		double latitude = sensor.getLatitude();
		double altitude = sensor.getAltitude();
		double heading = sensor.getHeading();
		double airspeed = sensor.getAirspeed();
		
		longitudeText.setText(String.valueOf(longitude));
		latitudeText.setText(String.valueOf(latitude));
		altitudeText.setText(String.valueOf(altitude));
		airspeedText.setText(String.valueOf(airspeed));
		headingText.setText(String.valueOf(heading));		
	}
}
