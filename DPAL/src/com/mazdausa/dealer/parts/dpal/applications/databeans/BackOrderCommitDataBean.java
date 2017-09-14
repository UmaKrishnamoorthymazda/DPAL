/*
 * Created on Dec 16, 2010
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
public class BackOrderCommitDataBean {
	private String salesOrder;
	private String lineNumber;
	private String message;
	private boolean result;
	
	
	/**
	 * @param salesOrder
	 * @param lineNumber
	 * @param message
	 * @param result
	 */
	public BackOrderCommitDataBean(String salesOrder, String lineNumber,
			String message, boolean result) {
		this.salesOrder = salesOrder;
		this.lineNumber = lineNumber;
		this.message = message;
		this.result = result;
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
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return Returns the result.
	 */
	public boolean isResult() {
		return result;
	}
	/**
	 * @param result The result to set.
	 */
	public void setResult(boolean result) {
		this.result = result;
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
