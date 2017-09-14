/*
 * Created on Dec 13, 2010
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
public class AwaitingConfirmationDataBean {
	String salesOrder;
	String lineNumber;
	String backOrderDate;
	PartsDataBean parts;
	DealerDataBean buyingDealer;
	
	/**
	 * @param salesOrder
	 * @param lineNumber
	 * @param backOrderDate
	 * @param parts
	 * @param buyingDealer
	 */
	public AwaitingConfirmationDataBean(String salesOrder, String lineNumber,
			String backOrderDate, PartsDataBean parts,
			DealerDataBean buyingDealer) {
		this.salesOrder = salesOrder;
		this.lineNumber = lineNumber;
		this.backOrderDate = backOrderDate;
		this.parts = parts;
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
	 * @return Returns the parts.
	 */
	public PartsDataBean getParts() {
		return parts;
	}
	/**
	 * @param parts The parts to set.
	 */
	public void setParts(PartsDataBean parts) {
		this.parts = parts;
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
}
