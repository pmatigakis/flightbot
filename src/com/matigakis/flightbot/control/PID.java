package com.matigakis.flightbot.control;

/**
 * 
 * Implementation of a PID controller
 */
public class PID {
	private double kp;
	private double ki;
	private double kd;
	
	private double dt;
	
	private double maxIntegral;
	private double integral;
	
	private double last_error;
	
	public PID(double kp, double ki, double kd, double dt, double maxIntegral){
		this.kp = kp;
		this.ki = ki;
		this.kd = kd;
		
		this.dt = dt;
		
		this.maxIntegral = maxIntegral;
		
		last_error = 0.0;
		integral = 0.0;
	}
	
	/**
	 * calculate the PID controller output
	 * 
	 * @param error the current error
	 * @return PID controller output
	 */
	public double update(double error){
		integral += error * dt;
		
		if(integral > maxIntegral){
			integral = maxIntegral;
		}else if(integral < -maxIntegral){
			integral = -maxIntegral;
		}
		
		double derivative = (error - last_error) / dt;
		
		double output = kp * error + ki * integral + kd * derivative;
		
		last_error = error;
		
		return output;
	}
}
