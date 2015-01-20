package com.matigakis.flightbot.maps;

import java.io.IOException;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

public interface MarkerLoader {
	public void loadMarkers(String filename) throws IOException, MarkerLoadException;
	public List<MapMarker> getMarkers();
}
