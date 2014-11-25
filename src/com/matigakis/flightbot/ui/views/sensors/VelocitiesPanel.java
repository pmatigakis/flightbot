package com.matigakis.flightbot.ui.views.sensors;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.matigakis.fgcontrol.fdm.Velocities;

public class VelocitiesPanel extends JPanel{	
	private static final long serialVersionUID = 1L;
	
	private JTextField calibratedAirspeedText;
	private JTextField climbRateText;
	private JTextField rollRateText;
	private JTextField pitchRateText;
	private JTextField yawRateText;
	private JTextField northVelocityText;
	private JTextField eastVelocityText;
	private JTextField verticalVelocityText;
	private JTextField uText;
	private JTextField vText;
	private JTextField wText;
	
	public VelocitiesPanel(){
		super();
		
		setBorder(BorderFactory.createTitledBorder("Velocities"));
		
		GridBagLayout layout = new GridBagLayout();  
		setLayout(layout);
		
		JLabel calibratedAirspeedLabel = new JLabel("Calibrated airspeed (kts)");
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		add(calibratedAirspeedLabel, c);
		
		calibratedAirspeedText = new JTextField(10);
		calibratedAirspeedText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 0;
		add(calibratedAirspeedText, c);
		
		JLabel climbRateLabel = new JLabel("Climb rate (fps)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 1;
		add(climbRateLabel, c);
		
		climbRateText = new JTextField(10);
		climbRateText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 1;
		add(climbRateText, c);
		
		JLabel rollRateLabel = new JLabel("Roll rate (dps)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 2;
		add(rollRateLabel, c);
		
		rollRateText = new JTextField(10);
		rollRateText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 2;
		add(rollRateText, c);
		
		JLabel pitchRateLabel = new JLabel("Pitch rate (dps)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 3;
		add(pitchRateLabel, c);
		
		pitchRateText = new JTextField(10);
		pitchRateText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 3;
		add(pitchRateText, c);
		
		JLabel yawRateLabel = new JLabel("Yaw rate (dps)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 4;
		add(yawRateLabel, c);
		
		yawRateText = new JTextField(10);
		yawRateText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 4;
		add(yawRateText, c);
		
		JLabel northVelocityLabel = new JLabel("North velocity (fps)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 5;
		add(northVelocityLabel, c);
		
		northVelocityText = new JTextField(10);
		northVelocityText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 5;
		add(northVelocityText, c);
		
		JLabel eastVelocityLabel = new JLabel("East velocity (fps)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 6;
		add(eastVelocityLabel, c);
		
		eastVelocityText = new JTextField(10);
		eastVelocityText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 6;
		add(eastVelocityText, c);
		
		JLabel verticalVelocityLabel = new JLabel("Vertical velocity (fps)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 7;
		add(verticalVelocityLabel, c);
		
		verticalVelocityText = new JTextField(10);
		verticalVelocityText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 7;
		add(verticalVelocityText, c);
		
		JLabel uVelocityLabel = new JLabel("U (fps)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 8;
		add(uVelocityLabel, c);
		
		uText = new JTextField(10);
		uText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 8;
		add(uText, c);
		
		JLabel vVelocityLabel = new JLabel("V (fps)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 9;
		add(vVelocityLabel, c);
		
		vText = new JTextField(10);
		vText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 9;
		add(vText, c);
		
		JLabel wVelocityLabel = new JLabel("W (fps)");
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 10;
		add(wVelocityLabel, c);
		
		wText = new JTextField(10);
		wText.setEditable(false);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 10;
		add(wText, c);
		
		setVisible(true);
	}

	public void updateVelocities(Velocities velocities) {
		String calibratedAirspeed = String.valueOf(velocities.getCalibratedAirspeed());
		String climbRate = String.valueOf(velocities.getClimbRate());
		String rollRate = String.valueOf(velocities.getRollRate());
		String pitchRate = String.valueOf(velocities.getPitchRate());
		String yawRate = String.valueOf(velocities.getYawRate());
		String northVelocity = String.valueOf(velocities.getNorthVelocity());
		String eastVelocity = String.valueOf(velocities.getEastVelocity());
		String verticalVelocity = String.valueOf(velocities.getVerticalVelocity());
		String u = String.valueOf(velocities.getU());
		String v = String.valueOf(velocities.getV());
		String w = String.valueOf(velocities.getW());
		
		calibratedAirspeedText.setText(calibratedAirspeed);
		climbRateText.setText(climbRate);
		rollRateText.setText(rollRate);
		pitchRateText.setText(pitchRate);
		yawRateText.setText(yawRate);
		northVelocityText.setText(northVelocity);
		eastVelocityText.setText(eastVelocity);
		verticalVelocityText.setText(verticalVelocity);
		uText.setText(u);
		vText.setText(v);
		wText.setText(w);
	}
}
