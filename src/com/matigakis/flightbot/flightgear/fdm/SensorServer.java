package com.matigakis.flightbot.flightgear.fdm;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelFuture;

/**
 * The SensorServer object is used to receive telemetry data from Flightgear
 */
public class SensorServer extends Thread{
	private final int port;
	private EventLoopGroup group;
	private final SensorDataHandler sensorDataHandler;
	
	public SensorServer(int port){
		super();
		
		this.port = port;
		this.sensorDataHandler = new SensorDataHandler();
	}
	
	public void run(){
		//EventLoopGroup group = new NioEventLoopGroup();
		group = new NioEventLoopGroup();
		
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)
		.channel(NioDatagramChannel.class)
		.option(ChannelOption.SO_BROADCAST, true)
		.handler(sensorDataHandler);
		
		try{
			ChannelFuture channelFuture = bootstrap.bind(port).sync();
			
			channelFuture.channel().closeFuture().await();
		}catch(InterruptedException e){
			//TODO: Log the error 
		}
	}
	
	public void stopServer(){
		group.shutdownGracefully();
	}
	
	public void startServer(){
		start();
	}
	
	public void addSensorDataListener(SensorDataListener sensorDataListener){
		sensorDataHandler.addSensorDataListener(sensorDataListener);
	}
	
	public void removeSensorDataListener(SensorDataListener sensorDataListener){
		sensorDataHandler.removeSensorDataListener(sensorDataListener);
	}
}
