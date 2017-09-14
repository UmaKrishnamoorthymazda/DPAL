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
import com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean;
import com.mazdausa.dealer.parts.dpal.applications.form.DealerDetailForm;
import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;
import com.mazdausa.dealer.parts.dpal.persistence.DealerDetailDAO;
import com.mazdausa.dealer.parts.dpal.persistence.DealerDetailDAOImpl;
import com.mazdausa.dealer.parts.dpal.persistence.DealerDetailUpdateDAO;
import com.mazdausa.dealer.parts.dpal.persistence.DealerListDAO;
import com.mazdausa.dealer.parts.dpal.persistence.DealerListDAOImpl;
import com.mazdausa.dealer.parts.dpal.persistence.DpalDAOFactory;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailUpdateDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerListDTO;
/**
 * @author InterraIT
 *
 */
public class AdminAction  extends DPALActionAbstract {
	private static Logger log = EMDCSLogger.getLogger(AdminAction.class);
	
	private DealerDetailForm dealerDetailForm;
	
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
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		String forwardPage = "list";
		String action = req.getParameter("aAction");
		log.debug("AdminAction: executeAction: action = " + action);
		log.debug("action = " + action);
		
		String countryCode = WebUtils.getCountryCode(req);//"US";//req.getParameter("fromCntryCd");
		String dealerCode = WebUtils.getDealerCode(req, this.getUserContext(req));//ApplicationUtil.getSystemProperty("DPAL", "debug.selling.dealer");//"23958"; //WebUtils.getDealerCode(req, this.getUserContext(req));

		String wslId = WebUtils.getWSLId(req, this.getUserContext(req));
		
		dealerDetailForm = (DealerDetailForm) form;
		
		if("save".equals(action)) {
			String contactName = req.getParameter("contactName");
			String contactNumber = req.getParameter("contactNumber");
			String[] buyingDealerWslId = req.getParameterValues("buyingDealerWslId");
			String[] shippingDealerWslId = req.getParameterValues("shippingDealerWslId");
			
			String[] buyingDealerFullList = new String[5];
			String[] shippingDealerFullList = new String[5];
						
			log.debug("AdminAction: executeAction: contact name = " + contactName + "num: " + contactNumber);
			for(int z=0; z<5; z++) {
				if(buyingDealerWslId != null && z < buyingDealerWslId.length) buyingDealerFullList[z] = buyingDealerWslId[z];
				else buyingDealerFullList[z] = "";
				
				if(shippingDealerWslId != null && z < shippingDealerWslId.length) shippingDealerFullList[z] = shippingDealerWslId[z];
				else shippingDealerFullList[z] = "";
			}
			for(int z=0; z<5; z++) {
				log.debug("AdminAction: executeAction: buy " + z + "= " + buyingDealerFullList[z] + ", ship " + z + "= " + shippingDealerFullList[z] );
			}
			
			log.debug("AdminAction: executeAction: setting dealerdatabean");
			DealerDataBean bean = new DealerDataBean();
			bean.setContactName(contactName);
			bean.setContactNumber(contactNumber);
			bean.setBuyingDealer(buyingDealerFullList);
			bean.setShippingDealer(shippingDealerFullList);
			
			DealerDetailUpdateDAO dao = null;
			DealerDetailUpdateDTO dto = null;
			
			log.debug("AdminAction: executeAction: setting show update status in form");
			dealerDetailForm.setShowUpdateStatus(true);
			
			try{
				log.debug("AdminAction: executeAction: Getting Factory");
				DpalDAOFactory factory = new DpalDAOFactory(AdminAction.class.getName());
				factory.init("DPAL_FACTORY");
				log.debug("AdminAction: executeAction: Getting DAO");
				dao = (DealerDetailUpdateDAO)factory.lookupDAO(DealerDetailUpdateDAO.class);
				log.debug("AdminAction: executeAction: dao = " + dao);
				log.debug("AdminAction: executeAction: getting DTO");
				dto = dao.findDTO(countryCode, dealerCode, wslId, bean);
				log.debug("AdminAction: executeAction: dto = " + dto + ", status = " + dto.getMessageOutputDataBean()/*.getStatus()*/);

				// verify the update status
				if(dto.getMessageOutputDataBean().getStatus().equals(""))
					dealerDetailForm.setUpdateStatus(true);
				else
					dealerDetailForm.setUpdateStatus(false);
			} catch(Exception e) {
				log.error("An exception occurred while updating dealer details.", e);
				e.printStackTrace();
				dealerDetailForm.setUpdateStatus(false);
			}
		} else if("details".equals(action)) {
			DealerListDAO dao = new DealerListDAOImpl();
			DealerListDTO dto = null;
			try {
				dto = dao.findDTO(dealerCode);
				log.debug("AdminAction: executeAction: dto map values = " + dto.getLdapPersonList());
			} catch(Exception e) {
				throw e;
			}
			
			log.debug("DTO = " + dto);
			
			dealerDetailForm.setDealerListDTO(dto);
			forwardPage = "dealerList";
		}
		
		// get the dealer information to be shown on the page
		// only if "details" is not requested.
		if(!"details".equals(action)) {
			log.debug("list dealer details");
			DealerDetailDAO dao = null;
			DealerDetailDTO dto = null;
			try{
				DpalDAOFactory factory = new DpalDAOFactory(AdminAction.class.getName());
				factory.init("DPAL_FACTORY");
				dao = (DealerDetailDAO)factory.lookupDAO(DealerDetailDAO.class);
				dto = dao.findDTO(countryCode, dealerCode, DealerDetailDAOImpl.ADMIN_DETAILS);
			} catch(Exception e) {
				log.error("An exception occurred while retrieved dealer details.", e);
			}
			req.setAttribute("idList", "");
			dealerDetailForm.setDealerDetailDTO(dto);
			
			forwardPage = "list";
		}
		log.debug("AdminAction: executeAction: forwardPage = " + forwardPage);
		return mapping.findForward(forwardPage);
	}
}

