package com.matigakis.flightbot.ui.controllers;

import java.util.List;
import java.util.LinkedList;

import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.aircraft.controllers.loaders.AutopilotLoader;
import com.matigakis.flightbot.ui.views.AutopilotView;

/**
 * Implementation of the AutopilotViewController interface
 */
public class AutopilotWindowController implements AutopilotViewController{
	private Autopilot autopilot;
	private boolean autopilotActive;
	private List<AutopilotView> autopilotViews;
	
	public AutopilotWindowController(){
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
		autopilotActive = true;
		
		for(AutopilotView autopilotView: autopilotViews){
			autopilotView.updateAutopilotState(autopilotActive);
		}
	}

	@Override
	public void deactivateAutopilot() {
		autopilotActive = false;
		
		for(AutopilotView autopilotView: autopilotViews){
			autopilotView.updateAutopilotState(autopilotActive);
		}
	}

	@Override
	public void loadAutopilot(AutopilotLoader autopilotLoader) {
		//Disable the autopilot menu items just in case something goes wrong
		//and the autopilot module doesn't load properly.
		for(AutopilotView autopilotView: autopilotViews){
			autopilotView.setAutopilotControlsState(false);
		}
		
		deactivateAutopilot();
		
		autopilot = autopilotLoader.getAutopilot();
		
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
}
