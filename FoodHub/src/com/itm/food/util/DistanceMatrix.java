package com.itm.food.util;

public class DistanceMatrix {

	private String orginLat;
	private String orginLng;
	private String destLat;
	private String destLng;
	private String miles;
	private String duration;

	public String getOrginLat() {
		return orginLat;
	}

	public void setOrginLat(String orginLat) {
		this.orginLat = orginLat;
	}

	public String getOrginLng() {
		return orginLng;
	}

	public void setOrginLng(String orginLng) {
		this.orginLng = orginLng;
	}

	public String getDestLat() {
		return destLat;
	}

	public void setDestLat(String destLat) {
		this.destLat = destLat;
	}

	public String getDestLng() {
		return destLng;
	}

	public void setDestLng(String destLng) {
		this.destLng = destLng;
	}

	public String getMiles() {
		return miles;
	}

	public void setMiles(String miles) {
		this.miles = miles;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	@Override
	public String toString(){
		return "["+ destLat + ", " + destLng +  " ==> " + miles + ", " + duration + "]";
		
	}

}
