package com.matigakis.flightbot.controls;

/**
 * The Controls object holds the state of the aircraft's controls.
 */
public class Controls {
	protected double aileron;
	protected double elevator;
	protected double rudder;
	protected double throttle;
	
	/**
	 * Get the aileron state. 
	 * 
	 * 1.0 and -1.0 means full deflection where 0.0 means no deflection.  
	 * 
	 * @return aileron state
	 */
	public double getAileron(){
		return aileron;
	}
	
	/**
	 * Get the elevator state. 
	 * 
	 * 1.0 means full upward elevator and -1.0 means full downward elevator.
	 * 
	 * @return elevator state
	 */
	public double getElevator(){
		return elevator;
	}
	
	/**
	 * Get the rudder state. 
	 * 
	 * 1.0 means full rudder right and -1.0 means full rudder left.
	 * 
	 * @return rudder state
	 */
	public double getRudder(){
		return rudder;
	}
	
	/**
	 * Get the throttle state. 
	 * 
	 * 0.0 means throttle closed and 1.0 means full throttle.
	 * 
	 * @return
	 */
	public double getThrottle(){
		return throttle;
	}
	
	/**
	 * Set the aileron state
	 * 
	 * valid values are between -1.0 and 1.0;
	 * 
	 * @param aileron
	 */
	public void setAileron(double aileron){
		if (aileron > 1.0 || aileron < -1.0){
			throw new IllegalArgumentException("Aileron value was " + aileron);
		}
		
		this.aileron = aileron; 
	}
	
	/**
	 * Set elevator state.
	 * 
	 * valid values are between -1.0 and 1.0.
	 * 
	 * @param elevator
	 */
	public void setElevator(double elevator){
		if (elevator > 1.0 || elevator < -1.0){
			throw new IllegalArgumentException("Elevator was " + elevator);
		}
		
		this.elevator = elevator;
	}
	
	/**
	 * Set the rudder state. 
	 * 
	 * Valid values are between -1.0 and 1.0.
	 * 
	 * @param rudder
	 */
	public void setRudder(double rudder){
		if (rudder > 1.0 || rudder < -1.0){
			throw new IllegalArgumentException("Rudder was " + rudder);
		}
		
		this.rudder = rudder;
	}
	
	/**
	 * Set the throttle state
	 * 
	 * Valid values a re between 0.0 and 1.0.
	 * 
	 * @param throttle
	 */
	public void setThrottle(double throttle){
		if (throttle > 1.0 || throttle < 0.0){
			throw new IllegalArgumentException("Throttle was " + throttle);
		}
		
		this.throttle = throttle;
	}
}
