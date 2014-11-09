package com.matigakis.flightbot.aircraft.sensors;

import com.matigakis.fgcontrol.fdm.FDMData;;

/**
 * The StaticPressureSensor holds the pressure of the air around the aircraft.
 */
public class StaticPressureSensor extends PressureSensor{

	/**
	 * Update the pressure using data from the sensors. 
	 */
	@Override
	public void updateFromFDMData(FDMData fdmData) {
		setPressure(fdmData.getAtmosphere().getStaticPressure());
	}
}
