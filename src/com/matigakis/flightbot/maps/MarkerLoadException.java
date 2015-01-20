package com.matigakis.flightbot.maps;

public class MarkerLoadException extends Exception{
	private static final long serialVersionUID = 1L;

	public MarkerLoadException(){
		super();
	}
	
	public MarkerLoadException(String message){
		super(message);
	}
	
	public MarkerLoadException(String message, Throwable cause){
		super(message, cause);
	}
}
