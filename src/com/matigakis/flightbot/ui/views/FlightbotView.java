package com.matigakis.flightbot.ui.views;

import java.io.OutputStream;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import com.matigakis.fgcontrol.fdm.Controls;
import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.aircraft.Aircraft;

public interface FlightbotView {
	void addMapMarkers(List<MapMarker> mapMarkers);
	void removeAllMapMarkers();
	void updateFDMData(FDMData fdmData);
	void updateControls(Controls controls);
	void updateAircraftState(Aircraft aircraft);
	OutputStream getConsoleStream();
	void close();
}
