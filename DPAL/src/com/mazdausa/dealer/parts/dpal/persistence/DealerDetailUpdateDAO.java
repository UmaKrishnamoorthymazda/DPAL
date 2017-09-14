/*
 * Created on Jan 7, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailUpdateDTO;
import com.mazdausa.dealer.parts.dpal.services.DPALDAOException;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface DealerDetailUpdateDAO {
	DealerDetailUpdateDTO findDTO(String countryCode, String buyingDealerCode, String wslId, DealerDataBean bean) throws DPALDAOException;
}
