package com.matigakis.flightbot.configuration;

public class ConfigurationFileException extends Exception{
	private static final long serialVersionUID = 1L;
	public final String filename;
	
	public ConfigurationFileException(String filename){
		super();
		
		this.filename = filename;
	}
	
	public ConfigurationFileException(String filename, Exception ex){
		super(ex);
		
		this.filename = filename;
	}
}
