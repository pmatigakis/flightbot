package com.matigakis.flightbot.ui.controllers;

import java.util.List;
import java.util.LinkedList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.ui.views.AutopilotView;

/**
 * Implementation of the AutopilotViewController interface
 */
public class AutopilotWindowController implements AutopilotViewController{
	private static final Logger LOGGER = LoggerFactory.getLogger(AutopilotWindowController.class);
	
	private Aircraft aircraft;
	private Autopilot autopilot;
	private boolean autopilotActive;
	private List<AutopilotView> autopilotViews;
	
	public AutopilotWindowController(Autopilot autopilot){
		this.autopilot = autopilot;
		aircraft = new Aircraft();
		autopilotViews = new LinkedList<AutopilotView>();
		autopilotActive = false;
	}
	
	public void attachAutopilotView(AutopilotView autopilotView){
		autopilotViews.add(autopilotView);
	}
	
	public void detachAutopilotView(AutopilotView autopilotView){
		autopilotViews.remove(autopilotView);
	}
	
	@Override
	public void activateAutopilot() {
		LOGGER.info("Activating the autopilot");
		
		autopilotActive = true;
		
		for(AutopilotView autopilotView: autopilotViews){
			autopilotView.updateAutopilotState(autopilotActive);
		}
	}

	@Override
	public void deactivateAutopilot() {
		LOGGER.info("Deactivating the autopilot");
		
		autopilotActive = false;
		
		for(AutopilotView autopilotView: autopilotViews){
			autopilotView.updateAutopilotState(autopilotActive);
		}
	}

	@Override
	public boolean isAutopilotActivated() {
		return autopilotActive;
	}

	@Override
	public Autopilot getAutopilot() {
		return autopilot;
	}

	@Override
	public void run(FDMData fdmData) {
		aircraft.updateFromFDMData(fdmData);
		
		autopilot.updateControls(aircraft);
		
		for(AutopilotView autopilotView: autopilotViews){
			autopilotView.updateAutopilotControls(aircraft.getControls());
		}
	}

	@Override
	public Aircraft getAircraft() {
		return aircraft;
	}
}
