/*
 * Created on Dec 20, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.applications.databeans;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ItemAttributeDataBean {
	private String salesOrder;
	private String lineNumber;
	private double quantity;
	private double weight; //in pounds
	private double ouBasePrice;
	private double promoUnitDiscount;
	
	
	/**
	 * @return Returns the lineNumber.
	 */
	public String getLineNumber() {
		return lineNumber;
	}
	/**
	 * @param lineNumber The lineNumber to set.
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	/**
	 * @return Returns the salesOrder.
	 */
	public String getSalesOrder() {
		return salesOrder;
	}
	/**
	 * @param salesOrder The salesOrder to set.
	 */
	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}
	/**
	 * @return Returns the ouBasePrice.
	 */
	public double getOuBasePrice() {
		return ouBasePrice;
	}
	/**
	 * @param ouBasePrice The ouBasePrice to set.
	 */
	public void setOuBasePrice(double ouBasePrice) {
		this.ouBasePrice = ouBasePrice;
	}
	/**
	 * @return Returns the promoUnitDiscount.
	 */
	public double getPromoUnitDiscount() {
		return promoUnitDiscount;
	}
	/**
	 * @param promoUnitDiscount The promoUnitDiscount to set.
	 */
	public void setPromoUnitDiscount(double promoUnitDiscount) {
		this.promoUnitDiscount = promoUnitDiscount;
	}
	/**
	 * @return Returns the quantity.
	 */
	public double getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return Returns the weight.
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * @param weight The weight to set.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
}
