package com.matigakis.flightbot.aircraft.sensors;

import com.matigakis.fgcontrol.fdm.FDMData;;

public class PitotTube extends PressureSensor {
	@Override
	public void updateFromFDMData(FDMData fdmData) {
		setPressure(fdmData.getAtmosphere().getTotalPressure());
	}
}
