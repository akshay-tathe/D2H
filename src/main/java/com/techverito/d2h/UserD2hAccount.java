package com.techverito.d2h;

/**
 * This is D2H user account POJO .it is hold all user details
 */
public class UserD2hAccount {
	
 
	private long balance;
	private String email;
	private long phoneNumber;
	private Subscription subscription;


	public UserD2hAccount() {
		super();
		this.subscription=new Subscription();
	}
    
	
	public Subscription getSubscription() {
		return subscription;
	}
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public long getBalance() {
		return this.balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
}
