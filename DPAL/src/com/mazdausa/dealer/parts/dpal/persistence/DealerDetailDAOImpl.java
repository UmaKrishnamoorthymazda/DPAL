/*
 * Created on Dec 17, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import org.apache.log4j.Logger;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailDTOImpl;
import com.mazdausa.dealer.parts.dpal.services.DPALDAOException;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealerDetailDAOImpl extends DealerDetailDAOAbstract implements DealerDetailDAO{

	private static Logger log = EMDCSLogger.getLogger(DealerDetailDAOImpl.class);
	public static final String CONTACT_DETAILS = "contactDetails"; 
	public static final String ADMIN_DETAILS = "adminDetails";
	public static final String SHIPPING_DETAILS = "shippingDetails";
	public DealerDetailDTO findDTO(String countryCode, String dealerCode, String operation) throws DPALDAOException{
		DealerDetailDTO dto = new DealerDetailDTOImpl();
		DealerDataBean bean = null;
		
		try {			
			if(operation.equalsIgnoreCase(CONTACT_DETAILS))
				bean = this.callStoredProcedure("SB60012", countryCode, dealerCode, operation);
			else if(operation.equalsIgnoreCase(ADMIN_DETAILS))
				bean = this.callStoredProcedure("SB60015", countryCode, dealerCode, operation);
			else if(operation.equalsIgnoreCase(SHIPPING_DETAILS))
				bean = this.callStoredProcedure("SB60017", countryCode, dealerCode, operation);
			
			log.debug("Value of bean " + bean);
			dto.setDealerDataBean(bean);
		} catch(Exception e) {
			throw new DPALDAOException(e);
		}
		return dto;
	}
	
	
}
