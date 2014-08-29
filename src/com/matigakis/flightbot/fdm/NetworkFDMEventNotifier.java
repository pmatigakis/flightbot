package com.matigakis.flightbot.fdm;

public interface NetworkFDMEventNotifier {
	void addEventListener(NetworkFDMEventListener networkFDMEventListener);
	void stateUpdated();
}
