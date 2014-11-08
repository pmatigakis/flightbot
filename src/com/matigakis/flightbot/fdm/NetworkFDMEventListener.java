package com.matigakis.flightbot.fdm;

/**
 * Interface for an object that can receive NetworkFDM events
 */
public interface NetworkFDMEventListener {
	void networkFDMStateUpdated(NetworkFDM fdm);
}
