/**
 * This is the ScoreAdminAction class <B>Creation Date: </B> May 10, 2009
 * <BR>
 * <BR>
 * 
 * @author InterraIT
 * @version 1.0 <BR>
 *          <BR>
 *          <B>Patterns Used: </B> <BR>
 *          <BR>
 *          Copyright 2002 by Mazda North America Operations, Inc., 7755 Irvine
 *          Center Drive Irvine, CA 92623, U.S.A. All rights reserved. <BR>
 *          <BR>
 *          This software is the confidential and proprietary information of
 *          Mazda North America Operations Inc. ("Confidential Information").
 *          You shall not disclose such Confidential Information and shall use
 *          it only in accordance with the terms of the license agreement you
 *          entered into with Mazda North American Operations.
 */
package com.mazdausa.dealer.parts.dpal.applications.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mazdausa.common.log.EMDCSLogger;

/**
 * @author InterraIT
 * 
 */
public class HomeAction extends DPALActionAbstract {
	private static Logger log = EMDCSLogger.getLogger(HomeAction.class);
	/**
	 * * This method handles verifies the user authentication and displays the
	 * menu screen
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward
	 * @throws Exception
	 * @see com.mazdausa.common.application.actions.EmazdaActionAbstract#executeAction(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	 protected ActionForward executeAction (
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {

		log.debug("HomeAction : executeAction : execute called");
		
		String action = req.getParameter("aAction");
		String forwardPage = "home";
		if(action == null)
			forwardPage = "home";
		else if(action.equals("menu"))
			forwardPage = "menu";
		else if(action.equals("notification"))
			forwardPage = "notification";
			
		//return mapping.findForward("menu");
		return mapping.findForward(forwardPage);
	}
}
