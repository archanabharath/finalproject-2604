package com.itm.food.dao;

public enum OrderStatus {

	IN_PROGRESS(1), OUT_FOR_DELIVERY(2), READY_FOR_PICKUP(3), COMPLETED(4);

	private int id;

	OrderStatus(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
