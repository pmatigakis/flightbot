package com.matigakis.flightbot.ui.views.information;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import java.text.NumberFormat;

import com.matigakis.flightbot.aircraft.Instrumentation;

/**
 * The InstrumentationPanel is used to display information about the instruments
 * readings of the aircraft.
 */
public class InstrumentationPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final JFormattedTextField altitudeText;
	private final JFormattedTextField airspeedText;
	private final JFormattedTextField headingText;
	
	public InstrumentationPanel(){
		super();
		
		Border border = BorderFactory.createTitledBorder("Instrumentation");
		setBorder(border);
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		
		NumberFormat textFormat = NumberFormat.getInstance();
		textFormat.setMaximumFractionDigits(0);
		
		JLabel altitudeLabel = new JLabel("Altitude (ft)");
		altitudeText = new JFormattedTextField(textFormat);
		altitudeText.setColumns(10);
		altitudeText.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		add(altitudeLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		add(altitudeText, c);
		
		JLabel headingLabel = new JLabel("Heading (deg)");
		headingText = new JFormattedTextField(textFormat);
		headingText.setColumns(10);
		headingText.setEditable(false);
		c.gridx = 0;
		c.gridy = 1;
		add(headingLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		add(headingText, c);
		
		JLabel airspeedLabel = new JLabel("Airspeed (kt)");
		airspeedText = new JFormattedTextField(textFormat);
		airspeedText.setColumns(10);
		airspeedText.setEditable(false);
		c.gridx = 0;
		c.gridy = 2;
		add(airspeedLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		add(airspeedText, c);
		
		setVisible(true);
	}
	
	/**
	 * Update the panel
	 * 
	 * @param instrumentation
	 */
	public void updateFromInstrumentation(Instrumentation instrumentation){
		altitudeText.setValue(instrumentation.getAltitude());
		headingText.setValue(instrumentation.getHeading());
		airspeedText.setValue(instrumentation.getAirspeed());
	}
}
