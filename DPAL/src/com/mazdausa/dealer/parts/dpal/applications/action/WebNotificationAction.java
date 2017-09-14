/*
 * Created on Jan 26, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.applications.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.mazdausa.dealer.parts.dpal.services.NotificationException;
import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.dealer.parts.dpal.application.notification.FileBasedNotification;
import com.mazdausa.dealer.parts.dpal.application.notification.Notification;
import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WebNotificationAction extends DPALActionAbstract {
	private static Logger log = EMDCSLogger.getLogger(WebNotificationAction.class);
	String dealerCode;
	String nextCheck;
	
	private static final String KEY_NOTIFICATION_PRESENT = "notification.present";
	private static final String KEY_NOTIFICATION_ABSENT = "notification.absent";
	private static final String KEY_NOT_AUTHORIZED = "notification.not.authorized";
	
	private static final int NOT_AUTHORIZED = -1;
	private static final int NOTIFICATION_PRESENT = 1;
	private static final int NOTIFICATION_ABSENT = 2;
	
	private static final String timeoutOnError = "120000"; // 5 mins
	
	//private static final String 
	/* (non-Javadoc)
	 * @see com.mazdausa.common.application.actions.EmazdaActionAbstract#executeAction(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String responseMessage = null;
		
		// check if user is a dealer
		if(!WebUtils.isDealer(req, this.getUserContext(req))) {
			this.nextCheck = "-1";
			writeResponse(res, getResponseMessage(req, false, NOT_AUTHORIZED));
			return null;
		}
		
		// get the dealer code
		this.dealerCode = WebUtils.getDealerCode(req, this.getUserContext(req));
		
		responseMessage = getResponseMessage(req, isNotificationPresent());
		
		writeResponse(res, responseMessage);
		
		return null;
	}
	
	private boolean isNotificationPresent() {
		// check MQ to see if a notification is present
		boolean notificationStatus = false;
		Notification notification = new FileBasedNotification();
		try {
			notificationStatus = notification.isNotificationAvailable(dealerCode);
			this.nextCheck = Long.toString(notification.getDelayInNextExecution());
		} catch(NotificationException e) {
			log.error("Unable to read notification now. Will try again in 5 minutes");
			notificationStatus = false;
			this.nextCheck = timeoutOnError;
		}
		// initialize the next check time (in milliseconds)
		
		
		// return true if notification is present
		return notificationStatus;
	}
	
	private String getResponseMessage(HttpServletRequest req, boolean status, int messageType) {
		StringBuffer jsonData = new StringBuffer();
		
		String message = getResourceMessage(req, messageType);
		
		jsonData.append("{\"showNotification\": \"" + status + "\",")
		.append("\"nextCheck\": \"" + this.nextCheck + "\",")
		.append("\"message\": \"" + message	+ "\"")
		.append("}");
		
		return jsonData.toString();
	}
	
	private String getResponseMessage(HttpServletRequest req, boolean status) {
		//return status? getResourceMessage(req, NOTIFICATION_PRESENT): getResourceMessage(req, NOTIFICATION_ABSENT);
		
		return  getResponseMessage(req, status, status? NOTIFICATION_PRESENT: NOTIFICATION_ABSENT);
	}
	
	private void writeResponse(HttpServletResponse res, String message) throws Exception {
		log.debug("Notification message: " + message);
		PrintWriter pw = res.getWriter();
		res.setContentType("application/json");
		res.setContentLength(message.length());
		pw.write(message);
	}
	
	private String getResourceMessage(HttpServletRequest req, int messageType) {
		String message = null;
		MessageResources resource = this.getResources(req);
		
		switch(messageType) {
			case NOT_AUTHORIZED: 
				message = resource.getMessage(KEY_NOT_AUTHORIZED);
				break;
			case NOTIFICATION_PRESENT:
				message = resource.getMessage(KEY_NOTIFICATION_PRESENT);
				break;
			case NOTIFICATION_ABSENT:
				message = resource.getMessage(KEY_NOTIFICATION_ABSENT);
				break;
			default:
				message = null;
				break;
		}
		log.debug("WebNotificationAction : executeAction : responseMessage = " + message);
		return message;
	}
}
