package com.mazdausa.dealer.parts.dpal.applications.action;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.common.util.ApplicationUtil;
import com.mazdausa.dealer.parts.dpal.applications.form.ProcessedBackOrderForm;
import com.mazdausa.dealer.parts.dpal.applications.util.SortTypes;
import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;
import com.mazdausa.dealer.parts.dpal.persistence.dto.ProcessedBackOrderDTO;
import com.mazdausa.dealer.parts.dpal.services.ProcessedBackOrderListCommand;

public class TodayShippedPartListAction extends DPALActionAbstract {
	
	private static Logger log = EMDCSLogger.getLogger(TodayShippedPartListAction.class);
	private ProcessedBackOrderForm processedBackOrderForm;
	
	protected  ActionForward executeAction (
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {

		String forwardPage = "list";
		
		processedBackOrderForm = (ProcessedBackOrderForm) form;

		String action = req.getParameter("aAction");
		
		String countryCode = WebUtils.getCountryCode(req) ;
		String dealerCode =WebUtils.getDealerCode(req, this.getUserContext(req));
		String pageNumber = req.getParameter("pageNumber")==null?"1":req.getParameter("pageNumber");
		String pageSize = WebUtils.getPageSize(); log.debug("pageSize = " + pageSize);
		String sortType = req.getParameter("sortType")==null ||"".equals(req.getParameter("sortType").trim())?SortTypes.BACKORDER_DATE_DSC:req.getParameter("sortType");

		// contains a list of selected rows in the form <sales-order>|<line-no>
		String idList = req.getParameter("idList");
		log.debug("ProcessedBackOrderAction : executeAction : 1. idList = " + idList);
		log.debug(action);
		
		String trackingnumber = ApplicationUtil.getSystemProperty("APPLICATION_URLS","ups.tracking.url" );
		req.setAttribute("trackingnumber", trackingnumber);
		ProcessedBackOrderListCommand command = null;
		String statusFlag = "P";
		if(action == null || "".equals(action.trim()) || "list".equals(action) || "sort".equals(action)) {
			log.debug("ProcessedBackOrderAction : executeAction :listing backorders");
			// if we're here, we have to show the first page
			req.setAttribute("pageNumber", "1");
			req.setAttribute("idList", "");
			//processedBackOrderForm.setIdArrayList(new ArrayList());
			forwardPage = "list";
			command = new ProcessedBackOrderListCommand(countryCode, dealerCode, statusFlag, pageNumber, pageSize, sortType);
			command.list();			
		} else if("page".equals(action)) {
			int page = paginate(req, processedBackOrderForm);
			pageNumber = Integer.toString(page);
			forwardPage = "list";
			command = new ProcessedBackOrderListCommand(countryCode, dealerCode, statusFlag, pageNumber, pageSize, sortType);
			command.list();
		}
		
		log.debug("countryCode = " + countryCode + ", dealerCode = " + dealerCode + ", pageNumber = " + pageNumber +
				", pageSize = " + pageSize + ", sortType = " + sortType);
		
		// retrieve the dto and set it in the actionform
		ProcessedBackOrderDTO dto = command.getProcessedBackOrderDTO();
		log.debug("ProcessedBackOrderAction : executeAction :action]: dto = " + dto + ", backOrderForm = " + processedBackOrderForm);
		processedBackOrderForm.setProcessedBackOrderDTO(dto);
		
		// retify the total page count
		int totalCount = dto!=null?dto.getTotalRecordsCount():0;
		int totalPages = (int)Math.ceil((double)(totalCount)/Double.parseDouble(WebUtils.getPageSize()));
		req.setAttribute("totalPages", Integer.toString(totalPages));
		
		log.debug("forwardPage = " + forwardPage);
		return mapping.findForward(forwardPage);
}
	private int paginate(HttpServletRequest req, ProcessedBackOrderForm form) {
		// since the actual number of records can change between requests,
		// calculate the next requested page based on what we already know
		// and rectify the total count as retrieved from the proc call.
		// This might result in a minor bug in some situations, but this is
		// the best way we have.
		String pageNumber = req.getParameter("pageNumber")==null?"1":req.getParameter("pageNumber");
		String pageAction = req.getParameter("pageAction");
		String totalCount = req.getParameter("totalCount");
		int totalPages = (int)Math.ceil(Double.parseDouble(totalCount)/Double.parseDouble(WebUtils.getPageSize()));
		log.debug("ProcessedBackOrderAction : pagination : page calc: " + (Double.parseDouble(totalCount)/Double.parseDouble(WebUtils.getPageSize())));
		
		int nPageNumber = 1;
		try {
			nPageNumber = Integer.parseInt(pageNumber);
		} catch(Exception e) {
			nPageNumber = 1;
		}
		int targetPage = nPageNumber;			
		log.debug("ProcessedBackOrderAction : pagination : totalPages = " + totalPages);
		
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
		log.debug("ProcessedBackOrderAction : pagination : targetPage = " + targetPage);
		pageNumber = Integer.toString(targetPage);
		req.setAttribute("pageNumber", pageNumber);
		
		log.debug("ProcessedBackOrderAction : pagination : setting idlist values");
		// set the selected id values
		String idList = req.getParameter("idList");
		log.debug("ProcessedBackOrderAction : pagination : idList = " + idList);
		ArrayList idArrayList = new ArrayList();
		if(idList != null && idList.trim().length()!= 0) {
			String[] idArray = idList.split(",");
			log.debug("ProcessedBackOrderAction : pagination : idArray = " + idArray + ", length = " + idArray.length);
			try {
				idArrayList = new ArrayList(Arrays.asList(idArray));
			} catch(Exception e) {
				e.printStackTrace();
			}
			log.debug("ProcessedBackOrderAction : pagination : idArrayList = " + idArrayList);			
		}
		else
			idList = "";
		req.setAttribute("idList", idList);
		//processedBackOrderForm.setIdArrayList(idArrayList);
		
		log.debug("ProcessedBackOrderAction : pagination : idList set successfully");
		return targetPage;
	}
}