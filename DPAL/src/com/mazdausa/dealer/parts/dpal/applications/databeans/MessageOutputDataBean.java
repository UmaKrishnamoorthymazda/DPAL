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
public class MessageOutputDataBean {
	private String status; // 3 bytes
	private String sqlCode; // 4 bytes
	private String message; // 50 bytes
	
	// Begin: Added to fix Bug id: 2
	public boolean isSuccess() {
		return "".equals(status.trim());
	}
	// End changes
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
	 * @return Returns the sqlCode.
	 */
	public String getSqlCode() {
		return sqlCode;
	}
	/**
	 * @param sqlCode The sqlCode to set.
	 */
	public void setSqlCode(String sqlCode) {
		this.sqlCode = sqlCode;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
