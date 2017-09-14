/*
 * Created on Dec 21, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence.dto;

import com.mazdausa.dealer.parts.dpal.applications.databeans.ItemAttributeDataBean;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ItemAttributeDTOImpl implements ItemAttributeDTO {
	private ItemAttributeDataBean itemAttributeDataBean;
	
	/**
	 * @return Returns the itemAttributeDataBean.
	 */
	public ItemAttributeDataBean getItemAttributeDataBean() {
		return itemAttributeDataBean;
	}
	/**
	 * @param itemAttributeDataBean The itemAttributeDataBean to set.
	 */
	public void setItemAttributeDataBean(
			ItemAttributeDataBean itemAttributeDataBean) {
		this.itemAttributeDataBean = itemAttributeDataBean;
	}
}
