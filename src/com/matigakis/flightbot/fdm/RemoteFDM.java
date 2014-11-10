package com.matigakis.flightbot.fdm;

/**
 * Interface for remote FDM objects
 */
public interface RemoteFDM extends FDM{
	void connect() throws RemoteFDMConnectionException;
	void disconnect();
}
