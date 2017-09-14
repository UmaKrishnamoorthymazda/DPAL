/*
 * Created on Dec 20, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import com.mazdausa.dealer.parts.dpal.applications.databeans.ItemAttributeDataBean;
import com.mazdausa.dealer.parts.dpal.persistence.dto.ItemAttributeDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.ItemAttributeDTOImpl;
import com.mazdausa.dealer.parts.dpal.services.DPALDAOException;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ItemAttributeDAOImpl extends ItemAttributeDAOAbstract implements ItemAttributeDAO {
	public ItemAttributeDTO findDTO(String countryCode, String dealerCode, String salesOrder, String lineNumber) throws DPALDAOException {
		ItemAttributeDataBean bean = null;
		ItemAttributeDTO dto = new ItemAttributeDTOImpl();
		
		try {
			bean = this.callStoredProcedure("SB60014", countryCode, dealerCode, salesOrder, lineNumber);
		} catch(Exception e) {
			throw new DPALDAOException(e);
		}
		
		dto.setItemAttributeDataBean(bean);
		return dto;
	}
}
