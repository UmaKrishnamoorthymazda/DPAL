/*
 * Created on Dec 10, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence.dto;

import java.util.ArrayList;
import java.util.Iterator;

import com.mazdausa.dealer.parts.dpal.applications.databeans.BackOrderDataBean;

/**
 * @author InterraIT
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BackOrderDTOImpl implements BackOrderDTO {

	private ArrayList backOrderDataBeanList;
	private int totalRecordsCount;
	
	public BackOrderDTOImpl() {
		backOrderDataBeanList = new ArrayList();
	}
	
	public BackOrderDTOImpl(ArrayList beanList) {
		this.backOrderDataBeanList = beanList;
	}
	
	public void setBackOrderDataBean(ArrayList beanList) {
		backOrderDataBeanList = beanList;
	}
	
	public ArrayList getBackOrderDataBeanList() {
		return backOrderDataBeanList;
	}
	
	public void addBackOrderDataBean(BackOrderDataBean bean) {
		backOrderDataBeanList.add(bean);
	}
	
	public Iterator iterator() {
		return backOrderDataBeanList.iterator();
	}
	
	
	/**
	 * @return Returns the totalRecordsCount.
	 */
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}
	/**
	 * @param totalRecordsCount The totalRecordsCount to set.
	 */
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}
}
