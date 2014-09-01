package com.matigakis.flightbot.fdm;

/**
 * The FDMFactory interface must be implemented by all objects that can
 * create a flight dynamics object
 */
public interface FDMFactory {
	FDM createFDM();
}
