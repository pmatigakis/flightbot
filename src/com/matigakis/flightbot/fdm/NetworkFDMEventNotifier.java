package com.matigakis.flightbot.fdm;

/**
 * Interface for an object that can raise NetworkFDM events
 */
public interface NetworkFDMEventNotifier {
	void addEventListener(NetworkFDMEventListener networkFDMEventListener);
	void stateUpdated();
}
