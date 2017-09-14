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

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;
import com.mazdausa.dealer.parts.dpal.persistence.DealerDetailDAO;
import com.mazdausa.dealer.parts.dpal.persistence.DealerDetailDAOImpl;
import com.mazdausa.dealer.parts.dpal.persistence.DpalDAOFactory;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailDTO;
import com.mazdausa.dealer.parts.dpal.services.BackOrderListCommand;

/**
 * @author InterraIT
 *
 */
public class DealerDetailsAction extends DPALActionAbstract {

	private static Logger log = EMDCSLogger.getLogger(DealerDetailsAction.class);
	/**
	 * * This method handles verifies the user authentication and displays the menu screen
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
	protected ActionForward executeAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String countryCode = WebUtils.getCountryCode(req) ;
		String dealerCode = req.getParameter("dealerCode");

		DealerDetailDAO dao = null;
		DealerDetailDTO dto = null;
		try {
			DpalDAOFactory factory = new DpalDAOFactory(BackOrderListCommand.class.getName());
			factory.init("DPAL_FACTORY");
			dao = (DealerDetailDAO)factory.lookupDAO(DealerDetailDAO.class);

			dto = dao.findDTO(countryCode, dealerCode, DealerDetailDAOImpl.SHIPPING_DETAILS/*CONTACT_DETAILS*/);

		} catch(Exception e) {
			throw e;
		}

		StringBuffer jsonData = new StringBuffer();
		if(dto != null) {
			// create the JSON structure
			jsonData.append("{\"dealerCode\": \"" + dto.getDealerDataBean().getCode() + "\",")
			.append("\"name\": \"" + dto.getDealerDataBean().getName() + "\",")
			.append("\"contactName\": \"" + dto.getDealerDataBean().getContactName() + "\",")
			.append("\"contactNumber\": \"" + formatContactNumber(dto.getDealerDataBean().getContactNumber()) + "\"")
			.append("}");
		} else {
			jsonData.append("Unable to find dealer information");
		}
		log.debug("BackOrderAction : executeAction : json = " + jsonData);
		PrintWriter pw = resp.getWriter();
		resp.setContentType("application/json");
		resp.setContentLength(jsonData.length());
		pw.write(jsonData.toString());

		return null;
	}

	private String formatContactNumber(String argContactNumber){
		String contactNumber = argContactNumber;
		log.debug("Phone Number = " + argContactNumber + "END");
		try{
			argContactNumber = argContactNumber.trim();
			if(argContactNumber.length()==10){
				contactNumber = argContactNumber.substring(0, 3) + "-" + argContactNumber.substring(3, 6) 
				+ "-" + argContactNumber.substring(6, 10);
			}else if(argContactNumber.length() > 10){
				contactNumber = argContactNumber.substring(0, 3) + "-" + argContactNumber.substring(3, 6) 
				+ "-" + argContactNumber.substring(6, 10) + "/" + argContactNumber.substring(10);
			}
		}catch(Exception e){
			contactNumber = argContactNumber;
		}				
		return contactNumber; 
	}
}