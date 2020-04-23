package com.techverito.d2h;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This class use to set constant value or metadata
 */
class ConstantValue {

	public static final List<String> MENU_LIST = Arrays.asList("View current balance in the account",
			"Recharge Account", "View available packs, channels and services", "Subscribe to base packs",
			"Add channels to an existing subscription", "Subscribe to special services",
			"View current subscription details", "Update email and phone number for notifications", "Exit");

	public static Subscription SUBSCRIPTION() {
		HashMap<String, Integer> pack = new HashMap<>();
		HashMap<String, Integer> channel = new HashMap<>();
		HashMap<String, Integer> service = new HashMap<>();
		pack.put("Silver pack: Zee, Sony, Star Plus", 50);
		pack.put("Gold Pack: Zee, Sony, Star Plus, Discovery, NatGeo", 100);

		channel.put("Zee", 10);
		channel.put("Sony", 15);
		channel.put("Star Plus", 20);
		channel.put("Discovery", 10);
		channel.put("NatGeo", 20);
		
		service.put("LearnCooking", 100 );
		service.put("LearnEnglish", 200 );
		
		return new Subscription(pack,channel,service);
	}
}