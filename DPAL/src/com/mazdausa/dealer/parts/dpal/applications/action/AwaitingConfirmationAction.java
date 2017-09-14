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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.xerces.impl.dv.util.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.mazdausa.common.jmsinterface.EmazdaJMSMessageListener;
import com.mazdausa.common.jmsinterface.EmazdaJMSServiceFactory;
import com.mazdausa.common.jmsinterface.MazdaCommonJMSService;
import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.common.mail.EMazdamailsender;
import com.mazdausa.common.util.ApplicationUtil;
import com.mazdausa.dealer.parts.dpal.applications.databeans.BOCancelMQMessageDataBean;
import com.mazdausa.dealer.parts.dpal.applications.databeans.BackOrderDataBean;
import com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean;
import com.mazdausa.dealer.parts.dpal.applications.databeans.ItemAttributeDataBean;
import com.mazdausa.dealer.parts.dpal.applications.databeans.MQMessageDataBean;
import com.mazdausa.dealer.parts.dpal.applications.form.BackOrderForm;
import com.mazdausa.dealer.parts.dpal.applications.util.SortTypes;
import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;
import com.mazdausa.dealer.parts.dpal.persistence.DealerDetailDAO;
import com.mazdausa.dealer.parts.dpal.persistence.DealerDetailDAOImpl;
import com.mazdausa.dealer.parts.dpal.persistence.DpalDAOFactory;
import com.mazdausa.dealer.parts.dpal.persistence.dto.BackOrderDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.StatusUpdateDTO;
import com.mazdausa.dealer.parts.dpal.services.AwaitingConfirmationCommand;
import com.mazdausa.dealer.parts.dpal.services.ShippingConfirmationCommand;
import com.mazdausa.dealer.parts.dpal.ups.ProcessUPSWebService;
import com.mazdausa.dealer.parts.dpal.ups.RequestSender;
/**
 * @author InterraIT
 * 
 */
public class AwaitingConfirmationAction extends DPALActionAbstract {
	private BackOrderForm backOrderForm;
	private static Logger log = EMDCSLogger.getLogger(AwaitingConfirmationAction.class);
	/**
	 * This method handles verifies the user authentication and displays the
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


		backOrderForm = (BackOrderForm) form;

		String forwardPage = "list";
		String action = req.getParameter("aAction");
		log.debug("action = " + action);		
		String countryCode = WebUtils.getCountryCode(req) ;
		String dealerCode = WebUtils.getDealerCode(req, this.getUserContext(req));//ApplicationUtil.getSystemProperty("DPAL", "debug.selling.dealer");//"23958"; //WebUtils.getDealerCode(req, this.getUserContext(req));
		String pageNumber = req.getParameter("pageNumber")==null?"1":req.getParameter("pageNumber");
		String pageSize = WebUtils.getPageSize(); log.debug("pageSize = " + pageSize);
		// Modified to fix Bug id: 7, 8, 9
		//String sortType = req.getParameter("sortType")==null?SortTypes.BACKORDER_DATE_DSC:req.getParameter("sortType");
		String sortType = req.getParameter("sortType")==null || "".equals(req.getParameter("sortType").trim())?SortTypes.BACKORDER_DATE_DSC:req.getParameter("sortType");


		String wslId = WebUtils.getWSLId(req, this.getUserContext(req));
		String buyingdealerCode = req.getParameter("buyingDealerCode");
		log.debug("Buying Dealer Code" + buyingdealerCode);
		// contains a list of selected rows in the form <sales-order>|<line-no>
		String idList = req.getParameter("idList");
		//boolean processingDone = false;

		// pagination variables
		if(action == null || "".equals(action) || "list".equals(action) || "sort".equals(action)) {

			log.debug("AwaitingConfirmationAction: executeAction: listing awaiting shipment confirmation");
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
			
			if("sort".equals(action)){
				ArrayList idArrayList = getIdArrayList(idList);
				idList = (idList == null && idList.trim().length() == 0)? "": idList;
				req.setAttribute("idList", idList);
				backOrderForm.setIdArrayList(idArrayList);
			} else {
				// original code
				req.setAttribute("idList", "");
				backOrderForm.setIdArrayList(new ArrayList());
			}
			req.setAttribute("buyingDealerCode","");
			forwardPage = "list";

		} else if("page".equals(action)) {
			int page = paginate(req, backOrderForm);
			pageNumber = Integer.toString(page);
			forwardPage = "list";

		} else if("ship".equals(action)) {
			forwardPage = "list";
			log.debug("AwaitingConfirmationAction: executeAction: ship: idList = " + idList);
			log.debug("AwaitingConfirmationAction: executeAction: ship: wslId = " + wslId);
			backOrderForm.setShowUpdateStatus(true);
			if(idList != null) {
				AwaitingConfirmationCommand command = new AwaitingConfirmationCommand(countryCode, dealerCode, idList, wslId, "G", "W"); 
				StatusUpdateDTO dto = command.updateStatus();
				// Modified to fix Bug id: 2
				//if(dto.getMessageOutputDataBean().getStatus().equals("")){
				if(dto.isUpdateSuccess()){
					backOrderForm.setUpdateStatus(true);
					log.debug("Have Updated Successfully" + backOrderForm.isUpdateStatus());
				}
				else
					backOrderForm.setUpdateStatus(false);
				log.debug("AwaitingConfirmationAction: executeAction: getErrorIdArrayList = " + command.getErrorIdArrayList());
				if(command.getErrorIdArrayList() == null)
					forwardPage = "shippingconfirmation";
				else
					req.setAttribute("errorIdList", command.getErrorIdArrayList().toArray());
			}		
			req.setAttribute("pageNumber", "1");
			req.setAttribute("idList", "");
			req.setAttribute("buyingDealerCode","");
			//log.debug("***************************** forwardPage = " + forwardPage);
			backOrderForm.setIdArrayList(new ArrayList());

		} else if("remove".equals(action)) {
			forwardPage = "list";
			log.debug("AwaitingConfirmationAction: executeAction: remove : wslId = " + wslId);
			backOrderForm.setShowUpdateStatus(true);
			if(idList != null) {
				AwaitingConfirmationCommand command = new AwaitingConfirmationCommand(countryCode, dealerCode, idList, wslId, "O", "W"); 
				StatusUpdateDTO dto = command.updateStatus();
				//Modified to fix Bug id: 2
				//if(dto.getMessageOutputDataBean().getStatus().equals("")){
				if(dto.isUpdateSuccess()){
					backOrderForm.setUpdateStatus(true);
					log.debug("Have Updated Successfully" + backOrderForm.isUpdateStatus());
				}
				else
					backOrderForm.setUpdateStatus(false);
			}
			req.setAttribute("pageNumber", "1");
			req.setAttribute("idList", "");
			backOrderForm.setIdArrayList(new ArrayList());

		} else if("confirm".equals(action)) {
			log.debug("inside confirm");
			forwardPage = "shippingconfirmation";
			backOrderForm.setStage("0");

			String[] ids = req.getParameterValues("id");
			log.debug("AwaitingConfirmationAction: executeAction: ids length: " + ids.length);
			String[] shipQuantity = req.getParameterValues("shipQuantity");
			String[] boQuantity = req.getParameterValues("boQuantity");
			String[] buyingDealerCode = req.getParameterValues("buyingDealerCode");

			ShippingConfirmationCommand shipCommand = new ShippingConfirmationCommand(countryCode, dealerCode);
			ArrayList errorIdList = null; 
			Map idErrorType = new HashMap();
			Map idQuantityMap = new HashMap();
			int zeroQuantityCount = 0;
			int skipItemCount = 0;	
			boolean errorInLine = false;
			for(int z=0; z<ids.length; z++) {
				errorInLine = false;

				String[] item = ids[z].split("\\|");
				log.debug("Sales Order Number" + item[0] + ", Line number" + item[1]);
				shipCommand.setSalesOrder(item[0]);
				shipCommand.setLineNumber(item[1]);

				shipCommand.setDealerCode(buyingDealerCode[0]);
				log.debug("Buying dealer code: " + buyingDealerCode[0]);				
				shipCommand.execute();

				ItemAttributeDataBean boItem = shipCommand.getItemAttributeDTO().getItemAttributeDataBean();
				idQuantityMap.put(ids[z], new Integer(shipQuantity[z]));
				log.debug("AwaitingConfirmationAction: execute action: stage 1 verification: boItem.getQuantity = " + boItem.getQuantity() + ", shipQuantity = " + shipQuantity[z]);

				log.debug("shipQuantity[z] = " + shipQuantity[z] + ", boItem.getQuantity() = " + boItem.getQuantity() + "ids[" +z + "] = " + ids[z]);
				//check if b/o quantity is less than shipped quantity. If yes, then update the b/o quantity with the quantity
				//in DB2SOLN table and set the status flag to G in DB2DPDR and DB2DPBO.				
				if(boItem.getQuantity() != 0 && (boItem.getQuantity() < Integer.parseInt(shipQuantity[z]))) {
					log.debug("AwaitingConfirmationAction: execute action: BO quantity is less than entered ship quantity");
					if(errorIdList == null) errorIdList = new ArrayList();
					errorIdList.add(ids[z]);
					idErrorType.put(ids[z], "greater");
					backOrderForm.setShowUpdateStatusShip(true);
					errorInLine = true;
					double shippedQuantity = new Double(boItem.getQuantity()-Double.parseDouble(boQuantity[z])).doubleValue();
					AwaitingConfirmationCommand command = new AwaitingConfirmationCommand(countryCode, dealerCode,
							ids[z], wslId, "G", "G", new Double(shippedQuantity).toString());
					log.debug("AwaitingConfirmationAction: execute action: about to update status & quantity");
					StatusUpdateDTO dto = command.updateStatusAndQuantity();	
					log.debug("AdminAction: executeAction: dto = " + dto + ", status = " + dto.getStatusUpdateDataList());
					//Modified to fix Bug id: 2
					//if(dto.getMessageOutputDataBean().getStatus().equals("")){
					if(dto.isUpdateSuccess()){
						backOrderForm.setUpdateStatus(true);
						log.debug("Have Updated Successfully" + backOrderForm.isUpdateStatus());
					}
					else
						backOrderForm.setUpdateStatus(false);
				}
				
				log.debug("(Integer.parseInt(shipQuantity[z]) == 0) = " + (Integer.parseInt(shipQuantity[z]) == 0));

				if(Integer.parseInt(shipQuantity[z]) == 0) {
					if(errorIdList == null) errorIdList = new ArrayList();
					errorIdList.add(ids[z]);
					log.debug("adding error type: skip");
					idErrorType.put(ids[z], "skip");
					errorInLine = true;
					skipItemCount++;
				}
				
				else if(boItem.getQuantity() == 0) {
					if(errorIdList == null) errorIdList = new ArrayList();
					errorIdList.add(ids[z]);
					idErrorType.put(ids[z], "zero");
					errorInLine = true;
					zeroQuantityCount++;
				} 
				log.debug("AwaitingConfirmationAction: execute action: errorIdList = " + errorIdList);

				if(errorInLine){ 
					backOrderForm.setErrorIdArrayList(errorIdList);
					backOrderForm.setErrorType(idErrorType);
					// Bug Fix: Check for the situation when all the records to be displayed 
					// have B/O quantity zero. A disabled "Process More Shipments" button there
					// will leave the users in a lurch 
					backOrderForm.setIdQuantityMap(idQuantityMap);
					log.debug("AwaitingConfirmationAction: execute action: IN STAGE 1: errorIdList is NOT null. Stage 1");					
				}
				else {
					log.debug("AwaitingConfirmationAction: execute action: IN STAGE 2: errorIdList is null. Stage 2");
					log.debug("AwaitingConfirmationAction: execute action: idQuantityMap: " + idQuantityMap);

					backOrderForm.setIdQuantityMap(idQuantityMap);						
					idErrorType.put(ids[z], "include");
					backOrderForm.setErrorType(idErrorType);
				}
			}
			if(errorIdList != null){
				log.debug("#### reached here");
				backOrderForm.setStage("1");
				backOrderForm.setDisableButtonConfirmAndCancel(true);
				log.debug("#### Size of error id list" + errorIdList.size());
				if(errorIdList.size()< ids.length){
					log.debug("#### Size of error id list" + errorIdList.size());
					if(ids.length > zeroQuantityCount) 
						backOrderForm.setStage("2");

					if(ids.length > skipItemCount)
						backOrderForm.setStage("2");

				}	
			}else{
				backOrderForm.setDisableButtonConfirmAndCancel(true);
				backOrderForm.setStage("2");

			}
		}
		else if("label".equals(action)) {
			forwardPage = null;

			String[] ids = req.getParameterValues("id");
			log.debug("AwaitingConfirmationAction: executeAction: label:  ids length: " + ids.length);
			String[] shipQuantity = req.getParameterValues("shipQuantity");
			String[] boQuantity = req.getParameterValues("boQuantity");
			String[] partNumber = req.getParameterValues("partNumber");
			boolean errorFromUPS = false;
			String confirmresp = null;
			String acceptresp = null;
			ArrayList errorIdListForRollback = null;
			log.debug("AwaitingConfirmationAction: executeAction: label: Entering loop");

			for(int z=0; z<ids.length; z++) {
				//log.debug("AwaitingConfirmationAction: executeAction: label: z = " + z);
				log.debug("AwaitingConfirmationAction: executeAction: label: boQuantity[" + z + "] = " + boQuantity[z]);

				if(Integer.parseInt(boQuantity[z]) == 0) {
					log.debug("AwaitingConfirmationAction: execute action: label: BO quantity is zero");
					backOrderForm.setShowUpdateStatusShip(true);
					log.debug("AwaitingConfirmationAction: execute action: label: updating status G -> C");
					AwaitingConfirmationCommand command = new AwaitingConfirmationCommand(countryCode, dealerCode, /*idList*/ids[z], wslId, "C", "G"); 
					StatusUpdateDTO dto = command.updateStatus();
					log.debug("AdminAction: executeAction: dto = " + dto + ", status = " + dto.getStatusUpdateDataList()/*.getStatus()*/);
					//Modified to fix Bug id: 2
					//if(dto.getMessageOutputDataBean().getStatus().equals("")){
					if(dto.isUpdateSuccess()){
						backOrderForm.setUpdateStatus(true);
						log.debug("Have Updated Successfully" + backOrderForm.isUpdateStatus());
					}
					else{
						backOrderForm.setUpdateStatus(false);
						errorIdListForRollback = new ArrayList();
						errorIdListForRollback.add(ids[z]);
					}
					log.debug("AwaitingConfirmationAction: execute action: label: Update Success");
				}

				if(Integer.parseInt(shipQuantity[z]) == 0) {
					log.debug("AwaitingConfirmationAction: execute action: label: shipped quantity is zero");		
					log.debug("AwaitingConfirmationAction: execute action: label: updating status G ->O");
					backOrderForm.setShowUpdateStatusShip(true);
					AwaitingConfirmationCommand command = new AwaitingConfirmationCommand(countryCode, dealerCode, ids[z], wslId, "O", "G"); 
					StatusUpdateDTO dto =command.updateStatus();
					//Modified to fix Bug id: 2
					//if(dto.getMessageOutputDataBean().getStatus().equals("")){
					if(dto.isUpdateSuccess()){
						backOrderForm.setUpdateStatus(true);
						log.debug("Have Updated Successfully" + backOrderForm.isUpdateStatus());
					}
					else{
						backOrderForm.setUpdateStatus(false);
						errorIdListForRollback = new ArrayList();
						errorIdListForRollback.add(ids[z]);
					}
					log.debug("AwaitingConfirmationAction: execute action: label: Update Success");
				}
				//check if the b/o quantity is equal to shipped quantity. If yes, then update the b/o quantity in DB2DPBO to 
				//zero and set the status flag to P in DB2DPDR and DB2DPBO. Activate process more shipment button
				if(Integer.parseInt(boQuantity[z]) != 0 && (Integer.parseInt(boQuantity[z]) == Integer.parseInt(shipQuantity[z]))) {
					log.debug("AwaitingConfirmationAction: execute action: BO quantity is equal than entered ship quantity");
					backOrderForm.setShowUpdateStatusShip(true);
					// Begin: Modified to fix Bug id: 17
					double shippedQuantity = new Double(shipQuantity[z]).doubleValue();
					AwaitingConfirmationCommand command = new AwaitingConfirmationCommand(countryCode, dealerCode,
							ids[z], wslId, "P", "P", new Double(shippedQuantity).toString());
					/*log.debug("updating quantity to 0");
					AwaitingConfirmationCommand command = new AwaitingConfirmationCommand(countryCode, dealerCode,
							ids[z], wslId, "P", "P", new Double(0).toString());*/
					// End changes
					log.debug("AwaitingConfirmationAction: execute action: about to update status & quantity");
					StatusUpdateDTO dto= command.updateStatusAndQuantity();	
					//Modified to fix Bug id: 2
					//if(dto.getMessageOutputDataBean().getStatus().equals("")){
					if(dto.isUpdateSuccess()){
						backOrderForm.setUpdateStatus(true);
						log.debug("Have Updated Successfully" + backOrderForm.isUpdateStatus());
					}
					else{						
						backOrderForm.setUpdateStatus(false);
						errorIdListForRollback = new ArrayList();
						errorIdListForRollback.add(ids[z]);
					}
					if(errorIdListForRollback != null) 
						backOrderForm.setErrorIdArrayList(errorIdListForRollback);				
				}
				//check if the shipped quantity is less than b/o quantity. If yes, then update the b/o quantity to 
				//(b/o quantity - shipped quantity) & set the status flag to P in DB2DPDR and O in DB2DPBO. Activate 
				//process more shipment button
				if(Integer.parseInt(boQuantity[z]) != 0 && Integer.parseInt(shipQuantity[z]) != 0 && (Integer.parseInt(boQuantity[z]) > Integer.parseInt(shipQuantity[z]))) {
					log.debug("AwaitingConfirmationAction: execute action: BO quantity is greater than entered ship quantity");
					backOrderForm.setShowUpdateStatusShip(true);
					double shippedQuantity = new Double(shipQuantity[z]).doubleValue();

					AwaitingConfirmationCommand command = new AwaitingConfirmationCommand(countryCode, dealerCode,
							ids[z], wslId, "O", "P", new Double(shippedQuantity).toString());
					/*AwaitingConfirmationCommand command = new AwaitingConfirmationCommand(countryCode, dealerCode,
							ids[z], wslId, "O", "P", new Double(backOrderQuantity - shippedQuantity).toString());*/
					// End changes
					log.debug("AwaitingConfirmationAction: execute action: about to update status & quantity where boquantity is greater than shipped quantity");
					StatusUpdateDTO dto = command.updateStatusAndQuantity();	
					//Modified to fix Bug id: 2
					//if(dto.getMessageOutputDataBean().getStatus().equals("")){
					if(dto.isUpdateSuccess()){
						backOrderForm.setUpdateStatus(true);
						log.debug("Have Updated Successfully" + backOrderForm.isUpdateStatus());
					}
					else{
						backOrderForm.setUpdateStatus(false);
						errorIdListForRollback = new ArrayList();
						errorIdListForRollback.add(ids[z]);
					}

					if(errorIdListForRollback != null) 
						backOrderForm.setErrorIdArrayList(errorIdListForRollback);
				}
			}

			// If any of the shipment fails all the committed records needs to be rolled back
			if(backOrderForm.getErrorIdArrayList() != null){
				req.setAttribute("errorIdList", backOrderForm.getErrorIdArrayList().toArray());
				//roll back status and quantity
				rollBackStatutsAndQuantity(backOrderForm.getErrorIdArrayList(),
						ids, countryCode, dealerCode, wslId, boQuantity, shipQuantity, req);
			} else {
				log.debug("AwaitingConfirmationAction: execute action: label");
	
				// UPS Shipment
				String buyingDealerCode = req.getParameter("buyingDealerCode");
	
				// Create a new ItemAttribute bean to contain the total weight and price
				ItemAttributeDataBean bean = new ItemAttributeDataBean();
	
				// Calculate the total weight and price of the shipment.
				// Weight will be used for UPS shipping. Item attributes will be 
				// used for the MQ message (Credit and Debit memo)
				ArrayList items = calculateTotalWeightAndPrice(countryCode, buyingDealerCode, ids, shipQuantity, boQuantity, bean);
				// UPS Bug Fix: Package weights rounded off to zero is not acceptable by UPS
				if(bean.getWeight() < 1.0) bean.setWeight(1.0);
				String packageWeight = new Double(bean.getWeight()).toString();
				log.debug("AwaitingConfirmationAction: execute action: calculated weight = " + bean.getWeight());
	
				AwaitingConfirmationCommand dealerCommand = new AwaitingConfirmationCommand();
				dealerCommand.setCountryCode(countryCode);
				dealerCommand.setDealerCode(buyingdealerCode);
				dealerCommand.getDealerDetails();
				log.debug("for buying dealer: command.getDealerDTO() = " + dealerCommand.getDealerDTO());
				DealerDataBean buyingDealerBean = dealerCommand.getDealerDTO().getDealerDataBean();
	
				dealerCommand = new AwaitingConfirmationCommand();
				dealerCommand.setCountryCode(countryCode);
				dealerCommand.setDealerCode(dealerCode);
				dealerCommand.getDealerDetails();
				log.debug("for shipping dealer: command.getDealerDTO() = " + dealerCommand.getDealerDTO());
				DealerDataBean sellingDealerBean = dealerCommand.getDealerDTO().getDealerDataBean();
				log.debug("AwaitingConfirmationAction: execute action: shippingLabel: Beans: " + buyingDealerBean + " " + sellingDealerBean);
	
				ProcessUPSWebService prcUPSWS = new ProcessUPSWebService();
	
				// initialize the weight
				String confirmRequest = prcUPSWS.createShipmentConfirmRequest(buyingDealerBean, sellingDealerBean, packageWeight);
				log.debug("Confirm Request: " + confirmRequest);
				try{	
					RequestSender confirmReqSend = new RequestSender(ApplicationUtil.getSystemProperty("APPLICATION_URLS","ups.url.confirm"), confirmRequest);
					confirmresp = confirmReqSend.sendRequest();
					log.debug("Confirm Response: " + confirmresp);
					// roll back if we get error in response code
					errorFromUPS = isErrorResponseFromUPS(confirmresp, "ConfirmResponse");
				}catch(Exception e){
					log.error("An exception occured while posting messages to the queue: " + e.getMessage(), e);
					// roll back for exception from UPS
					errorFromUPS = true;
				}
				if(errorFromUPS){
					rollBackStatutsAndQuantity(backOrderForm.getErrorIdArrayList(), ids, countryCode, dealerCode, wslId,
							boQuantity, shipQuantity, req);
					forwardPage = "label";
				}
				else{
					prcUPSWS.processShipmentConfirmResponse(confirmresp);
					String acceptRequest = prcUPSWS.createShipmentAcceptRequest();
					try{
						RequestSender acceptReqSend = new RequestSender(ApplicationUtil.getSystemProperty("APPLICATION_URLS","ups.url.accept"), acceptRequest );
						acceptresp = acceptReqSend.sendRequest();
						// roll back if we get error in response code
						errorFromUPS = isErrorResponseFromUPS(acceptresp, "AcceptResponse");
					}catch(Exception e){
						log.error("An exception occured while posting messages to the queue: " + e.getMessage(), e);
						// roll back for exception from UPS
						errorFromUPS = true;
					}
					if(errorFromUPS){
						rollBackStatutsAndQuantity(backOrderForm.getErrorIdArrayList(),ids, 
								countryCode, dealerCode, wslId, boQuantity, shipQuantity, req);	
						forwardPage = "label";
					}
					else{
						prcUPSWS.processShipmentAcceptResponse(acceptresp);
						String trackingNumber = prcUPSWS.getTrackingNumber();
						log.debug("AwaitingConfirmationAction: execute action: Tracking Number = " + trackingNumber);
						req.setAttribute("trackingNumber", trackingNumber);
	
						String image = prcUPSWS.getGraphicImage();
						HttpSession session = req.getSession(true);
						session.setAttribute("image", image);
	
						// Post message to MQ
						ArrayList postFailedIds = postMessageToMQ(items, partNumber, shipQuantity, buyingDealerCode, dealerCode, 
								trackingNumber, countryCode);
	
						if(postFailedIds.size() > 0){
							MessageResources resource = this.getResources(req);
							String subject = resource.getMessage("MQ.commit.failed.message");
							Object objArr[] = postFailedIds.toArray();
							String failedIds[] = new String[objArr.length];
							for(int l = 0; l < objArr.length; l++){
								failedIds[l] = (String)objArr[l];
							}
							sendMailToModertor(failedIds, subject);					
						} else {
							// Added to fix Bug id: 34
							// send email to the buying dealer about the shipment
							sendMailToDealers(req, buyingDealerCode, dealerCode, ids, partNumber, shipQuantity, trackingNumber);
						}
					}
					forwardPage = "label";
				}
			}

		} else if("shippingLabel".equals(action)) {
			HttpSession session = req.getSession(true);
			String imageStr = (String) session.getAttribute("image");

			resp.setContentType("image/gif");
			byte[] image = getImageBytes(imageStr);
			resp.setContentLength(image.length);
			resp.getOutputStream().write(image);
			resp.getOutputStream().flush();
			resp.getOutputStream().close();

			// remove the image from the session
			session.setAttribute("image", null);
			return null;

		}else if("cancelShipment".equals(action)){
			forwardPage = "list";
			log.debug("AwaitingConfirmationAction: executeAction: inside cancel shipment");
			backOrderForm.setShowUpdateStatusShip(true);
			String[] ids = req.getParameterValues("id");
			idList = ids[0]; 
			log.debug("AwaitingConfirmationAction: executeAction: idList: " + idList);
			AwaitingConfirmationCommand command = null;			
			if(idList != null) {
				for(int i=0; i<ids.length; i++){
					command = new AwaitingConfirmationCommand(countryCode, dealerCode, ids[i], wslId, "O", "G"); 
					StatusUpdateDTO dto = command.updateStatus();
					log.debug("AwaitingConfirmationAction: executeAction: getErrorIdArrayList = " + command.getErrorIdArrayList());
					log.debug("AdminAction: executeAction: dto = " + dto + ", status = " + dto.getStatusUpdateDataList()/*.getStatus()*/);
					//Modified to fix Bug id: 2
					//if(dto.getMessageOutputDataBean().getStatus().equals("")){
					if(dto.isUpdateSuccess()){
						backOrderForm.setUpdateStatus(true);
						log.debug("Have Updated Successfully" + backOrderForm.isUpdateStatus());
					}
					else
						backOrderForm.setUpdateStatus(false);	
				}
				if(command.getErrorIdArrayList() == null)
					forwardPage = "shippingconfirmation";
				else
					req.setAttribute("errorIdList", command.getErrorIdArrayList().toArray());
			}

		}else if("processMoreShipment".equals(action)) {
			// If we receive any record with B/O quantity zero here, update their statuses to C
			// If we receive any record with Ship quantity zero here, update their statuses to O
			log.debug("ProcessMoreShipment action");
			String[] ids = req.getParameterValues("id");
			log.debug("processMoreShipment: :  ids length: " + ids.length);
			String[] boQuantity = req.getParameterValues("boQuantity");log.debug("boQuantity = " + boQuantity);
			String[] shipQuantity = req.getParameterValues("shipQuantity");log.debug("shipQuantity = " + shipQuantity);

			if(ids != null) {
				log.debug("ids != null");
				// For all records with B/O quantity zero change the status from G to C
				String newStatus = null;			
				for(int z=0; z<ids.length; z++){
					log.debug("integer parsing");
					log.debug("boQuantity[z] = " + boQuantity[z]);
					if(Integer.parseInt(boQuantity[z]) == 0 || (shipQuantity != null && Integer.parseInt(shipQuantity[z]) == 0)) {
						log.debug("inside if");
						if((shipQuantity == null && Integer.parseInt(boQuantity[z]) == 0) || Integer.parseInt(boQuantity[z]) == 0) newStatus = "C";
						else if(Integer.parseInt(shipQuantity[z]) == 0) newStatus = "O";
						backOrderForm.setShowUpdateStatus(true);
						log.debug("processMoreShipment: updating status G -> " + newStatus + " for " + ids[z]);
						AwaitingConfirmationCommand command = new AwaitingConfirmationCommand(countryCode, dealerCode, ids[z], wslId, /*"C"*/newStatus, "G"); 
						StatusUpdateDTO dto = command.updateStatus();
						//Modified to fix Bug id: 2
						//if(dto.getMessageOutputDataBean().getStatus().equals("")){
						if(dto.isUpdateSuccess()){
							backOrderForm.setUpdateStatus(true);
							log.debug("Have Updated Successfully" + backOrderForm.isUpdateStatus());
						}
						else
							backOrderForm.setUpdateStatus(false);
						log.debug("processMoreShipment: Update Success");
					}
					log.debug("outside if");
				}
			}
			forwardPage = "shippingconfirmation";
		}

		log.debug("AwaitingConfirmationAction: executeAction: countryCode = " + countryCode + ", dealerCode = " + dealerCode + ", pageNumber = " + pageNumber +
				", pageSize = " + pageSize + ", sortType = " + sortType);

		// execute the request
		log.debug("AwaitingConfirmationAction: executeAction: Initializing command");
		AwaitingConfirmationCommand command = new AwaitingConfirmationCommand(countryCode, dealerCode, pageNumber, pageSize, sortType);

		if("shippingconfirmation".equals(forwardPage)){
			log.debug("forward page is shippingconfirmation");
			command.setStatusFlag("G");
			log.debug("executing shipping confirmation list");
			command.executeShippingConfirmationList();
			log.debug("command.getAwaitingConfirmationDTO() = " + command.getAwaitingConfirmationDTO() + ", command.getAwaitingConfirmationDTO().getBackOrderDataBeanList() = " + command.getAwaitingConfirmationDTO().getBackOrderDataBeanList().size());
			if(command.getAwaitingConfirmationDTO().getBackOrderDataBeanList() != null && command.getAwaitingConfirmationDTO().getBackOrderDataBeanList().size() > 0) {
				String buyingDealerCode = ((BackOrderDataBean)command.getAwaitingConfirmationDTO().getBackOrderDataBeanList().get(0)).getBuyingDealer().getCode();
				log.debug("[For shipping confirmation page] buyingDealerCode = " + buyingDealerCode);
				AwaitingConfirmationCommand dealerCommand = new AwaitingConfirmationCommand();
				dealerCommand.setCountryCode(countryCode);
				dealerCommand.setDealerCode(buyingDealerCode);
				dealerCommand.getDealerDetails();
				log.debug("command.getDealerDTO() = " + dealerCommand.getDealerDTO());
				backOrderForm.setDealerDetailDTO(dealerCommand.getDealerDTO());
			}			
		}
		else{
			command.setStatusFlag("W");
			command.list();
		}
		log.debug("AwaitingConfirmationAction: executeAction: Executing command");		

		//retrieve the dto and set it in the actionform
		log.debug("AwaitingConfirmationAction: executeAction: calling getAwaitingConfirmationDTO()");
		log.debug("for status flag: " + command.getStatusFlag());
		BackOrderDTO dto = command.getAwaitingConfirmationDTO();
		log.debug("AwaitingConfirmationAction: executeAction: dto = " + dto);
		backOrderForm.setBackOrderDTO(dto);

		//rectify the total page count
		int totalPages = 0;
		if(dto != null) {
			int totalCount = dto.getTotalRecordsCount();
			totalPages = (int)Math.ceil((double)(totalCount)/Double.parseDouble(WebUtils.getPageSize()));
		} else
			totalPages = 0;
		req.setAttribute("totalPages", Integer.toString(totalPages));

		log.debug("AwaitingConfirmationAction: executeAction: forwardPage = " + forwardPage);
		return mapping.findForward(forwardPage);
	}

	private int paginate(HttpServletRequest req, /*AwaitingConfirmationForm*/ BackOrderForm form) {
		// since the actual number of records can change between requests,
		// calculate the next requested page based on what we already know
		// and rectify the total count as retrieved from the proc call.
		// This might result in a minor bug in some situations, but this is
		// the best way we have.
		String pageNumber = req.getParameter("pageNumber")==null?"1":req.getParameter("pageNumber");
		String pageAction = req.getParameter("pageAction");
		String totalCount = req.getParameter("totalCount");
		int totalPages = (int)Math.ceil(Double.parseDouble(totalCount)/Double.parseDouble(WebUtils.getPageSize()));
		log.debug("AwaitingConfirmationAction : paginate : page calc: " + (Double.parseDouble(totalCount)/Double.parseDouble(WebUtils.getPageSize())));

		int nPageNumber = 1;
		try {
			nPageNumber = Integer.parseInt(pageNumber);
		} catch(Exception e) {
			nPageNumber = 1;
		}
		int targetPage = nPageNumber;			
		log.debug("AwaitingConfirmationAction : paginate : totalPages = " + totalPages);

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
		log.debug("AwaitingConfirmationAction : paginate : targetPage = " + targetPage);
		pageNumber = Integer.toString(targetPage);
		req.setAttribute("pageNumber", pageNumber);

		log.debug("AwaitingConfirmationAction : paginate : setting idlist values");
		// set the selected id values
		String idList = req.getParameter("idList");
		log.debug("AwaitingConfirmationAction : paginate : idList = " + idList);
		/*ArrayList idArrayList = new ArrayList();
		if(idList != null && idList.trim().length()!= 0) {
			String[] idArray = idList.split(",");
			log.debug("AwaitingConfirmationAction : paginate : idArray = " + idArray + ", length = " + idArray.length);
			try {
				idArrayList = new ArrayList(Arrays.asList(idArray));
			} catch(Exception e) {
				e.printStackTrace();
			}
			log.debug("AwaitingConfirmationAction : paginate : idArrayList = " + idArrayList);
			req.setAttribute("idList", idList);

			backOrderForm.setIdArrayList(idArrayList);
		}
		else
			idList = "";*/
		ArrayList idArrayList = getIdArrayList(idList);
		idList = (idList == null && idList.trim().length() == 0)? "": idList;
		req.setAttribute("idList", idList);
		backOrderForm.setIdArrayList(idArrayList);
		log.debug("AwaitingConfirmationAction : paginate :  idList set successfully");

		return targetPage;
	}

	private ArrayList calculateTotalWeightAndPrice(String countryCode, String dealerCode, String[] ids, String[] shipQuantity, String[] boQuantity, ItemAttributeDataBean bean) throws Exception{
		log.debug("AwaitingConfirmationAction: calculateTotalWeightAndPrice: entered. country=" + countryCode + ", dealer=" + dealerCode + ",ids=" + ids + ", shipQuantity=" + shipQuantity + ",bean=" + bean);
		double totalWeight = 0;
		double totalPrice = 0;
		ArrayList items = null;
		try {
			// get the sales order and line number from ids array
			// and call stored proc SB60014 to get the total weight of the items
			for(int z=0; z<ids.length; z++) {
				if(shipQuantity[z].trim().equals("0") || boQuantity[z].trim().equals("0")) {
					log.debug("Skipping weight calculation for " + ids[z] + " because shipQuantity = " + shipQuantity[z] + " and boQuantity = " + boQuantity[z]);
					if(items == null) items = new ArrayList();
					items.add(null);
					continue;
				}
				// ensure that items is null if no elements are iterated over
				if(items == null) items = new ArrayList();

				log.debug("AwaitingConfirmationAction: calculateTotalWeightAndPrice: ids[" + z + "] = " + ids[z]);
				String[] item = ids[z].split("\\|");
				ShippingConfirmationCommand shipCommand = new ShippingConfirmationCommand(countryCode, dealerCode);
				shipCommand.setSalesOrder(item[0]);
				shipCommand.setLineNumber(item[1]);
				shipCommand./*setBuyingDealerCode*/setDealerCode(dealerCode);
				log.debug("AwaitingConfirmationAction: calculateTotalWeightAndPrice: calling ShippingConfirmationCommand");
				shipCommand.execute();
				log.debug("AwaitingConfirmationAction: calculateTotalWeightAndPrice: ShippingConfirmationCommand completed");
				ItemAttributeDataBean boItem = shipCommand.getItemAttributeDTO().getItemAttributeDataBean();
				log.debug("AwaitingConfirmationAction: calculateTotalWeightAndPrice: available quantity = " + boItem.getQuantity());
				log.debug("AwaitingConfirmationAction: calculateTotalWeightAndPrice: shipQuantity[" + z + "] = " + shipQuantity[z]);
				items.add(boItem);
				int quantity = Integer.parseInt(shipQuantity[z]);
				log.debug(z + ". AwaitingConfirmationAction: calculateTotalWeightAndPrice: Calculating weight");
				totalWeight += (boItem.getWeight() * quantity);
				log.debug(z + ". AwaitingConfirmationAction: calculateTotalWeightAndPrice: totalWeight = " + totalWeight);
				log.debug(z + ". AwaitingConfirmationAction: calculateTotalWeightAndPrice: Calculating price");
				totalPrice += ((boItem.getOuBasePrice() - boItem.getPromoUnitDiscount()) * quantity);
				log.debug(z + ". AwaitingConfirmationAction: calculateTotalWeightAndPrice: totalPrice = " + totalPrice);
			}
			bean.setWeight(totalWeight);
			bean.setOuBasePrice(totalPrice);
		} catch (Exception e) {
			log.debug("An error occured while calculating package weight and price");
			e.printStackTrace();
			throw e;
		}
		return items;
	}

	public byte[] getImageBytes(String encodedString) throws Exception {
		byte []image = null;
		image = Base64.decode(encodedString);
		return image;
	}

	private boolean sendMQMessage(String mqName, String message, boolean expectReply) throws Exception{
		log.debug("posting to MQ: " + mqName + ", message = " + message);

		// variable success will contain true only if a success response was received.
		// its value will be false if the response was not a success one or 
		// a timeout was encountered
		final boolean[] success = {false};
		
		try {
			MazdaCommonJMSService service = EmazdaJMSServiceFactory.createEmazdaJMSService(mqName);
			log.debug("MQ service = " + service);
			if (service != null) {
				if(expectReply)
					service.setTimeout(Integer.parseInt(ApplicationUtil.getSystemProperty("DPAL", "mq.timeout")));
				else 
					service.setTimeout(0);
				String msgId = service.send(message);
				final boolean[] complete = { false };

				/**
				 * Receive message
				 */
				if(expectReply) {
					service.receive(msgId, new EmazdaJMSMessageListener() {
						public boolean isComplete() {
							log.debug("MQ isComplete called");
							return complete[0];
						}
	
						public void processMessage(Object msgObj) {
							log.debug("MQ processMessage: " + msgObj);
							if (msgObj == null)
								complete[0] = true;
							else {
								if (msgObj instanceof String) {
									try {
										//Process the message here;
										//set complete[0] = true if the trailer or
										// end of message logic is satisfied.
										String msg = (String)msgObj;
										msg = msg.trim();
										
										if(msg.startsWith("DETAIL") && msg.indexOf("BACK ORDER CANCELLED") != -1) {
											// the back order was cancelled successfully
											success[0] = true;
										}
										if(((String)msgObj).startsWith("TRAILER")) {
											complete[0] = true;
										}
									} catch (Exception e) {
										throw new RuntimeException("Exception encountered when routing the message through the reply handler", e);
									}
								} else
									throw new UnsupportedOperationException(
											"The current MQ framework only work with text msg."
											+ "The msg received is typed: "
											+ msgObj.getClass().getName());
							}
						}
					});
					
					log.debug("MQ messages have been processed: success = " + success[0] + ", complete = " + complete[0]);
				}
			}
		} catch (Exception e) {
			log.error("Exception in executeAction:" + e);
			throw e;
		}
		return success[0];
	}

	private ArrayList postMessageToMQ(ArrayList items, String[] partNumber, String[] shipQuantity, String buyingDealer, String sellingDealer, String trackingNumber, String countryCode) throws Exception{
		int z=0;
		log.debug("postMessageToMQ: Creating messages for DPSH and PDS");
		
		
		BOCancelMQMessageDataBean pdsMsgBean =  null; //new BOCancelMQMessageDataBean();
		
		
		ArrayList postSuccessIds = new ArrayList();
		
		ArrayList P243Message = new ArrayList();
		try {
			for(Iterator it = items.iterator(); it.hasNext(); z++) {
				ItemAttributeDataBean item = (ItemAttributeDataBean)it.next();
				if(item == null) {
					P243Message.add(null);
					continue;
				}
				// Create MQ for create credit/debit memo
				MQMessageDataBean bean = new MQMessageDataBean();

				bean.setBoQuantity(Double.toString(item.getQuantity()));
				bean.setBuyingDealerCode(buyingDealer);
				bean.setCountryCode(countryCode);
				bean.setItemNo(partNumber[z]);
				bean.setLineNo(item.getLineNumber());
				bean.setPromoDiscount(Double.toString(item.getPromoUnitDiscount()));
				bean.setSalesOrderNo(item.getSalesOrder());
				bean.setSellingDealerCode(sellingDealer);
				bean.setShippedQuantity(shipQuantity[z]);
				bean.setTrackingNo(trackingNumber);
				bean.setUnitPrice(Double.toString(item.getOuBasePrice()));

				log.debug("MQ message(DPSH): " + bean.toString());
				sendMQMessage("PART6", bean.toString(), false);

				// Create MQ message to update order in PDS
				pdsMsgBean = new BOCancelMQMessageDataBean();
				//pdsMsgBean.setDealerCode(buyingDealer);
				pdsMsgBean.setPartNumber(partNumber[z]);
				// Modified to fix Bug id:37
				//pdsMsgBean.setQuantity(Double.toString(item.getQuantity()));
				pdsMsgBean.setQuantity(shipQuantity[z]);
				pdsMsgBean.setSalesOrderLineNumber(item.getLineNumber());
				pdsMsgBean.setSalesOrderNumber(item.getSalesOrder());
				pdsMsgBean.setSequenceNumber(z+1);

				log.debug("MQ message(PDS): " + pdsMsgBean.toString());
				StringBuffer pdsMQMessage = new StringBuffer();
				pdsMQMessage.append(BOCancelMQMessageDataBean.TRANSACTION_ID).append(WebUtils.padString(buyingDealer, 5));
				pdsMQMessage.append(pdsMsgBean.toString());
				pdsMQMessage.append("          9999                         ");
				// Add the message to the list
				P243Message.add(pdsMQMessage.toString());
				
				postSuccessIds.add(item.getSalesOrder() + "|" + item.getLineNumber());				
			}
		} catch(Exception e) {
			log.error("An exception occured while posting messages to the queue: " + e.getMessage(), e);
		}

		// send the P243 messages
		for(int x=0; x< P243Message.size(); x++) {
			try{
				// post the PDS message
				log.debug("posting PDS msg: " + P243Message.get(x));
				boolean success = false;
				if(P243Message.get(x) != null) {
					success = sendMQMessage("PART1_6", ((String)P243Message.get(x)), true);
					if(!success) {
						ItemAttributeDataBean iadbean = (ItemAttributeDataBean)items.get(x);
						if(iadbean != null)
							this.sendMailToModertor(new String[]{iadbean.getSalesOrder() + "|" + iadbean.getLineNumber()}, "Possible exception while processing P243 transaction");
					}
				}
			}catch(Exception e){
				log.error("An exception occured while posting messages to the queue: " + e.getMessage(), e);
				postSuccessIds = new ArrayList();
			}
		}

		ArrayList postFailedIds = new ArrayList();
		if(postSuccessIds.size()!=items.size()){
			for(int i=0; i< items.size(); i++){
				ItemAttributeDataBean item = (ItemAttributeDataBean)items.get(i);
				if(item == null) continue;
				boolean pass = false;
				for(int j = 0; j< postSuccessIds.size(); j++ ){
					if((item.getSalesOrder()+"|"+item.getLineNumber()).equals(postSuccessIds.get(j).toString())){
						pass = true;
						break;
					}
				}
				if(!pass)
					postFailedIds.add(item.getSalesOrder()+"|"+item.getLineNumber());
			}
		}
		return postFailedIds;
	}

	/* RollBack Orders from Status P --> W */
	private void rollBackStatutsAndQuantity(ArrayList getErrorIdArrayList, String[] ids,
			String countryCode,String dealerCode,String wslId,
			String[] boQuantity,String[] shipQuantity, HttpServletRequest req) throws Exception	{	
		log.debug("Rollback plan activated");
		
		log.debug("Rollback: getErrorIdArrayList = " + getErrorIdArrayList);
		log.debug("countryCode = " + countryCode + ", dealerCode = " + dealerCode + ", wslId = " + wslId);
		
		log.debug("ids = " + ids + ", length = " + (ids!=null?ids.length:0));
		if(ids != null){ 
			for(int z=0; z<ids.length; z++)
				log.debug("ids[" + z + "] = " + ids[z]);
		}
		log.debug("boQuantity = " + boQuantity + ", length = " + (boQuantity!=null?boQuantity.length:0));
		if(boQuantity != null){ 
			for(int z=0; z<boQuantity.length; z++)
				log.debug("boQuantity[" + z + "] = " + boQuantity[z]);
		}
		log.debug("shipQuantity = " + shipQuantity + ", length = " + (shipQuantity!=null?shipQuantity.length:0));
		if(shipQuantity != null){ 
			for(int z=0; z<shipQuantity.length; z++)
				log.debug("shipQuantity[" + z + "] = " + shipQuantity[z]);
		}
		
		boolean success = true;
		boolean rollbackSelectedRecord = true;
		ArrayList failedList = new ArrayList();

		for(int i=0; i< ids.length; i++){
			log.debug("rollback iteration [" + i + "]");
			rollbackSelectedRecord = true;
			if(getErrorIdArrayList!=null){
				for(int j = 0; j< getErrorIdArrayList.size(); j++ ){
					String value = (String) getErrorIdArrayList.get(j);
					if(value.equalsIgnoreCase(ids[i])){
						rollbackSelectedRecord = false;
						break;
					}
				}
			}
			log.debug("rollbackSelectedRecord = " + rollbackSelectedRecord);
			if(rollbackSelectedRecord){
				log.debug("rollback: (Integer.parseInt(boQuantity[i]) == 0 ) = " + (Integer.parseInt(boQuantity[i]) == 0));
				log.debug("rollback: (Integer.parseInt(shipQuantity[i]) == 0) = " + (Integer.parseInt(shipQuantity[i]) == 0));
				AwaitingConfirmationCommand command = null;
				if(Integer.parseInt(boQuantity[i]) == 0 ) {	
					log.debug("rollback: inside condition (Integer.parseInt(boQuantity[i]) == 0 )");
					command = new AwaitingConfirmationCommand(countryCode, dealerCode,
							ids[i], wslId, "C", "C", Double.toString(0));

				}
				else if(Integer.parseInt(shipQuantity[i]) == 0) {
					log.debug("rollback: inside condition (Integer.parseInt(shipQuantity[i]) == 0)");
					command = new AwaitingConfirmationCommand(countryCode, dealerCode,
							ids[i], wslId, "O", "O", Double.toString(0));
				}
				else{
					log.debug("rollback: inside else");
					//shippedQuantity = shippedQuantity*(-1);			
					//TODO: This needs to be modified as we are passing value as zero
					double shippedQuantity = new Double(shipQuantity[i]).doubleValue();
					command = new AwaitingConfirmationCommand(countryCode, dealerCode,
							ids[i], wslId, "W", "W", Double.toString(new Double(shippedQuantity).doubleValue()*-1));

				}
				log.debug("flag 1 = " + command.getPreviousStatusFlag() + ", flag 2 = " + command.getStatusFlag());
				StatusUpdateDTO dto = command.updateStatusAndQuantity();

				if(!dto.isUpdateSuccess()){
					success = false;
					failedList.add(ids[i]);					
				}				
			}			
		}	

		if (success)
			req.setAttribute("rollbackSuccess", "1");
		else{
			req.setAttribute("rollbackSuccess", "0");
			MessageResources resource = this.getResources(req);
			String subject = resource.getMessage("rollback.failed.message");
			String failedIds[] = new String[failedList.toArray().length];
			for(int l = 0; l < failedList.toArray().length; l++){
				failedIds[l] = (String)failedList.toArray()[l];
			}
			sendMailToModertor(failedIds, subject);		
		}
	}

	/* If UPS API give error in response*/
	private boolean isErrorResponseFromUPS(String reponseXML, String resp ) throws Exception{
		String shipStatusCode = null;
		Document doc = null;
		boolean success = false;
		String argXpath = null;
		//convert string to XML
		try {	
			if(resp.equals("ConfirmResponse")){
				argXpath = "ShipmentConfirmResponse/Response/ResponseStatusCode";
			}
			else if(resp.equals("AcceptResponse")){
				argXpath = "ShipmentAcceptResponse/Response/ResponseStatusCode";
			}
			DocumentBuilderFactory domFactory = DocumentBuilderFactory .newInstance();
			DocumentBuilder builder = domFactory.newDocumentBuilder();

			StringReader reader = new StringReader(reponseXML);
			InputSource inputSource = new InputSource( reader );

			doc = builder.parse(inputSource);
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile(argXpath);

			Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
			NodeList nodelist = node.getChildNodes();

			for(int i = 0; i < nodelist.getLength(); i++){
				Node childNode = nodelist.item(i);
				if(childNode.getNodeType() == Node.TEXT_NODE)
					shipStatusCode = childNode.getNodeValue();
			}
			reader.close();
			//in the XML, check for the node errorXML or equivalent logic to check if the response was a success.
			if(shipStatusCode.equals("0"))
				//if this node is present, return true else return false
				success = true;	

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return success;
	}

	/* Added for sending mails on failure of MQ message */
	private void sendMailToModertor(String[] ids, String subject){
		log.debug("Sending mail to moderator");
		try{
			String[] moderatorEmailID = ApplicationUtil.getSystemProperty("DPAL", "moderator.email.id").split(",");
			String fromEmailID = ApplicationUtil.getSystemProperty("DPAL", "from.email.id");

			String message = "";
			for(int i = 0; i< ids.length; i++){
				String[] id = ids[i].split("\\|");
				message = message + "Sales Order Number: " + id[0] + " Line Order Number: " + id[1] + "\n";				 
			}
			EMazdamailsender mail = new EMazdamailsender();
			mail.SendMazdaMail(fromEmailID, moderatorEmailID, null, null, subject, message);
		}catch(Exception e){
			e.printStackTrace();			
		}
	}	
	
	// Begin: Added to fix Bug id: 34
	private void sendMailToDealers(HttpServletRequest req, String buyingDealerCode, String sellingDealerCode, String[] ids, String[] partNumber, String[] quantity, String trackingNumber) throws Exception{
		// Buying dealer information
		DealerDetailDAO dao = null;
		DealerDetailDTO dto = null;
		try{
			DpalDAOFactory factory = new DpalDAOFactory(AdminAction.class.getName());
			factory.init("DPAL_FACTORY");
			dao = (DealerDetailDAO)factory.lookupDAO(DealerDetailDAO.class);
			dto = dao.findDTO(WebUtils.getCountryCode(req), buyingDealerCode, DealerDetailDAOImpl.ADMIN_DETAILS);
		} catch(Exception e) {
			log.error("An exception occurred while retrieving dealer details.", e);
		}
		DealerDataBean bean = dto.getDealerDataBean();
		String[] buyingDealers = bean.getBuyingDealer();
		
		// Selling dealer information
		DealerDetailDAO buyingDealerDAO = null;
		DealerDetailDTO buyingDealerDTO = null;
		try{
			DpalDAOFactory factory = new DpalDAOFactory(AdminAction.class.getName());
			factory.init("DPAL_FACTORY");
			buyingDealerDAO = (DealerDetailDAO)factory.lookupDAO(DealerDetailDAO.class);
			buyingDealerDTO = buyingDealerDAO.findDTO(WebUtils.getCountryCode(req), sellingDealerCode, DealerDetailDAOImpl.SHIPPING_DETAILS);
		} catch(Exception e) {
			log.error("An exception occurred while retrieving dealer details.", e);
		}
		DealerDataBean sellingDealerBean = buyingDealerDTO.getDealerDataBean();
		
		// debug values
		boolean isShippingDebugEnabled = new Boolean(ApplicationUtil.getSystemProperty("DPAL", "ship.debug")).booleanValue();
		String debugEmailId = ApplicationUtil.getSystemProperty("DPAL", "ship.debug.emailid");
		
		String from = ApplicationUtil.getSystemProperty("DPAL", "ship.mail.from");
		String subject = ApplicationUtil.getSystemProperty("DPAL", "ship.mail.subject");
		
		// compose the mail body
		StringBuffer body = new StringBuffer(ApplicationUtil.getSystemProperty("DPAL", "ship.mail.body"));
		int index = 1;
		for(int z=0; z<partNumber.length; z++) {
			if(quantity[z].equals("0")) continue;
			body.append(index++)
				.append(". ")
				.append(partNumber[z])
				.append(" : ")
				.append(quantity[z])
				.append("pc(s)")
				.append(",\t Sales Order: ")
				.append(ids[z].split("\\|")[0])
				.append(",\t Line Number: ")
				.append(ids[z].split("\\|")[1])
				.append("\n"); 
		}
		body.append(ApplicationUtil.getSystemProperty("DPAL", "ship.mail.body.dealerinfo"))
			.append(sellingDealerCode).append(", ")
			.append(sellingDealerBean.getName().trim()).append(", ")
			//.append(sellingDealerBean.getAddressLines().trim()).append(", ")
			.append(sellingDealerBean.getCity().trim()).append(", ")
			.append(sellingDealerBean.getState().trim()).append(".");
		
		body.append(ApplicationUtil.getSystemProperty("DPAL", "ship.mail.body.tracking"))
			.append(trackingNumber)
			.append(".")
			.append(ApplicationUtil.getSystemProperty("DPAL", "ship.mail.body.conclusion"))
			.append("\n\n");
		// end of composition
		
		EMazdamailsender mail = new EMazdamailsender();
		String emailId = null;
		boolean wasMailSent = false;
		if(buyingDealers != null) {
			for(int z=0; z<buyingDealers.length; z++) {
				if(isShippingDebugEnabled) {
					log.debug("ship.debug is enabled. Sending mail to debug id. Mail could have been sent to: " + WebUtils.getEmailByWSLId(buyingDealers[z]));
					emailId = debugEmailId; 
				} else {
					log.debug("ship.debug is disabled. Sending mail to actual dealer");
					emailId = WebUtils.getEmailByWSLId(buyingDealers[z]);
					//emailId = debugEmailId;
				}
				if(WebUtils.isValidEmail(emailId)) {
					mail.SendMazdaMail(from, emailId, subject, body.toString());
					wasMailSent = true;
				}
				else {
					log.debug("mail not sent to " + emailId);
				}
			}				
		} else {
			log.debug("Email was not sent to the buying dealer " + buyingDealerCode + " because it did not have any email ids.");
		}
		if(!wasMailSent) {
			log.debug("Email was not sent to the buying dealer " + buyingDealerCode + " because it did not have any valid email ids.");
		}
	}
	// End changes
	
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
