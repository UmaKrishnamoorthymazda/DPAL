/*
 * Created on Dec 16, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import com.mazdausa.dealer.parts.dpal.persistence.dto.StatusUpdateDTO;
import com.mazdausa.dealer.parts.dpal.services.DPALDAOException;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface StatusUpdateDAO {
	/*
	 * Normally, this method is used to call stored procedure SB60008 to update the status flag 
	 * of the tables DB2DPBO and DB2DPDR to the given statusFlag. The previousStatusFlag is used
	 * to verify if the existing status of the records is equal to it, before modifying it to 
	 * statusFlag.	 
	 */
	StatusUpdateDTO findDTO(String countryCode, String dealerCode,
			String idList, String wslId, String statusFlag, String previousStatusFlag) throws DPALDAOException;
	
	StatusUpdateDTO findDTO(String countryCode, String dealerCode, String idList, String wslId, String dpboStatusFlag,
			String dpdrStatusFlag, String shippedQuantity) throws DPALDAOException;
}
