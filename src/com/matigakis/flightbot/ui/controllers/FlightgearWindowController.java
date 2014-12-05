package com.matigakis.flightbot.ui.controllers;

import com.matigakis.fgcontrol.FGControl;

/**
 * The FlightgearWindowController is used to pause, reset, etc Flightgear
 */
public class FlightgearWindowController implements FlightgearViewController{
	private FGControl fgControl;
	
	public FlightgearWindowController(FGControl fgControl){		
		this.fgControl = fgControl;
	}
	
	@Override
	public void pause() {
		fgControl.pause();
	}

	@Override
	public void unpause() {
		fgControl.pause();
	}

	@Override
	public void reset() {
		fgControl.reset();
	}
}
