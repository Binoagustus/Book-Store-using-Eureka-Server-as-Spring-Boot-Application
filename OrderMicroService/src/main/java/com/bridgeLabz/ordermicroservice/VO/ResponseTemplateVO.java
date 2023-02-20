package com.bridgeLabz.ordermicroservice.VO;

import java.util.List;

public class ResponseTemplateVO {

	private int orderId;
	private List<UserBookAndQuantityVO> viewBooks;
	private String name;
	private String address;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public List<UserBookAndQuantityVO> getViewBooks() {
		return viewBooks;
	}

	public void setViewBooks(List<UserBookAndQuantityVO> viewBooks) {
		this.viewBooks = viewBooks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "RestTemplateVO [orderId=" + orderId + ", viewBooks=" + viewBooks + ", name=" + name + ", address="
				+ address + "]";
	}	
}
