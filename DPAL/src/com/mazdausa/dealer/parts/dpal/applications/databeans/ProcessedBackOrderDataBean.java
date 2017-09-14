package com.mazdausa.dealer.parts.dpal.applications.databeans;

public class ProcessedBackOrderDataBean {
	String salesOrder;
	String lineNumber;
	String backOrderDate;
	String itemNumber;
	String itemDescription;
	String shippedQuantity;
	String shippingMethod;
	String trackingNumber;
	DealerDataBean buyingDealer;
	
	/**
	 * @param salesOrder
	 * @param lineNumber
	 * @param backOrderDate
	 * @param parts
	 * @param buyingDealer
	 */
	public ProcessedBackOrderDataBean(String salesOrder, String lineNumber,
			String backOrderDate, String itemNumber, String itemDescription,
			String shippedQuantity, String shippingMethod, 
			String trackingNumber, DealerDataBean buyingDealer) {
		this.salesOrder = salesOrder;
		this.lineNumber = lineNumber;
		this.backOrderDate = backOrderDate;
		this.itemNumber = itemNumber;
		this.itemDescription = itemDescription;
		this.shippedQuantity = shippedQuantity;
		this.shippingMethod = shippingMethod;
		this.trackingNumber = trackingNumber;
		this.buyingDealer = buyingDealer;
	}
	/**
	 * @return Returns the backOrderDate.
	 */
	public String getBackOrderDate() {
		return backOrderDate;
	}
	/**
	 * @param backOrderDate The backOrderDate to set.
	 */
	public void setBackOrderDate(String backOrderDate) {
		this.backOrderDate = backOrderDate;
	}
	/**
	 * @return Returns the buyingDealer.
	 */
	public DealerDataBean getBuyingDealer() {
		return buyingDealer;
	}
	/**
	 * @param buyingDealer The buyingDealer to set.
	 */
	public void setBuyingDealer(DealerDataBean buyingDealer) {
		this.buyingDealer = buyingDealer;
	}
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
	 * @return the itemNumber
	 */
	public String getItemNumber() {
		return itemNumber;
	}
	/**
	 * @param itemNumber the itemNumber to set
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	/**
	 * @return the itemDescription
	 */
	public String getItemDescription() {
		return itemDescription;
	}
	/**
	 * @param itemDescription the itemDescription to set
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	/**
	 * @return the shippedQuantity
	 */
	public String getShippedQuantity() {
		// Begin: Added to fix Bug id: 35
		if(shippedQuantity != null) {
			double d = Double.parseDouble(shippedQuantity);
			int n = (int)d;
			shippedQuantity = Integer.toString(n);
		}
		// End changes
		return shippedQuantity;
	}
	/**
	 * @param shippedQuantity the shippedQuantity to set
	 */
	public void setShippedQuantity(String shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}
	/**
	 * @return the shippingMethod
	 */
	public String getShippingMethod() {
		return shippingMethod;
	}
	/**
	 * @param shippingMethod the shippingMethod to set
	 */
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	/**
	 * @return the trackingNumber
	 */
	public String getTrackingNumber() {
		return trackingNumber;
	}
	/**
	 * @param trackingNumber the trackingNumber to set
	 */
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
}
