package com.matigakis.flightbot.network;

import java.util.LinkedList;
import java.util.List;

import com.matigakis.flightbot.network.sensors.SensorDataFactory;
import com.matigakis.flightbot.sensors.SensorData;

import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SensorDataHandler object processes the data received from Flightgear
 */
public class SensorDataHandler extends SimpleChannelInboundHandler<DatagramPacket> implements SensorDataReceiver{
	private static final Logger logger = LoggerFactory.getLogger(SensorDataHandler.class);
	private final List<SensorDataListener> listeners;
	private final SensorDataFactory sensorDataFactory;
	
	public SensorDataHandler(){
		super();
		
		listeners = new LinkedList<SensorDataListener>();
		
		sensorDataFactory = new SensorDataFactory();
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext arg0, DatagramPacket msg){
		//System.out.println(msg.content().toString(CharsetUtil.US_ASCII));
		String data = msg.content().toString(CharsetUtil.US_ASCII);
		
		SensorData sensorData = sensorDataFactory.fromString(data);
		
		notifyListeners(sensorData);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		logger.error("Exception raised in SensorDataHandler", cause);
		ctx.close();
	}

	@Override
	public void addSensorDataListener(SensorDataListener sensorDataListener) {
		listeners.add(sensorDataListener);
	}

	@Override
	public void removeSensorDataListener(SensorDataListener sensorDataListener) {
		listeners.remove(sensorDataListener);
	}

	@Override
	public void notifyListeners(SensorData sensorData) {
		for(SensorDataListener sensorDataListener: listeners){
			sensorDataListener.handleSensorData(sensorData);
		}
	}
}
