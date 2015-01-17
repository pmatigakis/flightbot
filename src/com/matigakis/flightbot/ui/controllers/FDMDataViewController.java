package com.matigakis.flightbot.ui.controllers;

import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.ui.views.FDMDataView;

public interface FDMDataViewController {
	public void updateFDMData(FDMData fdmData);
	public void attachFDMDataView(FDMDataView fdmDataView);
	public void detachFDMDataView(FDMDataView fdmDataView);
}
