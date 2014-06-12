package com.matigakis.flightbot.network;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import com.matigakis.flightbot.controls.Controls;

public class ControlsClient {
	private static final Logger logger = LoggerFactory.getLogger(ControlsClient.class);
	private final InetSocketAddress address;
	private EventLoopGroup group;
	private Channel channel;
	
	public ControlsClient(String host, int port){
		address = new InetSocketAddress(host, port);
	}
	
	public void openConnection() throws InterruptedException{
		logger.info("Opening connection to server");
		
		group = new NioEventLoopGroup();
		
		Bootstrap bootstrap = new Bootstrap();
		
		bootstrap.group(group)
		.channel(NioDatagramChannel.class)
		.option(ChannelOption.SO_BROADCAST, true)
		.handler(new ControlsHandler()); //TODO: Check if this is required for transmition only operation
		
		channel = bootstrap.bind(0).sync().channel();
	}
	
	public void closeConnection(){
		logger.info("Closing connection to server");
		
		group.shutdownGracefully();
	}
	
	public void transmitControls(Controls controls){
		String controlsString = controls.getElevator() + "\t" + controls.getAileron() + "\t" + controls.getRudder() + "\t" + controls.getThrottle() + "\n";
		
		DatagramPacket packet = new DatagramPacket(Unpooled.copiedBuffer(controlsString, CharsetUtil.US_ASCII), address);
		
		channel.writeAndFlush(packet);
	}
}
