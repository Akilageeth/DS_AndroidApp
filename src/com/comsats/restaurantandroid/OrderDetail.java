package com.comsats.restaurantandroid;

public class OrderDetail {
	
	public static int tableNo;
	public static String specialInstructions;
	public static int countTime; 
	
	public static int getTableNo() {
		return tableNo;
	}
	public static void setTableNo(int tableNo) {
		OrderDetail.tableNo = tableNo;
	}
	public static String getSpecialInstructions() {
		return specialInstructions;
	}
	public static void setSpecialInstructions(String specialInstructions) {
		OrderDetail.specialInstructions = specialInstructions;
	}
	public static int getCountTime() {
		return countTime;
	}
	public static void setCountTime(int countTime) {
		OrderDetail.countTime = countTime;
	}
	
	

}
