/*
 * Created on Dec 10, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.applications.databeans;

/**
 * @author InterraIT
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PartsDataBean {
	private String number;
	private String description;
	private String orderedQuantity;
	private String myInventory;
	private String shippingQuantity;
	private String date;
	
	/**
	 * @param number
	 * @param description
	 * @param orderedQuantity
	 * @param myInventory
	 */
	public PartsDataBean(String number, String description,
			String orderedQuantity, String myInventory) {
		super();
		this.number = number;
		this.description = description;
		this.orderedQuantity = orderedQuantity.substring(0, orderedQuantity.indexOf("."));
		this.myInventory = myInventory.substring(0, myInventory.indexOf("."));
	}
	
	public PartsDataBean(String number, String description, String orderedQuantity, 
			String myInventory, String shippingQuantity, String date) {
		this.number = number;
		this.description = description;
		this.orderedQuantity = orderedQuantity;
		this.myInventory = myInventory;
		this.shippingQuantity = shippingQuantity;
		this.date = date;
	}
	
	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return Returns the shippingQuantity.
	 */
	public String getShippingQuantity() {
		return shippingQuantity;
	}
	/**
	 * @param shippingQuantity The shippingQuantity to set.
	 */
	public void setShippingQuantity(String shippingQuantity) {
		this.shippingQuantity = shippingQuantity;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the myInventory.
	 */
	public String getMyInventory() {
		return myInventory;
	}
	/**
	 * @param myInventory The myInventory to set.
	 */
	public void setMyInventory(String myInventory) {
		this.myInventory = myInventory;
	}
	/**
	 * @return Returns the number.
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number The number to set.
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return Returns the orderedQuantity.
	 */
	public String getOrderedQuantity() {
		return orderedQuantity;
	}
	/**
	 * @param orderedQuantity The orderedQuantity to set.
	 */
	public void setOrderedQuantity(String orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}
}
