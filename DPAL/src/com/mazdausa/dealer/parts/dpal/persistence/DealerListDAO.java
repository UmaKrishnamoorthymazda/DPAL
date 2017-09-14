/*
 * Created on Jan 8, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerListDTO;
import com.mazdausa.dealer.parts.dpal.services.DPALDAOException;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface DealerListDAO {
	DealerListDTO findDTO(String dealerCode) throws DPALDAOException;
}
