package com.mazdausa.dealer.parts.dpal.applications.dto;

public class AwaitingConfirmationDTO {
	private String id;
	private String date;
	private long orderedQuantity;
	private long myInventory;
	private String partNumber;
	private String description;
	private long dealerCode;
	private String dealerName;
	private String city;
	private String state;
	private long zip;

	public AwaitingConfirmationDTO() {
	}

	public AwaitingConfirmationDTO(String id, String date,
			long orderedQuantity, long myInventory, String partNumber,
			String description, long dealerCode, String dealerName,
			String city, String state, long zip) {
		this.id = id;
		this.date = date;
		this.orderedQuantity = orderedQuantity;
		this.myInventory = myInventory;
		this.partNumber = partNumber;
		this.description = description;
		this.dealerCode = dealerCode;
		this.dealerName = dealerName;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public String getId() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(long orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public long getMyInventory() {
		return myInventory;
	}

	public void setMyInventory(long myInventory) {
		this.myInventory = myInventory;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(long dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getZip() {
		return zip;
	}

	public void setZip(long zip) {
		this.zip = zip;
	}

}
