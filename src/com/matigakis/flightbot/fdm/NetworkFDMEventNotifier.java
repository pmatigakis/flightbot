package com.matigakis.flightbot.fdm;

/**
 * Interface for an object that can raise NetworkFDM events
 */
public interface NetworkFDMEventNotifier {
	void addNetworkFDMEventListener(NetworkFDMEventListener networkFDMEventListener);
	void networkFDMStateUpdated();
}
