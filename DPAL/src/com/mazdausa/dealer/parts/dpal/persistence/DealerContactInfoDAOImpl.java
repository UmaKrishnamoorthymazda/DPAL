package com.mazdausa.dealer.parts.dpal.persistence;

import com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailDTOImpl;
import com.mazdausa.dealer.parts.dpal.services.DPALDAOException;

public class DealerContactInfoDAOImpl extends DealerContactInfoDAOAbstract implements DealerContactInfoDAO{

	public DealerDetailDTO findDTO(String countryCode, String dealerCode, String shippingemail1, String shippingemail2, String shippingemail3, String shippingemail4, String shippingemail5, String buyingemail1, String buyingemail2, String buyingemail3, String buyingemail4, String buyingemail5, String name, String number) throws DPALDAOException{
		DealerDetailDTO dto = new DealerDetailDTOImpl();
		DealerDataBean bean = null;
		
		try {			
			bean = this.callStoredProcedure("SB60016", countryCode, dealerCode, shippingemail1,shippingemail2,shippingemail3,shippingemail4,shippingemail5, buyingemail1, buyingemail2,buyingemail3,buyingemail4,buyingemail5,name, number);
			dto.setDealerDataBean(bean);
		} catch(Exception e) {
			throw new DPALDAOException(e);
		}
		return dto;
	}
}
