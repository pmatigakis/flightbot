package com.matigakis.flightbot.ui.controllers;

import java.util.List;
import java.util.LinkedList;

import com.matigakis.flightbot.aircraft.controllers.Autopilot;
import com.matigakis.flightbot.aircraft.controllers.loaders.AutopilotLoader;
import com.matigakis.flightbot.aircraft.controllers.loaders.JythonAutopilotLoader;
import com.matigakis.flightbot.ui.views.AutopilotView;

/**
 * Implementation of the AutopilotViewController interface that is using 
 * autopilots written in Jython
 */
public class JythonAutopilotViewController implements AutopilotViewController{
	private Autopilot autopilot;
	private boolean autopilotActive;
	private List<AutopilotView> autopilotViews;
	
	public JythonAutopilotViewController(){
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
	public void loadAutopilot(String autopilotName) {
		//Disable the autopilot menu items just in case something goes wrong
		//and the autopilot module doesn't load properly.
		for(AutopilotView autopilotView: autopilotViews){
			autopilotView.setAutopilotControlsState(false);
		}
		
		deactivateAutopilot();
		
		//TODO: perhaps the JythonAutopilotLoader should become a static class
		AutopilotLoader autopilotLoader = new JythonAutopilotLoader(autopilotName);
		
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
