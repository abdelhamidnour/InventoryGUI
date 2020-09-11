package com.inventory.model;

public class Product {
	private String name;
	private String type;
	private String total;
	private String paid;
	private String remain;
	private String owner;
	private String worker;
	private String year;
	private String phoneNumber;
	private String carNum;
	private String note;
	private String image;
	private String carType;
	
	public Product(String name, String type, String total, String paid, String remain, String owner, String worker,
			String year, String phoneNumber, String carNum, String note, String image,String carType) {
		super();
		this.name = name;
		this.type = type;
		this.total = total;
		this.paid = paid;
		this.remain = remain;
		this.owner = owner;
		this.worker = worker;
		this.year = year;
		this.phoneNumber = phoneNumber;
		this.carNum = carNum;
		this.note = note;
		this.image = image;
		this.carType=carType;
	}
	
	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPaid() {
		return paid;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}
	public String getRemain() {
		return remain;
	}
	public void setRemain(String remain) {
		this.remain = remain;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", type=" + type + ", total=" + total + ", paid=" + paid + ", remain=" + remain
				+ ", owner=" + owner + ", worker=" + worker + ", year=" + year + ", phoneNumber=" + phoneNumber
				+ ", carNum=" + carNum + ", note=" + note + ", image=" + image + ", carType=" + carType + "]";
	}
	
	
	
	
}
