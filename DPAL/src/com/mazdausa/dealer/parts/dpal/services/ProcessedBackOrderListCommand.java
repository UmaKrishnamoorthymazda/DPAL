/*
 * Created on Dec 13, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.services;

import com.mazdausa.dealer.parts.dpal.persistence.ProcessedBackOrderDAO;
import com.mazdausa.dealer.parts.dpal.persistence.DpalDAOFactory;
import com.mazdausa.dealer.parts.dpal.persistence.dto.ProcessedBackOrderDTO;


/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProcessedBackOrderListCommand {
	
	private String countryCode;
	private String dealerCode;
	private String pageNumber;
	private String pageSize;
	private String sortType;
	private ProcessedBackOrderDTO pboDTO;
	
	/**
	 * @param countryCode
	 * @param dealerCode
	 * @param idList
	 * @param wslId
	 * @param statusFlag
	 * @param previousStatusFlag
	 */
	public ProcessedBackOrderListCommand(String countryCode, String dealerCode, 
			String argStatusFlag, String pageNumber, String pageSize, 
			String sortType) {
		this.countryCode = countryCode;
		this.dealerCode = dealerCode;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.sortType = sortType;		
	}
	
	public void list() throws Exception{
		ProcessedBackOrderDAO dao = null;
		ProcessedBackOrderDTO dto = null;
		try {
			DpalDAOFactory factory = new DpalDAOFactory(BackOrderListCommand.class.getName());
			factory.init("DPAL_FACTORY");
			dao = (ProcessedBackOrderDAO)factory.lookupDAO(ProcessedBackOrderDAO.class);
			
			dto = dao.findDTO(countryCode, dealerCode, "P", pageNumber, pageSize, sortType);
			
			this.pboDTO = dto;
		} catch(Exception e) {
			throw e;
		}
	}
	
	public ProcessedBackOrderDTO getProcessedBackOrderDTO() {
		return pboDTO;
	}	
}
