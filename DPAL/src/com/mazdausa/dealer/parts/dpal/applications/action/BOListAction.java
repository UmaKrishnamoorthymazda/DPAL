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

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.dealer.parts.dpal.applications.form.BackOrderForm;
import com.mazdausa.dealer.parts.dpal.applications.util.SortTypes;
import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;
import com.mazdausa.dealer.parts.dpal.persistence.dto.BackOrderDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.StatusUpdateDTO;
import com.mazdausa.dealer.parts.dpal.services.BackOrderListCommand;

/**
 * @author InterraIT
 * 
 */
public class BOListAction extends /*EmazdaActionAbstract*/ DPALActionAbstract {
	
	private static Logger log = EMDCSLogger.getLogger(BOListAction.class);
	private BackOrderForm backOrderForm;

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
		//if(true) throw new Exception("test exception");
		
		String forwardPage = "list";
		
		backOrderForm = (BackOrderForm) form;

		String action = req.getParameter("aAction");
		log.debug("action = " + action);
		String countryCode = WebUtils.getCountryCode(req) ;
		String dealerCode = WebUtils.getDealerCode(req, this.getUserContext(req));//ApplicationUtil.getSystemProperty("DPAL", "debug.selling.dealer");//"23958"; //WebUtils.getDealerCode(req, this.getUserContext(req));
		log.debug("dealerCode = " + dealerCode);
		String pageNumber = req.getParameter("pageNumber")==null?"1":req.getParameter("pageNumber");
		String pageSize = WebUtils.getPageSize(); log.debug("pageSize = " + pageSize);
		// Modified to fix Bug id: 7, 8, 9
		//String sortType = req.getParameter("sortType")==null?SortTypes.BACKORDER_DATE_DSC:req.getParameter("sortType");
		String sortType = req.getParameter("sortType")==null || "".equals(req.getParameter("sortType").trim())?SortTypes.BACKORDER_DATE_DSC:req.getParameter("sortType");
		// Begin: Added to fix Bug Id: 19
		int estimatedTotalCount = req.getParameter("totalCount") == null? 0: Integer.parseInt(req.getParameter("totalCount"));
		int decreaseValue = 0;
		// End changes
		
		log.debug("sortType = " + sortType);

		// contains a list of selected rows in the form <sales-order>|<line-no>
		String idList = req.getParameter("idList");
		log.debug("BackOrderAction : executeAction : 1. idList = " + idList);
		log.debug(action);
		
		BackOrderListCommand command = null;
		if(action == null || "".equals(action.trim()) || "list".equals(action) || "sort".equals(action)) {
			log.debug("BackOrderAction : executeAction :listing backorders");
			
			// if we're here, we have to show the first page
			req.setAttribute("pageNumber", "1");
			// Added to fix Bug id: 7, 8, 9
			if("sort".equals(action))
				pageNumber = "1";
			
			// Added to fix Bug id: 32
			String setDealer = req.getParameter("setDealer");
			log.debug("********setDealer = " + setDealer);
			if(setDealer != null && !setDealer.trim().equals("")) {
				req.setAttribute("pageNumber", "1");
				pageNumber = "1";
			}
			// End changes
			
			// Begin: Added/Modified to fix Bug id: 33
			if("sort".equals(action)){
				ArrayList idArrayList = getIdArrayList(idList);
				idList = (idList != null && idList.trim().length() == 0)? "": idList;
				req.setAttribute("idList", idList);
				backOrderForm.setIdArrayList(idArrayList);
			} else {
				// original code
				req.setAttribute("idList", "");
				backOrderForm.setIdArrayList(new ArrayList());
			}
			// End changes
			
			forwardPage = "list";
			command = new BackOrderListCommand(countryCode, dealerCode, pageNumber, pageSize, sortType);
			command.list();			
		} else if("page".equals(action)) {
			int page = paginate(req, backOrderForm);
			pageNumber = Integer.toString(page);
			forwardPage = "list";
			command = new BackOrderListCommand(countryCode, dealerCode, pageNumber, pageSize, sortType);
			command.list();
		} else if("commit".equals(action)) {
			forwardPage = "list";
			String wslId = WebUtils.getWSLId(req, this.getUserContext(req));
			backOrderForm.setShowUpdateStatus(true);
			log.debug("BackOrderAction : executeAction :commit :wslId = " + wslId);
			if(idList != null) {
				command = new BackOrderListCommand(countryCode, dealerCode, idList, wslId, "W", "O"); 
				StatusUpdateDTO dto = command.commit();
				log.debug("AdminAction: executeAction: dto = " + dto + ", status = " + dto.getStatusUpdateDataList()/*dto.getMessageOutputDataBean().getStatus()*//*.getStatus()*/);
				// Modified to fix Bug id: 2
				//if(dto.getMessageOutputDataBean().getStatus().equals("")){
				if(dto.isUpdateSuccess()){
					backOrderForm.setUpdateStatus(true);
					log.debug("Have Updated Successfully" + backOrderForm.isUpdateStatus());
					// calculate the number of records that were committed
					decreaseValue = idList.split(",").length;
					req.setAttribute("errorIdList", "");
				}
				else {
					backOrderForm.setUpdateStatus(false);
					handleErrorIdList(req, dto.getErrorIdList());
				}
				// for debug only
				//Thread.sleep(10000);
			}
			
			command = new BackOrderListCommand(countryCode, dealerCode, "1", pageSize, sortType);
			command.list();
			req.setAttribute("pageNumber", "1");
			req.setAttribute("idList", "");
			backOrderForm.setIdArrayList(new ArrayList());
		}
		
		log.debug("countryCode = " + countryCode + ", dealerCode = " + dealerCode + ", pageNumber = " + pageNumber +
				", pageSize = " + pageSize + ", sortType = " + sortType);
		
		// retrieve the dto and set it in the actionform
		BackOrderDTO dto = command.getBackOrderDTO();
		log.debug("BackOrderAction : executeAction :action]: dto = " + dto + ", backOrderForm = " + backOrderForm);
		backOrderForm.setBackOrderDTO(dto);
		
		// retify the total page count
		int totalCount = dto != null?dto.getTotalRecordsCount():0;
		
		// Added to fix Bug id: 19
		// check to see if the B/O quantity has changed between requests
		if(WebUtils.isDealer(req, this.getUserContext(req)) && estimatedTotalCount != 0 && (estimatedTotalCount - decreaseValue != totalCount)){
			req.setAttribute("totalCountChanged", Boolean.TRUE);
		} 
			
		int totalPages = (int)Math.ceil((double)(totalCount)/Double.parseDouble(WebUtils.getPageSize()));
		
		req.setAttribute("totalPages", Integer.toString(totalPages));
		//backOrderForm.setBackOrderDTO(null);
		log.debug("forwardPage = " + forwardPage);
		return mapping.findForward(forwardPage);
	}
	
	// Added to fix Bug id: 2
	private void handleErrorIdList(HttpServletRequest req, String errorIdList) {
		if(errorIdList != null && errorIdList.trim().length() != 0) {
			ArrayList errorIdArrayList = null;
			// populate the errorId arraylist
			String[] errorIdArray = errorIdList.split(",");
			log.debug("BackOrderAction : handleErrorIdList : errorIdArray = " + errorIdArray + ", length = " + errorIdArray.length);
			try {
				errorIdArrayList = new ArrayList(Arrays.asList(errorIdArray));
			} catch(Exception e) {
				e.printStackTrace();
			}
			backOrderForm.setErrorIdArrayList(errorIdArrayList);
		} else {
			errorIdList = "";
		}
		//req.setAttribute("errorIdList", errorIdList);
		backOrderForm.setErrorIdList(errorIdList);
	}
	// End changes
	
	private int paginate(HttpServletRequest req, BackOrderForm form) {
		// since the actual number of records can change between requests,
		// calculate the next requested page based on what we already know
		// and rectify the total count as retrieved from the proc call.
		// This might result in a minor bug in some situations, but this is
		// the best way we have.
		String pageNumber = req.getParameter("pageNumber")==null?"1":req.getParameter("pageNumber");
		String pageAction = req.getParameter("pageAction");
		String totalCount = req.getParameter("totalCount");
		int totalPages = (int)Math.ceil(Double.parseDouble(totalCount)/Double.parseDouble(WebUtils.getPageSize()));
		log.debug("BackOrderAction : pagination : page calc: " + (Double.parseDouble(totalCount)/Double.parseDouble(WebUtils.getPageSize())));
		
		int nPageNumber = 1;
		try {
			nPageNumber = Integer.parseInt(pageNumber);
		} catch(Exception e) {
			nPageNumber = 1;
		}
		int targetPage = nPageNumber;			
		log.debug("BackOrderAction : pagination : totalPages = " + totalPages);
		
		// decide the page to be target page to be shown depending upon the pageAction command (first, previous, next last)
		if("first".equals(pageAction)) {
			targetPage = 1;
		} else if("previous".equals(pageAction)) {
			targetPage = (nPageNumber - 1) <= 0?1:(nPageNumber - 1);
		} else if("next".equals(pageAction)) {
			targetPage = (nPageNumber + 1) > totalPages?totalPages:(nPageNumber + 1);
		} else if("last".equals(pageAction)) {
			targetPage = totalPages;
		}
		log.debug("BackOrderAction : pagination : targetPage = " + targetPage);
		pageNumber = Integer.toString(targetPage);
		req.setAttribute("pageNumber", pageNumber);
		
		log.debug("BackOrderAction : pagination : setting idlist values");
		// set the selected id values
		String idList = req.getParameter("idList");
		log.debug("BackOrderAction : pagination : idList = " + idList);
		/*ArrayList idArrayList = new ArrayList();
		if(idList != null && idList.trim().length()!= 0) {
			String[] idArray = idList.split(",");
			log.debug("BackOrderAction : pagination : idArray = " + idArray + ", length = " + idArray.length);
			try {
				idArrayList = new ArrayList(Arrays.asList(idArray));
			} catch(Exception e) {
				e.printStackTrace();
			}
			log.debug("BackOrderAction : pagination : idArrayList = " + idArrayList);			
		}
		else
			idList = "";*/
		ArrayList idArrayList = getIdArrayList(idList);
		idList = (idList == null && idList.trim().length() == 0)? "": idList;
		req.setAttribute("idList", idList);
		backOrderForm.setIdArrayList(idArrayList);
		// Added to fix Bug id: 2
		handleErrorIdList(req, req.getParameter("errorIdList"));
		
		log.debug("BackOrderAction : pagination : idList set successfully");
		return targetPage;
	}
	
	private ArrayList getIdArrayList(String idList) {
		ArrayList idArrayList = new ArrayList();
		if(idList != null && idList.trim().length()!= 0) {
			String[] idArray = idList.split(",");
			log.debug("BackOrderAction : pagination : idArray = " + idArray + ", length = " + idArray.length);
			try {
				idArrayList = new ArrayList(Arrays.asList(idArray));
			} catch(Exception e) {
				e.printStackTrace();
			}
			log.debug("BackOrderAction : pagination : idArrayList = " + idArrayList);			
		}
		return idArrayList;
	}
}
