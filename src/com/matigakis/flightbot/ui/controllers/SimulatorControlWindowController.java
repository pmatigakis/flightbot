package com.matigakis.flightbot.ui.controllers;

import com.matigakis.fgcontrol.FGControl;

public class SimulatorControlWindowController implements SimulatorControlViewController{
	private FGControl fgControl;
	
	public SimulatorControlWindowController(FGControl fgControl){
		this.fgControl = fgControl;
	}
	
	@Override
	public void pause() {
		fgControl.pause();
	}

	@Override
	public void reset() {
		fgControl.reset();
	}

}
