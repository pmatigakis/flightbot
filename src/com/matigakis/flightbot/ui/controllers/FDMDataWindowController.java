package com.matigakis.flightbot.ui.controllers;

import java.util.LinkedList;
import java.util.List;

import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.flightbot.ui.views.FDMDataView;

public class FDMDataWindowController implements FDMDataViewController{
	private List<FDMDataView> fdmDataViews;
	
	public FDMDataWindowController(){
		fdmDataViews = new LinkedList<FDMDataView>();
	}
	
	@Override
	public void updateFDMData(FDMData fdmData) {
		for(FDMDataView fdmDataView: fdmDataViews){
			fdmDataView.updateFDMData(fdmData);
		}
	}

	@Override
	public void attachFDMDataView(FDMDataView fdmDataView) {
		fdmDataViews.add(fdmDataView);
	}

	@Override
	public void detachFDMDataView(FDMDataView fdmDataView) {
		fdmDataViews.remove(fdmDataView);
	}

}
