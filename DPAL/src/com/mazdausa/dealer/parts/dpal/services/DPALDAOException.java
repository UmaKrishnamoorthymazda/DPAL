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
public class DPALDAOException extends Exception {
	public DPALDAOException() {
		super();
	}

	/**
	 * @param message
	 */
	public DPALDAOException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DPALDAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public DPALDAOException(Throwable cause) {
		super(cause);
	}
}
