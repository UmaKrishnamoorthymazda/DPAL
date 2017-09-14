/*
 * Created on Dec 14, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.services;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.dealer.parts.dpal.applications.databeans.BackOrderDataBean;
import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;
import com.mazdausa.dealer.parts.dpal.persistence.BackOrderDAO;
import com.mazdausa.dealer.parts.dpal.persistence.DealerDetailDAO;
import com.mazdausa.dealer.parts.dpal.persistence.DealerDetailDAOImpl;
import com.mazdausa.dealer.parts.dpal.persistence.DpalDAOFactory;
import com.mazdausa.dealer.parts.dpal.persistence.StatusUpdateDAO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.BackOrderDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.BackOrderDTOImpl;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.StatusUpdateDTO;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AwaitingConfirmationCommand {
	
	private static Logger log = EMDCSLogger.getLogger(AwaitingConfirmationCommand.class);
	private String countryCode;
	private String dealerCode;
	private String pageNumber;
	private String pageSize;
	private String sortType;
		
	private BackOrderDTO boDTO;
	private StatusUpdateDTO suDTO;
	private DealerDetailDTO dealerDTO;
	
	private String idList;
	private ArrayList errorIdArrayList;
	private String wslId;
	private String statusFlag;
	private String previousStatusFlag;
	
	private String dpboStatusFlag;
	private String dpdrStatusFlag;
	private String shippedQuantity;
	
	/**
	 * @param countryCode
	 * @param dealerCode
	 * @param idList
	 * @param wslId
	 * @param statusFlag
	 * @param previousStatusFlag
	 */
	public AwaitingConfirmationCommand() {
		
	}

	public AwaitingConfirmationCommand(String countryCode, String dealerCode,
			String idList, String wslId, String statusFlag,
			String previousStatusFlag) {
		this.countryCode = countryCode;
		this.dealerCode = dealerCode;
		this.idList = idList;
		this.wslId = wslId;
		this.statusFlag = statusFlag;
		this.previousStatusFlag = previousStatusFlag;
	}
	/**
	 * @param countryCode
	 * @param dealerCode
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 */
	 
	 public AwaitingConfirmationCommand(String countryCode, String dealerCode,
			String pageNumber, String pageSize, String sortType) {
		super();
		this.countryCode = countryCode;
		this.dealerCode = dealerCode;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.sortType = sortType;
	}	
	
	/**
	 * @param countryCode
	 * @param dealerCode
	 * @param wslId
	 */
	public AwaitingConfirmationCommand(String countryCode, String dealerCode,
			String wslId) {
		this.countryCode = countryCode;
		this.dealerCode = dealerCode;
		this.wslId = wslId;
	}
	
	public AwaitingConfirmationCommand(String countryCode, String dealerCode,
			String idList, String wslId, String dpboStatusFlag,
			String dpdrStatusFlag, String shippedQuantity) {
		this.countryCode = countryCode;
		this.dealerCode = dealerCode;
		this.idList = idList;
		this.wslId = wslId;
		this.dpboStatusFlag = dpboStatusFlag;
		this.dpdrStatusFlag = dpdrStatusFlag;
		this.shippedQuantity = shippedQuantity;		
	}
	
	public void list() throws Exception{
		log.debug("inside list(): " + "countryCode = " + countryCode + "dealerCode = " + dealerCode + ", pageNumber = " + pageNumber + ", pageSize = " + pageSize + ", sortType = " + sortType);
		BackOrderDAO dao = null;
		BackOrderDTO dto = null;
		try {
			log.debug("AwaitingConfirmationCommand : execute : getting factory"); 
			DpalDAOFactory factory = new DpalDAOFactory(AwaitingConfirmationCommand.class.getName());
			log.debug("AwaitingConfirmationCommand : execute : init factory");
			factory.init("DPAL_FACTORY");
			log.debug("AwaitingConfirmationCommand : execute : lookup DAO");			
			dao = (BackOrderDAO)factory.lookupDAO(BackOrderDAO.class);
			log.debug("AwaitingConfirmationCommand : execute : find DTO");
			dto = dao.findDTO(countryCode, dealerCode, "W", pageNumber, pageSize, sortType);
			this.boDTO = dto;
		} catch(Exception e) {
			throw e;
		}
	}
	
	public StatusUpdateDTO updateStatus() throws Exception {
		StatusUpdateDAO dao = null;
		StatusUpdateDTO dto = null;
		log.debug("AwaitingConfirmationCommand : ship : acquired idlist = " + idList);
		try {
			DpalDAOFactory factory = new DpalDAOFactory(AwaitingConfirmationCommand.class.getName());
			factory.init("DPAL_FACTORY");
			dao = (StatusUpdateDAO)factory.lookupDAO(StatusUpdateDAO.class);
			dto = dao.findDTO(countryCode, dealerCode, idList, wslId, statusFlag, previousStatusFlag);
			this.suDTO = dto;
		} catch(Exception e) {
			throw e;
		}
		
		return dto;
	}
	
	public StatusUpdateDTO updateStatusAndQuantity() throws Exception {
		StatusUpdateDAO dao = null;
		StatusUpdateDTO dto = null;
		log.debug("AwaitingConfirmationCommand : ship : acquired idlist = " + idList);
		try {
			DpalDAOFactory factory = new DpalDAOFactory(AwaitingConfirmationCommand.class.getName());
			factory.init("DPAL_FACTORY");
			dao = (StatusUpdateDAO)factory.lookupDAO(StatusUpdateDAO.class);
			
			dto = dao.findDTO(countryCode, dealerCode, idList, wslId, dpboStatusFlag, dpdrStatusFlag, shippedQuantity);
			this.suDTO = dto;
		} catch(Exception e) {
			throw e;
		}
		
	return dto;	
	}
	
	
	public void executeShippingConfirmationList() throws Exception {
		log.debug("AwaitingConfirmationCommand : executeShippingConfirmationList : Entering");
		BackOrderDAO dao = null;
		BackOrderDTO dto = null;
		BackOrderDTO newDTO = new BackOrderDTOImpl();
		
		String pageSize = WebUtils.getShippingConfirmationPageSize();//ApplicationUtil.getSystemProperty("DPAL","shipping.page.size");
		String sortType = "BS";
		String pageNumber = "1";
		
		try {
			DpalDAOFactory factory = new DpalDAOFactory(AwaitingConfirmationCommand.class.getName());
			factory.init("DPAL_FACTORY");
			dao = (BackOrderDAO)factory.lookupDAO(BackOrderDAO.class);
			dto = dao.findDTO(countryCode, dealerCode, statusFlag, pageNumber, pageSize, sortType );
			
			BackOrderDataBean bodb = null;
			String dealerCode = null;
			String address = null;
			
			//get the first buying dealer
			if(dto != null && dto.getTotalRecordsCount() > 0){
				bodb = (BackOrderDataBean)dto.getBackOrderDataBeanList().get(0);
				dealerCode = bodb.getBuyingDealer().getCode();
				address = bodb.getBuyingDealer().getFullAddress();
				newDTO.addBackOrderDataBean((BackOrderDataBean)dto.getBackOrderDataBeanList().get(0));
			}
			
			//select the dealers with common address assuming they are sorted by dealer code followed by address
			if(dto != null)
				for(int i = 1; i < dto.getTotalRecordsCount(); i++){
					bodb = (BackOrderDataBean)dto.getBackOrderDataBeanList().get(i);
					String nextDealerCode = bodb.getBuyingDealer().getCode();
					String nextAddress = bodb.getBuyingDealer().getFullAddress();
					if(nextDealerCode.equalsIgnoreCase(dealerCode) && nextAddress.equalsIgnoreCase(address)){
						newDTO.addBackOrderDataBean((BackOrderDataBean)dto.getBackOrderDataBeanList().get(i));
					}
				}
			
			log.debug("AwaitingConfirmationCommand : executeShippingConfirmationList : dto = " + dto);
			this.boDTO = newDTO;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void getDealerDetails() {
		DealerDetailDAO dao = null;
		DealerDetailDTO dto = null;
		
		// trying to set the buying dealer code to display contact name and number
		try{
			DpalDAOFactory factory = new DpalDAOFactory(AwaitingConfirmationCommand.class.getName());
			factory.init("DPAL_FACTORY");
			dao = (DealerDetailDAO)factory.lookupDAO(DealerDetailDAO.class);
			log.debug("calling SB60017 with params: countryCode = " + countryCode + ", dealerCode = " + dealerCode);
			dto = dao.findDTO(countryCode, dealerCode, DealerDetailDAOImpl.SHIPPING_DETAILS);
		} catch(Exception e) {
			log.error("An exception occurred while retrieved dealer details.", e);
		}
		log.debug("Contact Name = " + dto.getDealerDataBean().getContactName());	
		log.debug("Contact Number = " + dto.getDealerDataBean().getContactNumber());
		//req.setAttribute("Contact Name", ddto.getDealerDataBean().getContactName());
		//req.setAttribute("Contact Number", ddto.getDealerDataBean().getContactNumber());

		//log.debug("AwaitingConfirmationAction: executeAction: idList = " + idList);
		//log.debug("AwaitingConfirmationAction: executeAction: Action = " + action);
		
		this.dealerDTO = dto;
	}
	

	/**
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * @param countryCode The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	/**
	 * @return Returns the dealerCode.
	 */
	public String getDealerCode() {
		return dealerCode;
	}
	/**
	 * @param dealerCode The dealerCode to set.
	 */
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	/**
	 * @return Returns the pageNumber.
	 */
	public String getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * @return Returns the pageSize.
	 */
	public String getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize The pageSize to set.
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return Returns the sortType.
	 */
	public String getSortType() {
		return sortType;
	}
	/**
	 * @param sortType The sortType to set.
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	public BackOrderDTO getAwaitingConfirmationDTO() {
		return boDTO;
	}
	public StatusUpdateDTO getAwaitingConfirmationShipDTO() {
		return suDTO;
	}
	/**
	 * @param sortType The sortType to set.
	 */
	public void setSortType(/*AwaitingConfirmationDTO*/ BackOrderDTO awDTO) {
		this.boDTO = awDTO;
	}
	/**
	 * @return Returns the idList.
	 */
	public String getIdList() {
		return idList;
	}
	/**
	 * @param idList The idList to set.
	 */
	public void setIdList(String idList) {
		this.idList = idList;
	}
	/**
	 * @return Returns the statusFlag.
	 */
	public String getStatusFlag() {
		return statusFlag;
	}
	/**
	 * @param statusFlag The statusFlag to set.
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	/**
	 * @return Returns the wslId.
	 */
	public String getWslId() {
		return wslId;
	}
	/**
	 * @param wslId The wslId to set.
	 */
	public void setWslId(String wslId) {
		this.wslId = wslId;
	}
	/**
	 * @return Returns the errorIdArrayList.
	 */
	public ArrayList getErrorIdArrayList() {
		return errorIdArrayList;
	}
	/**
	 * @param errorIdArrayList The errorIdArrayList to set.
	 */
	public void setErrorIdArrayList(ArrayList errorIdArrayList) {
		this.errorIdArrayList = errorIdArrayList;
	}
	/**
	 * @return Returns the awsDTO.
	 */
	public StatusUpdateDTO getStatusUpdateDTO() {
		return suDTO;
	}
	/**
	 * @param awsDTO The awsDTO to set.
	 */
	public void setAwsDTO(StatusUpdateDTO awsDTO) {
		this.suDTO = awsDTO;
	}
	/**
	 * @return Returns the previousStatusFlag.
	 */
	public String getPreviousStatusFlag() {
		return previousStatusFlag;
	}
	/**
	 * @param previousStatusFlag The previousStatusFlag to set.
	 */
	public void setPreviousStatusFlag(String previousStatusFlag) {
		this.previousStatusFlag = previousStatusFlag;
	}	
	/**
	 * @return Returns the dealerDTO.
	 */
	public DealerDetailDTO getDealerDTO() {
		return dealerDTO;
	}
	/**
	 * @param dealerDTO The dealerDTO to set.
	 */
	public void setDealerDTO(DealerDetailDTO dealerDTO) {
		this.dealerDTO = dealerDTO;
	}
	/**
	 * @return Returns the dpboStatusFlag.
	 */
	public String getDpboStatusFlag() {
		return dpboStatusFlag;
	}
	/**
	 * @param dpboStatusFlag The dpboStatusFlag to set.
	 */
	public void setDpboStatusFlag(String dpboStatusFlag) {
		this.dpboStatusFlag = dpboStatusFlag;
	}
	/**
	 * @return Returns the dpdrStatusFlag.
	 */
	public String getDpdrStatusFlag() {
		return dpdrStatusFlag;
	}
	/**
	 * @param dpdrStatusFlag The dpdrStatusFlag to set.
	 */
	public void setDpdrStatusFlag(String dpdrStatusFlag) {
		this.dpdrStatusFlag = dpdrStatusFlag;
	}
	/**
	 * @return Returns the shippedQuantity.
	 */
	public String getShippedQuantity() {
		return shippedQuantity;
	}
	/**
	 * @param shippedQuantity The shippedQuantity to set.
	 */
	public void setShippedQuantity(String shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}
}
