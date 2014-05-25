package com.matigakis.flightbot.ui.views;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * The OperationPanel panel is used to control the function that is performed
 * by the autopilot.
 */
public class OperationPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private final JRadioButton onButton; 
	
	public OperationPanel(){
		super();
		
		GridLayout layout = new GridLayout(1, 2);
		setLayout(layout); 
		Border border = BorderFactory.createTitledBorder("Operation"); 
		setBorder(border);
		
		JPanel onOffPanel = new JPanel();
		onOffPanel.setLayout(new GridLayout(2, 1));
		
		ButtonGroup operationSelection = new ButtonGroup();
		
		JRadioButton offButton = new JRadioButton("OFF");
		offButton.setSelected(true);
		
		onButton = new JRadioButton("ON");
		
		operationSelection.add(offButton);
		operationSelection.add(onButton);
		
		onOffPanel.add(offButton);
		onOffPanel.add(onButton);
		
		add(onOffPanel);
		
		JButton resetButton = new JButton("Reset");
		add(resetButton);
				
		setVisible(true);
	}
	
	public boolean isAutopilotActive(){
		return onButton.isSelected();
	}
}
