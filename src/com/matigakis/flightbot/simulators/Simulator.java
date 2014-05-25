package com.matigakis.flightbot.simulators;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.controllers.AircraftController;
import com.matigakis.flightbot.fdm.FDM;
import com.matigakis.flightbot.ui.views.AircraftDataRenderer;

public abstract class Simulator {
	protected FDM fdm;
	protected AircraftController aircraftController;
	protected AircraftDataRenderer aircraftDataRenderer;
	
	public void run(double dt) {
		Aircraft aircraft = new Aircraft();
		
		fdm.init(aircraft);
		
		long updateRate = (long) (dt * 1000000000);
		long timeSinceGUIUpdate = System.nanoTime();
		
		//TODO: Do something better here
		while(aircraftDataRenderer.rendererActive()){			
			long timeSinceControlsUpdate = System.nanoTime();
			
			fdm.run(aircraft);
			
			if(aircraft.isAutopilotActive()){
				aircraftController.updateAircraftControls(aircraft);
			}
			
			if(System.nanoTime() - timeSinceGUIUpdate > 500000000){
				//TODO: The autopilot enable state is updated by TelemetryView. Perhaps I should change this
				aircraftDataRenderer.updateView(aircraft);
				
				timeSinceGUIUpdate = System.nanoTime();
			}
			
			long diff = (updateRate - System.nanoTime() - timeSinceControlsUpdate) / 1000000;
			
			if(diff > 0){
				try{
					Thread.sleep(diff);
				}catch(InterruptedException ex){
					return;
				}
			}
		}
	}
	
	public abstract void shutdown();
}
