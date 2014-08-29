package com.matigakis.flightbot;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridBagLayoutInfo;
import java.awt.Insets;

import org.openstreetmap.gui.jmapviewer.JMapViewer;

import com.matigakis.flightbot.ui.views.GPSPanel;
import com.matigakis.flightbot.ui.views.AccelerometerPanel;
import com.matigakis.flightbot.ui.views.GyroscopePanel;
import com.matigakis.flightbot.ui.views.MagnetometerPanel;
import com.matigakis.flightbot.ui.views.AtmosphericPanel;
import com.matigakis.flightbot.ui.views.OrientationPanel;
import com.matigakis.flightbot.ui.views.InstrumentationPanel;
import com.matigakis.flightbot.ui.views.ControlsPanel;
import com.matigakis.flightbot.ui.views.SensorsPanel;
import com.matigakis.flightbot.ui.views.AircraftPanel;

public class TestViewer extends JFrame{
	public TestViewer(){
		setTitle("Test viewer");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		//SensorsPanel sensorsPanel = new SensorsPanel();
		//add(sensorsPanel);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		SensorsPanel sensorsPanel = new SensorsPanel();
		add(sensorsPanel, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		
		JMapViewer mapViewer = new JMapViewer();
		mapViewer.setPreferredSize(new Dimension(500, 500));
		add(mapViewer, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		AircraftPanel aircraftPanel = new AircraftPanel();
		add(aircraftPanel, c);
				
		pack();
		setResizable(false);
		
		setVisible(true);
	}
	
	public static void main(String[] args){
		TestViewer viewer = new TestViewer();
	}
}
