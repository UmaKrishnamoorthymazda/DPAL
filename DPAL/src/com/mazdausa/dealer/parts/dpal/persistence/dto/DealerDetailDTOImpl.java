/*
 * Created on Dec 17, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence.dto;

import com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealerDetailDTOImpl implements DealerDetailDTO {
	DealerDataBean dealerDataBean;
	/**
	 * @return Returns the dealerDataBean.
	 */
	public DealerDataBean getDealerDataBean() {
		return dealerDataBean;
	}
	/**
	 * @param dealerDataBean The dealerDataBean to set.
	 */
	public void setDealerDataBean(DealerDataBean dealerDataBean) {
		this.dealerDataBean = dealerDataBean;
	}
}
