package com.keylesson.ModelClasses;

public class event {

	private int power;
	private int water;
	private String message;
	private String email;
	private int room_ID;
	private int ID;
	private int over_usage_power;
	private int over_usage_water;

	public int getID() {
		return ID;
	}
	public void setID(int id) {
		this.ID = id;
	}
	public int getOver_usage_power() {
		return over_usage_power;
	}
	public void setOver_usage_power(int over_usage_power) {
		this.over_usage_power = over_usage_power;
	}
	public int getOver_usage_water() {
		return over_usage_water;
	}
	public void setOver_usage_water(int over_usage_water) {
		this.over_usage_water = over_usage_water;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getWater() {
		return water;
	}
	public void setWater(int water) {
		this.water = water;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getRoom() {
		return room_ID;
	}
	public void setRoom(int room_ID) {
		this.room_ID = room_ID;
	}

	
	
	
	
}
