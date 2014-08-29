package com.matigakis.flightbot.ui.controllers;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.text.View;

import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matigakis.flightbot.aircraft.Aircraft;
import com.matigakis.flightbot.aircraft.Instrumentation;
import com.matigakis.flightbot.aircraft.Orientation;
import com.matigakis.flightbot.aircraft.sensors.Accelerometer;
import com.matigakis.flightbot.aircraft.sensors.GPS;
import com.matigakis.flightbot.aircraft.sensors.Gyroscope;
import com.matigakis.flightbot.aircraft.sensors.Magnetometer;
import com.matigakis.flightbot.aircraft.sensors.PitotTube;
import com.matigakis.flightbot.aircraft.sensors.StaticPressureSensor;
import com.matigakis.flightbot.aircraft.sensors.TemperatureSensor;
import com.matigakis.fgcontrol.controls.Controls;
import com.matigakis.fgcontrol.sensors.SensorData;
import com.matigakis.fgcontrol.SensorDataListener;
import com.matigakis.flightbot.ui.views.TelemetryView;
import com.matigakis.flightbot.ui.views.TelemetryWindow;

/**
 * The TelemetryViewController is responsible for the rendering of sensor data
 * to the telemetry view.
 */
public class TelemetryWindowController implements TelemetryViewController{
	private static final Logger logger = LoggerFactory.getLogger(TelemetryWindowController.class);
	private final TelemetryWindow telemetryView;
	private final Aircraft aircraft;
	private List<MapMarker> markers;
	
	public TelemetryWindowController(TelemetryWindow telemetryView){	
		this.telemetryView = telemetryView;
		aircraft = new Aircraft();
		markers = new LinkedList<MapMarker>();
	}

	/**
	 * Close the view
	 */
	@Override
	public void close() {
		//telemetryView.dispatchEvent(new WindowEvent(telemetryView, WindowEvent.WINDOW_CLOSING));
		telemetryView.close();
	}

	/**
	 * Get the state of the autopilot
	 * 
	 * @return The state of the autopilot
	 */
	public boolean getAutopilotState() {
		return aircraft.isAutopilotActive();
	}

	@Override
	public void updateAircraft(SensorData sensorData) {
		aircraft.updateFromSensorData(sensorData);
	}

	@Override
	public void updateView() {
		telemetryView.updateView(aircraft);
	}

	@Override
	public void addMarkers() {
		JFileChooser fileChooser = new JFileChooser();
		
		int selection = fileChooser.showOpenDialog(telemetryView);
		
		if(selection == fileChooser.APPROVE_OPTION){
			try {
				FileReader fr = new FileReader(fileChooser.getSelectedFile());
				
				BufferedReader br = new BufferedReader(fr);
				
				while(true){
					String line = br.readLine();
					
					if(line != null){
						String[] data = line.split(",");
						if(data.length == 2){
							MapMarkerDot marker = new MapMarkerDot(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
							marker.setVisible(true);
							marker.setColor(Color.blue);
							marker.setBackColor(Color.blue);
							markers.add(marker);
							telemetryView.addMarker(marker);
						}else{
							logger.warn("Invalid marker data");
						}
					}else{
						break;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void clearmarkers() {
		for(MapMarker mapMarker: markers){
			telemetryView.removeMarker(mapMarker);
		}
		
		markers.clear();
	}

	@Override
	public void updateAircraft(Aircraft aircraft) {
		Accelerometer acc1 = this.aircraft.getAccelerometer();
		Accelerometer acc2 = aircraft.getAccelerometer();
		
		acc1.setXAcceleration(acc2.getXAcceleration());
		acc1.setYAcceleration(acc2.getYAcceleration());
		acc1.setZAcceleration(acc2.getZAcceleration());
		
		Gyroscope gyro1 = this.aircraft.getGyroscope();
		Gyroscope gyro2 = aircraft.getGyroscope();
		
		gyro1.setXRotation(gyro2.getXRotation());
		gyro1.setYRotation(gyro2.getYRotation());
		gyro1.setZRotation(gyro2.getZRotation());
		
		Magnetometer magn1 = this.aircraft.getMagnetometer();
		Magnetometer magn2 = aircraft.getMagnetometer();
		
		magn1.setXAxis(magn2.getXAxis());
		magn1.setYAxis(magn2.getYAxis());
		magn1.setZAxis(magn2.getZAxis());
		
		GPS gps1 = this.aircraft.getGPS();
		GPS gps2 = aircraft.getGPS();
		
		gps1.setAirspeed(gps2.getAirspeed());
		gps1.setAltitude(gps2.getAltitude());
		gps1.setHeading(gps2.getHeading());
		gps1.setLatitude(gps2.getLatitude());
		gps1.setLongitude(gps2.getLongitude());
		
		StaticPressureSensor sps1 = this.aircraft.getStaticPressureSensor();
		StaticPressureSensor sps2 = aircraft.getStaticPressureSensor();
		
		sps1.setPressure(sps2.getPressure());
		
		PitotTube pt1 = this.aircraft.getPitotTube();
		PitotTube pt2 = aircraft.getPitotTube();
		
		pt1.setPressure(pt2.getPressure());
		
		TemperatureSensor tmp1 = this.aircraft.getTemperatureSensor();
		TemperatureSensor tmp2 = aircraft.getTemperatureSensor();
		
		tmp1.setTemperature(tmp2.getTemperature());
		
		Orientation or1 = this.aircraft.getOrientation();
		Orientation or2 = aircraft.getOrientation();
		
		or1.setPitch(or2.getPitch());
		or1.setRoll(or2.getRoll());
		
		Instrumentation inst1 = this.aircraft.getInstrumentation();
		Instrumentation inst2 = aircraft.getInstrumentation();
		
		inst1.setAirspeed(inst2.getAirspeed());
		inst1.setAltitude(inst2.getAltitude());
		inst1.setHeading(inst2.getHeading());
		
		Controls ctrl1 = this.aircraft.getControls();
		Controls ctrl2 = aircraft.getControls();
		
		ctrl1.setAileron(ctrl2.getAileron());
		ctrl1.setElevator(ctrl2.getElevator());
		ctrl1.setRudder(ctrl2.getRudder());
		ctrl1.setThrottle(ctrl2.getThrottle());
	}

	@Override
	public void setAutopilotState(boolean state) {
		aircraft.setAutopilotState(state);
	}
}
