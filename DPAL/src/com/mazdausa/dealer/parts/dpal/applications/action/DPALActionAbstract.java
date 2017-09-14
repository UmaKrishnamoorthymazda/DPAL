/*
 * Created on Jan 5, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.applications.action;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mazdausa.common.application.actions.EmazdaActionAbstract;
import com.mazdausa.common.application.actions.UserContext;
import com.mazdausa.common.application.actions.helpers.DealerCollectionViewHelper;
import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.common.user.model.User;
import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class DPALActionAbstract extends EmazdaActionAbstract {
	private static Logger log = EMDCSLogger.getLogger(DPALActionAbstract.class);
	
	private static boolean dumpPropsFlag = false;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse resp) throws Exception
    {
		// to be removed
		/*String dumpSysProps = req.getParameter("dumpSysProps");
		log.debug("########### dumpSysProps = " + dumpSysProps);
		if(dumpSysProps != null && dumpSysProps.equals("dump") && !dumpPropsFlag) {
			log.debug("Dumping system properties:");
			Properties p = System.getProperties();
			Enumeration e = p.keys();
			for(;e.hasMoreElements();) {
				String key = (String)e.nextElement();
				log.debug(key + " = " + p.getProperty(key));
			}
			dumpPropsFlag = true;
		}*/
		// end of block to be removed
		
		UserContext userContext = this.getUserContext(req);
		log.debug("DPALActionAbstract: execute: userContext = " + userContext);
		if(!WebUtils.isDebug()) {
			log.debug("DPALActionAbstract: execute: Initializing request variables");
			initializeViewHelper(userContext, req);
			initializeUserContext(userContext, req);
			initializeDealerDropDownFlag(userContext, req);
		} else {
			req.setAttribute("isShowDropDown", Boolean.FALSE);
		}
		
		// manage country code and language
		if(req.getSession().getAttribute("countryCode") == null)
			manageCountryAndLanguage(req, this.getUserContext(req));
		
		try {
			log.debug("DPALActionAbstract: checking for setDealer");
			// retrieve the dealer code passed as the value of param setDealer
			String dealerCode = req.getParameter("setDealer");
			log.debug("DPALActionAbstract: setDealer parameter = " + dealerCode);
			// Modified to fix Bug id: 6
			// if(dealerCode != null) {
			if(dealerCode != null && !"".equals(dealerCode.trim())) {
				log.debug("DPALActionAbstract: dealerCode is not null");
				log.debug("DPALActionAbstract: setting dealer code to " + dealerCode);
				req.getSession().setAttribute("selectedDealer", dealerCode);
			}
			// The actual dealer code
			req.getSession().setAttribute("dealerCode", WebUtils.getDealerCode(req, this.getUserContext(req)));
			// check to see if the dealer is selected for a non-dealer user
			log.debug("DPALActionAbstract: dealer code is: " + WebUtils.getDealerCode(req, this.getUserContext(req)));
			log.debug("DPALActionAbstract: !isDealer: " + (!WebUtils.isDealer(req, this.getUserContext(req))));
	        if(WebUtils.getDealerCode(req, this.getUserContext(req)) == null && !WebUtils.isDealer(req, this.getUserContext(req))) {
	        	log.debug("DPALActionAbstract: not dealer user. getting main menu");
	        	//req.setAttribute("selectDealer", Boolean.TRUE);
	        	return mapping.findForward("mainmenu");
	        }
	        
	        req.setAttribute("selectedDealer", WebUtils.getDealerCode(req, this.getUserContext(req)));
	        log.debug("DPALActionAbstract: dealer code for the session = " + WebUtils.getDealerCode(req, this.getUserContext(req)));
		} catch(Exception e) {
			log.error("An exception occured while attempting to manage dealer selection", e);
			throw e;
		}
		log.debug("isDealer: " + WebUtils.isDealer(req, this.getUserContext(req)));
		req.setAttribute("isDealer", new Boolean(WebUtils.isDealer(req, this.getUserContext(req))));
		
        ActionForward forward = null;
		try{
			// Modified the following to have more control over exceptions
			forward = /*super.*/executeAction(mapping, form, req, resp);
		} catch(Exception e) {
			log.error("An exception was caught while executing action", e);
			throw e;
		}
		
		//dumpSession(req);
		
		return forward;
    }
	
	public /*DealerCollectionViewHelper*/ void initializeViewHelper(UserContext userContext, HttpServletRequest req){
	    DealerCollectionViewHelper helper = (DealerCollectionViewHelper)req.getSession().getAttribute("UserInfoBean");
	    if (helper == null){
	        helper = new DealerCollectionViewHelper(userContext);
	        req.getSession().setAttribute("UserInfoBean", helper);
	    }
	    /*return helper;*/
	}
	
	public void initializeUserContext(UserContext ctx, HttpServletRequest req) {
		req.setAttribute("userContext", ctx);
	}
	
	public void initializeDealerDropDownFlag(UserContext ctx, HttpServletRequest req) {
		User user = ctx.getUser();
		log.debug("DPALActionAbstract: initializeDealerDropDownFlag: Location Code = " + user.getLoctnCd());
		log.debug("DPALActionAbstract: initializeDealerDropDownFlag: user type check: " + user.isCorporatePerson() + ", " + user.isDistrictPerson() + ", " + user.isRegionalPerson());
		
		if(user.isCorporatePerson() || user.isDistrictPerson() || user.isRegionalPerson())
			req.setAttribute("isShowDropDown", Boolean.TRUE);
		else
			req.setAttribute("isShowDropDown", Boolean.FALSE);
	}
	
	private void manageCountryAndLanguage(HttpServletRequest req, UserContext ctx) {
		//String countryCode = req.getParameter("fromCntryCd");
		//String language = req.getParameter("language");
		
		String countryCode = ctx.getUser().getCountryCd();
		String language = ctx.getUser().getLanguageCd();
		
		if(countryCode != null)
			req.getSession().setAttribute("countryCode", countryCode);
		if(language != null)
			req.getSession().setAttribute("language", language);
		
		log.debug("country = " + req.getSession().getAttribute("countryCode") + ", language = " + req.getSession().getAttribute("language"));
	}
	
	private void dumpSession(HttpServletRequest req) {
		try {
			HttpSession session = req.getSession();
			Enumeration e = session.getAttributeNames();
			log.debug("\n\ndumping session:");
			for(String s=(String)e.nextElement(); e.hasMoreElements(); s=(String)e.nextElement()) {
				/*ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bos);
				oos.writeObject(session.getAttribute(s));
				int size = bos.size();
				oos.close();*/
				log.debug(s + " = " + session.getAttribute(s) /*+ "[" + size +"]"*/);
			}
			log.debug("dump end\n\n");
		} catch(Exception e) {
			log.error("exceptioin occured while dumping session" + e);
			e.printStackTrace();
		}
	}
}
