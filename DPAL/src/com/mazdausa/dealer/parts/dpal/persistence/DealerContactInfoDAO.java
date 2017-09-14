package com.mazdausa.dealer.parts.dpal.persistence;

import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailDTO;
import com.mazdausa.dealer.parts.dpal.services.DPALDAOException;

public interface DealerContactInfoDAO {
	DealerDetailDTO findDTO(String countryCode, String dealerCode,
			String shippingemail1, String shippingemail2,
			String shippingemail3, String shippingemail4,
			String shippingemail5, String buyingemail1, String buyingemail2,
			String buyingemail3, String buyingemail4, String buyingemail5,
			String name, String number) throws DPALDAOException;
}
