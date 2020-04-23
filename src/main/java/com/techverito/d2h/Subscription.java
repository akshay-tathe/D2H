package com.techverito.d2h;

import java.util.HashMap;

/**
 * This is subscription POJO class it hold pack ,channel and service details.
 */
public class Subscription {

	public Subscription() {
		super();
		this.pack = new HashMap<>();
		this.channel = new HashMap<>();
		this.service = new HashMap<>();
	}
	
	public Subscription(HashMap<String, Integer> pack, HashMap<String, Integer> channel,
			HashMap<String, Integer> service) {
		super();
		this.pack = pack;
		this.channel = channel;
		this.service = service;
		
	}

	private HashMap<String,Integer> pack;
	private HashMap<String,Integer> channel;
	private HashMap<String,Integer> service;
	private int packMonths ;
	private int channelMonths ;
	private int serviceMonths ;
	public HashMap<String, Integer> getPack() {
		return pack;
	}
	public void setPack(HashMap<String, Integer> pack) {
		this.pack = pack;
	}
	public HashMap<String, Integer> getChannel() {
		return channel;
	}
	public void setChannel(HashMap<String, Integer> channel) {
		this.channel = channel;
	}
	public HashMap<String, Integer> getService() {
		return service;
	}
	public void setService(HashMap<String, Integer> service) {
		this.service = service;
	}
	public int getPackMonths() {
		return packMonths;
	}
	public void setPackMonths(int packMonths) {
		this.packMonths = packMonths;
	}
	public int getChannelMonths() {
		return channelMonths;
	}
	public void setChannelMonths(int channelMonths) {
		this.channelMonths = channelMonths;
	}
	public int getServiceMonths() {
		return serviceMonths;
	}
	public void setServiceMonths(int serviceMonths) {
		this.serviceMonths = serviceMonths;
	}
	
	
	
}
