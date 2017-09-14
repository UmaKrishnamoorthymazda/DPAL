/*
 * Created on Jan 7, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence.dto;

import com.mazdausa.dealer.parts.dpal.applications.databeans.MessageOutputDataBean;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealerDetailUpdateDTOImpl implements DealerDetailUpdateDTO{
	private MessageOutputDataBean messageOutputDataBean;
	
	/**
	 * @return Returns the messageOutputDataBean.
	 */
	public MessageOutputDataBean getMessageOutputDataBean() {
		return messageOutputDataBean;
	}
	/**
	 * @param messageOutputDataBean The messageOutputDataBean to set.
	 */
	public void setMessageOutputDataBean(
			MessageOutputDataBean messageOutputDataBean) {
		this.messageOutputDataBean = messageOutputDataBean;
	}
}
