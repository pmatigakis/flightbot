package com.matigakis.flightbot.ui.views;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.OutputStream;

import com.matigakis.fgcontrol.fdm.Controls;
import com.matigakis.flightbot.ui.controllers.AutopilotViewController;
import com.matigakis.flightbot.ui.controllers.FlightgearViewController;
import com.matigakis.flightbot.ui.views.information.ControlsPanel;

public class AutopilotWindow extends JFrame implements AutopilotView{
	private static final long serialVersionUID = 1L;
	
	private AutopilotViewController autopilotViewController;
	private FlightgearViewController flightgearViewController;
	private ControlsPanel controlsPanel;
	private JMenuItem startMenuItem;
	private JMenuItem stopMenuItem;
	private JMenuItem pauseFlightgearMenuItem;
	private JMenuItem resetFlightgearMenuItem;
	private JTextArea debugText;
	private OutputStream consoleStream;

	public AutopilotWindow(AutopilotViewController autopilotViewController, FlightgearViewController flightgearViewController){
		this.autopilotViewController = autopilotViewController;
		this.flightgearViewController = flightgearViewController;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setTitle("Autopilot controller");
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWIndow();
			}
		});
		fileMenu.add(exitMenuItem);
		
		menuBar.add(fileMenu);
		
		JMenu autopilotMenu = new JMenu("Autopilot");
		startMenuItem = new JMenuItem("Start");
		autopilotMenu.add(startMenuItem);
		startMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				activateAutopilot();
			}
		});
		
		stopMenuItem = new JMenuItem("Stop");
		stopMenuItem.setEnabled(false);
		autopilotMenu.add(stopMenuItem);
		stopMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deactivateAutopilot();
			}
		});
		
		JMenuItem resetMenuItem = new JMenuItem("Reset");
		//resetMenuItem.setEnabled(false);
		resetMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetAutopilot();
			}
		});
		autopilotMenu.add(resetMenuItem);
		
		JMenu flightgearMenu = new JMenu("Flightgear");

		pauseFlightgearMenuItem = new JMenuItem("Pause");
		pauseFlightgearMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pauseFlightgear();
			}
		});
		flightgearMenu.add(pauseFlightgearMenuItem);

		resetFlightgearMenuItem = new JMenuItem("Reset");
		resetFlightgearMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetFlightgear();
			}
		});
		flightgearMenu.add(resetFlightgearMenuItem);
		
		menuBar.add(autopilotMenu);
		menuBar.add(flightgearMenu);
		
		setJMenuBar(menuBar);
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		
		controlsPanel = new ControlsPanel();
		add(controlsPanel, c);
		
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill =  GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.gridwidth = 3;
		c.gridheight = 1;
		debugText = new JTextArea();
		debugText.setRows(10);
		debugText.setColumns(40);
		debugText.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(debugText);
		add(scrollPane, c);
		
		consoleStream = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				debugText.append(String.valueOf((char)b));
				debugText.setCaretPosition(debugText.getDocument().getLength());
			}
		};
		
		pack();
		setResizable(false);

		setVisible(true);
	}

	private void closeWIndow(){
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	@Override
	public void updateAutopilotControls(Controls controls) {
		controlsPanel.updateControls(controls);
	}
	
	private void pauseFlightgear(){
		flightgearViewController.pause();
	}

	private void resetFlightgear(){
		flightgearViewController.reset();
	}

	private void activateAutopilot(){
		autopilotViewController.activateAutopilot();
	}
	
	private void resetAutopilot(){
		autopilotViewController.resetAutopilot();
	}

	private void deactivateAutopilot(){
		autopilotViewController.deactivateAutopilot();
	}

	@Override
	public void updateAutopilotState(boolean autopilotEnabled) {
		if(autopilotEnabled){
			//autopilotViewController.activateAutopilot();
			startMenuItem.setEnabled(false);
			stopMenuItem.setEnabled(true);
		}else{
			//autopilotViewController.deactivateAutopilot();
			startMenuItem.setEnabled(true);
			stopMenuItem.setEnabled(false);
		}
		
	}
	
	public OutputStream getDebugConsoleStream(){
		return consoleStream;
	}
}
