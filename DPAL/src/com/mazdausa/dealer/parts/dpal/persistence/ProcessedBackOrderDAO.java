/*
 * Created on Dec 10, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import com.mazdausa.dealer.parts.dpal.persistence.dto.ProcessedBackOrderDTO;
import com.mazdausa.dealer.parts.dpal.services.DPALDAOException;

/**
 * @author InterraIT
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ProcessedBackOrderDAO {
	ProcessedBackOrderDTO findDTO(String countryCode, String dealerCode, String statusFlag, String pageNumber, String pageSize, String sortType) throws DPALDAOException;	
}
