package com.matigakis.flightbot.fdm;

import com.matigakis.fgcontrol.fdm.FDMData;
import com.matigakis.fgcontrol.fdm.RemoteFDM;
import com.matigakis.fgcontrol.fdm.RemoteFDMStateListener;

public class RemoteFDMAdapter implements RemoteFDMStateListener{

	@Override
	public void connectedToRemoteFDM(RemoteFDM fdm) {}

	@Override
	public void disconnectedFromRemoteFDM(RemoteFDM fdm) {}

	@Override
	public void fdmDataReceived(RemoteFDM fdm, FDMData fdmData) {}
}
