package com.keylesson.ModelClasses;

public class boardreg {
	int room_ID;
	String room_Name;
	int region_ID;
	String region_Name;
	int location_ID;
	String location_Name;
	String mac;
	
	
	public String getLocation_Name() {
		return location_Name;
	}

	public void setLocation_Name(String location_Name) {
		this.location_Name = location_Name;
	}

	public String getRoom_Name() {
		return room_Name;
	}

	public void setRoom_Name(String room_Name) {
		this.room_Name = room_Name;
	}

	public int getLocation_ID() {
		return location_ID;
	}

	public void setLocation_ID(int location_ID) {
		this.location_ID = location_ID;
	}

	public String getRegion_Name() {
		return region_Name;
	}

	public void setRegion_Name(String region_Name) {
		this.region_Name = region_Name;
	}

	public int getRegion_ID() {
		return region_ID;
	}

	public void setRegion_ID(int region_ID) {
		this.region_ID = region_ID;
	}

	public int getRoom_ID() {
		return room_ID;
	}
	
	public void setRoom_ID(int room_ID) {
		this.room_ID = room_ID;
	}
	
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	

}