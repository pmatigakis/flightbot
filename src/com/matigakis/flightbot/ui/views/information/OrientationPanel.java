package com.matigakis.flightbot.ui.views.information;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.matigakis.fgcontrol.fdm.Orientation;

/**
 * The OrientationPanel is used to display information about the orientation
 * of the aircraft
 */
public class OrientationPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JTextField rollText;
	private JTextField pitchText;
	private JTextField headingText;
	private JTextField angleOfAttackText;
	private JTextField sideslipAngleText;
	
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
		
		JLabel rollLabel = new JLabel("Roll (deg)");
		rollText = new JTextField();
		rollText.setColumns(10);
		rollText.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		add(rollLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		add(rollText, c);
		
		JLabel pitchLabel = new JLabel("Pitch (deg)");
		pitchText = new JTextField();
		pitchText.setColumns(10);
		pitchText.setEditable(false);
		c.gridx = 0;
		c.gridy = 1;
		add(pitchLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		add(pitchText, c);
		
		JLabel headingLabel = new JLabel("Heading (deg)");
		headingText = new JTextField();
		headingText.setColumns(10);
		headingText.setEditable(false);
		c.gridx = 0;
		c.gridy = 2;
		add(headingLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		add(headingText, c);
		
		JLabel angleOfAttackLabel = new JLabel("Angle of attack (deg)");
		angleOfAttackText = new JTextField();
		angleOfAttackText.setColumns(10);
		angleOfAttackText.setEditable(false);
		c.gridx = 0;
		c.gridy = 3;
		add(angleOfAttackLabel, c);
		c.gridx = 1;
		c.gridy = 3;
		add(angleOfAttackText, c);
		
		JLabel sideslipAngleLabel = new JLabel("sideslip angle (deg)");
		sideslipAngleText = new JTextField();
		sideslipAngleText.setColumns(10);
		sideslipAngleText.setEditable(false);
		c.gridx = 0;
		c.gridy = 4;
		add(sideslipAngleLabel, c);
		c.gridx = 1;
		c.gridy = 4;
		add(sideslipAngleText, c);
		
		setVisible(true);
	}
	
	/**
	 * Update the panel
	 * 
	 * @param orientation
	 */
	public void updateFromOrientation(Orientation orientation){
		rollText.setText(String.valueOf(orientation.getRoll()));
		pitchText.setText(String.valueOf(orientation.getPitch()));
		headingText.setText(String.valueOf(orientation.getHeading()));
		angleOfAttackText.setText(String.valueOf(orientation.getAngleOfAttack()));
		sideslipAngleText.setText(String.valueOf(orientation.getSideSlipAngle()));
	}
}