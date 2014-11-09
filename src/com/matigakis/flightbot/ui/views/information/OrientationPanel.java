package com.matigakis.flightbot.ui.views.information;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import java.text.NumberFormat;

import com.matigakis.fgcontrol.fdm.Orientation;

/**
 * The OrientationPanel is used to display information about the orientation
 * of the aircraft
 */
public class OrientationPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final JFormattedTextField rollText;
	private final JFormattedTextField pitchText;
	
	public OrientationPanel(){
		super();
		
		Border border = BorderFactory.createTitledBorder("Orientation");
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
		
		JLabel rollLabel = new JLabel("Roll (deg)");
		rollText = new JFormattedTextField(textFormat);
		rollText.setColumns(10);
		rollText.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		add(rollLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		add(rollText, c);
		
		JLabel pitchLabel = new JLabel("Pitch (deg)");
		pitchText = new JFormattedTextField(textFormat);
		pitchText.setColumns(10);
		pitchText.setEditable(false);
		c.gridx = 0;
		c.gridy = 1;
		add(pitchLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		add(pitchText, c);
		
		setVisible(true);
	}
	
	/**
	 * Update the panel
	 * 
	 * @param orientation
	 */
	public void updateFromOrientation(Orientation orientation){
		rollText.setValue(orientation.getRoll());
		pitchText.setValue(orientation.getPitch());
	}
}