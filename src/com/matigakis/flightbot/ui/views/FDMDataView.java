package com.matigakis.flightbot.ui.views;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import com.matigakis.fgcontrol.fdm.FDMData;

/**
 * Interface for every object that is responsible of rendering
 * fdm data
 */
public interface FDMDataView {
	void updateFDMData(FDMData fdmData);
	void addMarker(MapMarker marker);
	void removeMarker(MapMarker marker);
}
