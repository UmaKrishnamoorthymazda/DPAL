/*
 * Created on Dec 13, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.services;

import org.apache.log4j.Logger;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.dealer.parts.dpal.persistence.BackOrderDAO;
import com.mazdausa.dealer.parts.dpal.persistence.DpalDAOFactory;
import com.mazdausa.dealer.parts.dpal.persistence.StatusUpdateDAO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.BackOrderDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.StatusUpdateDTO;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BackOrderListCommand {
	
	private static Logger log = EMDCSLogger.getLogger(BackOrderListCommand.class);
	private String countryCode;
	private String dealerCode;
	private String pageNumber;
	private String pageSize;
	private String sortType;
	private BackOrderDTO boDTO;
	private StatusUpdateDTO statusDTO;
	
	private String idList;
	private String wslId;
	private String statusFlag;
	private String previousStatusFlag;
	
	/**
	 * @param countryCode
	 * @param dealerCode
	 * @param idList
	 * @param wslId
	 * @param statusFlag
	 * @param previousStatusFlag
	 */
	public BackOrderListCommand(String countryCode, String dealerCode,
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
	public BackOrderListCommand(String countryCode, String dealerCode,
			String pageNumber, String pageSize, String sortType) {
		super();
		this.countryCode = countryCode;
		this.dealerCode = dealerCode;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.sortType = sortType;
	}
	
	public void list() throws Exception{
		BackOrderDAO dao = null;
		BackOrderDTO dto = null;
		try {
			DpalDAOFactory factory = new DpalDAOFactory(BackOrderListCommand.class.getName());
			factory.init("DPAL_FACTORY");
			dao = (BackOrderDAO)factory.lookupDAO(BackOrderDAO.class);
			
			dto = dao.findDTO(countryCode, dealerCode, "O", pageNumber, pageSize, sortType);
			
			this.boDTO = dto;
		} catch(Exception e) {
			throw e;
		}
	}
	
	public StatusUpdateDTO commit() throws Exception {
		StatusUpdateDAO dao = null;
		StatusUpdateDTO dto = null;
		log.debug("acquired idlist = " + idList);
		try {
			log.debug("BackOrderListCommand : commit : getting factory");
			DpalDAOFactory factory = new DpalDAOFactory(BackOrderListCommand.class.getName());
			log.debug("BackOrderListCommand : commit : init factory");
			factory.init("DPAL_FACTORY");
			log.debug("BackOrderListCommand : commit : lookup dao");
			dao = (StatusUpdateDAO)factory.lookupDAO(StatusUpdateDAO.class);
			
			dto = dao.findDTO(countryCode, dealerCode, idList, wslId, statusFlag, previousStatusFlag);
			log.debug("BackOrderListCommand : commit : [commit]: Back from findDTO");
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		return dto;
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
	
	public BackOrderDTO getBackOrderDTO() {
		return boDTO;
	}
	
	public StatusUpdateDTO getBackOrderCommitDTO() {
		return statusDTO;
	}
	
	/**
	 * @param sortType The sortType to set.
	 */
	public void setSortType(BackOrderDTO boDTO) {
		this.boDTO = boDTO;
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
}
