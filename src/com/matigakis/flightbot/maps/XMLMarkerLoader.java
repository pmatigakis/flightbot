package com.matigakis.flightbot.maps;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

/**
 * The XMLMarkerLoader class is used to load map markers from an XML file.
 * 
 * See the autopilots/simple/waypoints.xml as a sample file.
 */
public class XMLMarkerLoader implements MarkerLoader{
	private List<MapMarker> markers;
	
	public XMLMarkerLoader(){
		markers = new LinkedList<MapMarker>();
	}
	
	public List<MapMarker> getMarkers(){
		return markers;
	}
	
	private void parse_markers_node(Node markers){
		NodeList markerNodes = markers.getChildNodes();
		
		for(int i = 0; i < markerNodes.getLength(); i++){
			Node markerNode = markerNodes.item(i);
			
			if(markerNode.getNodeName().toLowerCase() == "dot"){
				parse_dot_node(markerNode);
			}
		}
	}
	
	private void parse_dot_node(Node marker){
		double latitude = 0.0;
		double longitude = 0.0;
		
		NodeList markerArguments = marker.getChildNodes();
		
		for(int i = 0; i < markerArguments.getLength(); i++){
			Node markerArgument = markerArguments.item(i);
			
			if(markerArgument.getNodeName().toLowerCase() == "latitude"){
				latitude = Double.parseDouble(markerArgument.getTextContent());
			}else if(markerArgument.getNodeName().toLowerCase() == "longitude"){
				longitude = Double.parseDouble(markerArgument.getTextContent());
			}
		}
		
		MapMarkerDot dotMarker = new MapMarkerDot(latitude, longitude);
		dotMarker.setVisible(true);
		dotMarker.setColor(Color.blue);
		dotMarker.setBackColor(Color.blue);
		
		markers.add(dotMarker);
	}
	
	public void loadMarkers(String filename) throws IOException, MarkerLoadException{
		DOMParser parser = new DOMParser();
		
		try {
			parser.parse(filename);
		} catch (SAXException e) {
			throw new MarkerLoadException("Failed to load markers from XML", e);
		} 
		
		Document document = parser.getDocument();
		
		NodeList markers = document.getElementsByTagName("markers");
		
		for(int i = 0; i < markers.getLength(); i++){
			Node markersNode = markers.item(i);
			
			if(markersNode.getNodeName().toLowerCase() == "markers"){
				parse_markers_node(markersNode);
			}
		}
	}
}
