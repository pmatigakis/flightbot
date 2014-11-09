package com.matigakis.flightbot.tests.aircraft.sensors;

import com.matigakis.fgcontrol.fdm.Accelerations;
import com.matigakis.fgcontrol.fdm.Atmosphere;
import com.matigakis.fgcontrol.fdm.Controls;
import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.fgcontrol.fdm.Location;
import com.matigakis.fgcontrol.fdm.Orientation;

public class DummyFDMData extends FDMData{
	public DummyFDMData(){
		super();
		
		Accelerations accelerations = getAccelerations();
		accelerations.setXAcceleration(0.5);
		accelerations.setYAcceleration(0.3);
		accelerations.setZAcceleration(30.0);
		accelerations.setRollRate(2.0);
		accelerations.setPitchRate(1.5);
		accelerations.setYawRate(1.0);
		
		Location location = getLocation();
		location.setLatitude(37.937056);
		location.setLongitude(23.94667);
		location.setAltitude(1000.0);
		location.setAirspeed(60.0);
		location.setHeading(170.0);
		
		Atmosphere atmosphere = getAtmosphere();
		atmosphere.setPitotTubePressure(31.5);
		atmosphere.setStaticPressure(29.8);
		atmosphere.setTemperature(26.5);

		//xMagn = 1.1;
		//yMagn = 2.2;
		//zMagn = 3.3;
		
		Orientation orientation = getOrientation();
		orientation.setRoll(10.5);
		orientation.setPitch(12.5);
		orientation.setYaw(3.1);
		
		Controls controls = getControls();
		controls.setElevator(0.5);
		controls.setAileron(-0.6);
		controls.setRudder(0.7);
		controls.setThrottle(0.9);
	
		setSimulationTime(120.67);
	}
}
