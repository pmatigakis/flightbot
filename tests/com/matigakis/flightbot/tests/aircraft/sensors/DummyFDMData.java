package com.matigakis.flightbot.tests.aircraft.sensors;

import com.matigakis.fgcontrol.fdm.Accelerations;
import com.matigakis.fgcontrol.fdm.Atmosphere;
import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.fgcontrol.fdm.Orientation;
import com.matigakis.fgcontrol.fdm.Position;
import com.matigakis.fgcontrol.fdm.Velocities;

public class DummyFDMData extends FDMData{
	public DummyFDMData(){
		super();
		
		Accelerations accelerations = getAccelerations();
		accelerations.setXAcceleration(0.5);
		accelerations.setYAcceleration(0.3);
		accelerations.setZAcceleration(30.0);
		
		Velocities velocities = getVelocities();
		velocities.setRollRate(2.0);
		velocities.setPitchRate(1.5);
		velocities.setYawRate(1.0);
		velocities.setCalibratedAirspeed(60.0);
		
		Position position = getPosition();
		position.setLatitude(37.937056);
		position.setLongitude(23.94667);
		position.setAltitude(1000.0);
		position.setAGL(800.0);
		
		Atmosphere atmosphere = getAtmosphere();
		atmosphere.setTotalPressure(31.5);
		atmosphere.setStaticPressure(29.8);
		atmosphere.setTemperature(26.5);

		//xMagn = 1.1;
		//yMagn = 2.2;
		//zMagn = 3.3;
		
		Orientation orientation = getOrientation();
		orientation.setRoll(10.5);
		orientation.setPitch(12.5);
		orientation.setHeading(3.1);
		orientation.setAngleOfAttach(3.2);
		orientation.setSideSlipAngle(0.5);
	
		setSimulationTime(120.67);
	}
}
