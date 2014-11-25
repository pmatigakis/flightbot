package com.matigakis.flightbot.ui.controllers;

import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.ui.views.FDMDataView;

/**
 * The interface for the TelemetryView controller
 */
public interface FDMDataViewController {
	void updateView(FDMData fdmData);
	void addMarkers();
	void clearmarkers();
	void attachTelemetryView(FDMDataView telemetryView);
	void detachTelemetryView(FDMDataView telemetryView);
}
