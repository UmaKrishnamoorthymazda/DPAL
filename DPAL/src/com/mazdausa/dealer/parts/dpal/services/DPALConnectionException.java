/*
 * Created on Dec 10, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.services;

/**
 * @author InterraIT
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DPALConnectionException extends Exception {
	
	public DPALConnectionException() {
		super();

	}

	/**
	 * @param message
	 */
	public DPALConnectionException(String message) {
		super(message);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public DPALConnectionException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param cause
	 */
	public DPALConnectionException(Throwable cause) {
		super(cause);

	}
}
