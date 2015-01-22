package com.matigakis.flightbot.ui.controllers;

import java.io.File;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.fgcontrol.fdm.Controls;
import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.aircraft.controllers.JythonAutopilot;
import com.matigakis.flightbot.ui.views.AutopilotView;

/**
 * The AutopilotWindowController is used to update the autopilot window and run the autopilot.
 */
public class AutopilotWindowController implements AutopilotViewController{
	private static final Logger LOGGER = LoggerFactory.getLogger(AutopilotWindowController.class);
	
	private List<AutopilotView> autopilotViews;
	private Aircraft aircraft;
	private Autopilot autopilot;
	private OutputStream outputStream;
	private Configuration configuration;
	
	public AutopilotWindowController(Configuration configuration, Aircraft aircraft){
		this.aircraft = aircraft;
		this.configuration = configuration;
		
		autopilotViews = new LinkedList<AutopilotView>();
	}
	
	@Override
	public boolean isAutopilotActive() {
		return aircraft.isAutopilotActive();
	}

	@Override
	public void setAutopilotEnabled(boolean enabled) {
		if(autopilot != null){
			aircraft.setAutopilotActive(enabled);
		
			for(AutopilotView autopilotView: autopilotViews){
				autopilotView.updateAutopilotState(enabled);
			}
		}else{
			JOptionPane.showMessageDialog(null, "No autopilot is loaded");
		}
	}

	@Override
	public void resetAutopilot() {
		if(autopilot != null){
			autopilot.reset();
		}else{
			JOptionPane.showMessageDialog(null, "No autopilot is loaded");
		}
	}

	@Override
	public void loadJythonAutopilot() {
		aircraft.setAutopilotActive(false);
		
		for(AutopilotView autopilotView: autopilotViews){
			autopilotView.updateAutopilotState(false);
		}
		
		try{
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("python module", "py");
			fileChooser.setFileFilter(filter);
				
			int retval = fileChooser.showOpenDialog(null);
			if (retval == JFileChooser.APPROVE_OPTION){
				File file = fileChooser.getSelectedFile();
				autopilot = new JythonAutopilot(file, configuration);
				JythonAutopilot jythonAutopilot = (JythonAutopilot) autopilot;
				if(outputStream != null){
					jythonAutopilot.setOutputStream(outputStream);
				}
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Failed to load the new autopilot");
			LOGGER.error("Failed to load the new autopilot", e);
			autopilot = null;
		}
	}

	@Override
	public Controls runAutopilot() {
		try{
			synchronized (aircraft) {
				autopilot.updateControls(aircraft);		
			}
		}catch(Exception e){
			LOGGER.error("The autopilot encountered a critical error", e);
			
			aircraft.setAutopilotActive(false);
			
			for(AutopilotView autopilotView: autopilotViews){
				autopilotView.updateAutopilotState(false);
			}
			
			JOptionPane.showMessageDialog(null, "The autopilot encountered a critical error");
		}
		
		return aircraft.getControls();
	}

	@Override
	public void attachAutopilotView(AutopilotView autopilotView) {
		autopilotViews.add(autopilotView);
	}

	@Override
	public void detachAutopilotView(AutopilotView autopilotView) {
		autopilotViews.remove(autopilotView);
	}

	@Override
	public void setAutopilotOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public void updateControls() {		
		Controls controls = aircraft.getControls();
		
		synchronized(controls) {
			for(AutopilotView autopilotView: autopilotViews){
				autopilotView.updateControls(controls);
			}	
		}
	}
}
