/*
 * Created on Feb 2, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.applications.databeans;

import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BOCancelMQMessageDataBean {
	public static final String TRANSACTION_ID = "P243";
	//String dealerCode;
	int sequenceNumber;
	String salesOrderNumber;
	String salesOrderLineNumber;
	String partNumber;
	String quantity;
	
	
	
	/**
	 * @return Returns the dealerCode.
	 */
	/*public String getDealerCode() {
		return WebUtils.padString(dealerCode, 5);
	}*/
	/**
	 * @param dealerCode The dealerCode to set.
	 */
	/*public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}*/
	/**
	 * @return Returns the partNumber.
	 */
	public String getPartNumber() {
		return WebUtils.padString(partNumber, 20);
	}
	/**
	 * @param partNumber The partNumber to set.
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	/**
	 * @return Returns the quantity.
	 */
	public String getQuantity() {
		return WebUtils.padNumber(quantity, 5, true);
	}
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return Returns the salesOrderLineNumber.
	 */
	public String getSalesOrderLineNumber() {
		return WebUtils.padNumber(salesOrderLineNumber, 4, false);
	}
	/**
	 * @param salesOrderLineNumber The salesOrderLineNumber to set.
	 */
	public void setSalesOrderLineNumber(String salesOrderLineNumber) {
		this.salesOrderLineNumber = salesOrderLineNumber;
	}
	/**
	 * @return Returns the salesOrderNumber.
	 */
	public String getSalesOrderNumber() {
		return WebUtils.padString(salesOrderNumber, 6);
	}
	/**
	 * @param salesOrderNumber The salesOrderNumber to set.
	 */
	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}
	/**
	 * @return Returns the sequenceNumber.
	 */
	public String getSequenceNumber() {
		return WebUtils.padNumber(Integer.toString(sequenceNumber), 4, false);
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb/*.append(this.transactionId)
			.append(this.getDealerCode())*/
			.append(this.getSequenceNumber())
			.append(this.getSalesOrderNumber())
			.append(this.getSalesOrderLineNumber())
			.append(this.getPartNumber())
			.append(this.getQuantity());
		
		return sb.toString();
	}
}
