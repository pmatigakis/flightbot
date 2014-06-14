package com.matigakis.flightbot.util;

/**
 * The Navigator class provides some helper functions that are useful for navigation calculations
 */
public class Navigator {
	public static final double EARTH_RADIUS = 6371.01;
	
	/**
	 * Calculate the distance in km between two points
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public double distance(double lat1, double lon1, double lat2, double lon2){
		double phi1 = Math.toRadians(lat1);
		double phi2 = Math.toRadians(lat2);
		double lam1 = Math.toRadians(lon1);
		double lam2 = Math.toRadians(lon2);

		return EARTH_RADIUS * Math.acos(Math.sin(phi1) * Math.sin(phi2) + Math.cos(phi1) * Math.cos(phi2) * Math.cos(lam2 - lam1));
	}
	
	/**
	 * Calculate the bearing to a point
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public double bearing(double lat1, double lon1, double lat2, double lon2){
		double brng = Math.atan2(Math.sin(lon2-lon1)*Math.cos(lat2),
								 Math.cos(lat1)*Math.sin(lat2)-Math.sin(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1));

		return Math.toDegrees(brng % (2.0 * Math.PI));
	}
}
