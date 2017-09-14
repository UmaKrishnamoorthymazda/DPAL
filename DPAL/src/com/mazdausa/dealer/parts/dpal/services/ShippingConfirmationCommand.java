/*
 * Created on Dec 21, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.services;

import org.apache.log4j.Logger;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.dealer.parts.dpal.persistence.DpalDAOFactory;
import com.mazdausa.dealer.parts.dpal.persistence.ItemAttributeDAO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.ItemAttributeDTO;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ShippingConfirmationCommand {
	
	private static Logger log = EMDCSLogger.getLogger(ShippingConfirmationCommand.class);
	private ItemAttributeDTO itemAttributeDTO;
	
	private String countryCode;
	private String dealerCode;
	private String salesOrder;
	private String lineNumber;
	//private String buyingDealerCode;
	
	/**
	 * @param countryCode
	 * @param dealerCode
	 */
	public ShippingConfirmationCommand(String countryCode, String dealerCode) {
		this.countryCode = countryCode;
		this.dealerCode = dealerCode;
	}
	/**
	 * @param countryCode
	 * @param dealerCode
	 * @param salesOrder
	 * @param lineNumber
	 */
	public ShippingConfirmationCommand(String countryCode, String dealerCode,
			String salesOrder, String lineNumber) {
		this.countryCode = countryCode;
		this.dealerCode = dealerCode;
		this.salesOrder = salesOrder;
		this.lineNumber = lineNumber;
	}
	
	public void execute() throws Exception{
		ItemAttributeDAO dao = null;
		ItemAttributeDTO dto = null;
		try {
			log.debug("ShippingConfirmationCommand : execute : getting factory"); 
			DpalDAOFactory factory = new DpalDAOFactory(AwaitingConfirmationCommand.class.getName());
			log.debug("ShippingConfirmationCommand : execute : init factory");
			factory.init("DPAL_FACTORY");
			log.debug("ShippingConfirmationCommand : execute : lookup DAO");
			//dao = (AwaitingConfirmationDAO)factory.lookupDAO(AwaitingConfirmationDAO.class);
			dao = (ItemAttributeDAO)factory.lookupDAO(ItemAttributeDAO.class);
			log.debug("ShippingConfirmationCommand : execute : find DTO: " + /*buyingDealerCode*/ dealerCode);
			log.debug("ShippingConfirmationCommand : execute : Calling stored proc with params: " + "countryCode = " + countryCode + ", dealerCode = " + dealerCode + ", salesOrder = " + salesOrder + ", lineNumber = " + lineNumber);
			dto = dao.findDTO(countryCode, /*buyingDealerCode*/ dealerCode, salesOrder, lineNumber);
			log.debug("ShippingConfirmationCommand : execute : dto = " + dto);
			this.itemAttributeDTO = dto;
		} catch(Exception e) {
			throw e;
		}
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
	 * @return Returns the lineNumber.
	 */
	public String getLineNumber() {
		return lineNumber;
	}
	/**
	 * @param lineNumber The lineNumber to set.
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	/**
	 * @return Returns the salesOrder.
	 */
	public String getSalesOrder() {
		return salesOrder;
	}
	/**
	 * @param salesOrder The salesOrder to set.
	 */
	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}
	/**
	 * @return Returns the itemAttributeDTO.
	 */
	public ItemAttributeDTO getItemAttributeDTO() {
		return itemAttributeDTO;
	}
	/**
	 * @param itemAttributeDTO The itemAttributeDTO to set.
	 */
	public void setItemAttributeDTO(ItemAttributeDTO itemAttributeDTO) {
		this.itemAttributeDTO = itemAttributeDTO;
	}
	
	/*public void setBuyingDealerCode(String buyingDealerCode) {
		this.buyingDealerCode = buyingDealerCode;		
	}
	
	public String getBuyingDealerCode() {
		return buyingDealerCode;		
	}*/
}
