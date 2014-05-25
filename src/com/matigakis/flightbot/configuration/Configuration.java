package com.matigakis.flightbot.configuration;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.xerces.parsers.DOMParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;

/**
 * The Configuration class is responsible for the parsing of the configuration file.
 */
public class Configuration {
	private Document document;
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
	
	private Element getProtocolElement(){
		NodeList protocolElements = document.getElementsByTagName("protocol");
		Element protocolElement = (Element) protocolElements.item(0);
		
		return protocolElement;
	}
	
	/**
	 * Load a configuration file.
	 * 
	 * @param filename the file to load
	 * @throws Exception
	 */
	public void loadConfiguration(String filename) throws ConfigurationFileException{
		DOMParser parser = new DOMParser();
		
		try{
			parser.parse(filename);
		}catch(SAXException|IOException ex){
			logger.error("Failed to load configuration from " + filename, ex);
			throw new ConfigurationFileException(filename, ex);
		}
		
		document = parser.getDocument();
	}
	
	/**
	 * Get the address of the Flightgear process.
	 * 
	 * @return the address that Flightgear listens to 
	 */
	public String getHost(){
		Element protocolElement = getProtocolElement();
		
		NodeList hostElements = protocolElement.getElementsByTagName("host");
		Element hostElement = (Element) hostElements.item(0);
		
		String host = hostElement.getTextContent();
		
		return host;
	}
	
	/**
	 * Get the telemetry port
	 * 
	 * @return telemetry port
	 */
	public int getSensorPort(){
		Element protocolElement = getProtocolElement();
		
		NodeList sensorElements = protocolElement.getElementsByTagName("sensors");
		Element sensorsElement = (Element) sensorElements.item(0);
	
		int sensorsPort = Integer.parseInt(sensorsElement.getTextContent());
		
		return sensorsPort;
	}
	
	/**
	 * Get the controls port
	 * 
	 * @return controls port
	 */
	public int getControlsPort(){
		Element protocolElement = getProtocolElement();
		
		NodeList controlsElements = protocolElement.getElementsByTagName("controls");
		Element controlsElement = (Element) controlsElements.item(0);
		
		int controlsPort = Integer.parseInt(controlsElement.getTextContent());
	
		return controlsPort;
	}
}
