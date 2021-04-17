package com.comsats.restaurantandroid;

import java.util.ArrayList;

public class Ad {

	private Integer adId;
	private String adName;
	private String adDescription;
	private String bannerName;
	private String status;
	public static ArrayList<Ad> list = new ArrayList<Ad>();


	public Ad(){
		
	}
	
	public static ArrayList<Ad> getList() {
		return list;
	}

	public static void setList(ArrayList<Ad> list) {
		Ad.list = list;
	}



	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getAdId() {
		return adId;
	}
	public void setAdId(Integer adId) {
		this.adId = adId;
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getAdDescription() {
		return adDescription;
	}
	public void setAdDescription(String adDescription) {
		this.adDescription = adDescription;
	}
	public String getBannerName() {
		return bannerName;
	}
	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}
	
	
}
