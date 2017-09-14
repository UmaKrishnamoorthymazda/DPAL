/*
 * Created on Dec 10, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence.dto;

import java.util.ArrayList;
import java.util.Iterator;

import com.mazdausa.dealer.parts.dpal.applications.databeans.ProcessedBackOrderDataBean;

/**
 * @author InterraIT
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProcessedBackOrderDTOImpl implements ProcessedBackOrderDTO {

	private ArrayList processedBackOrderDataBeanList;
	private int totalRecordsCount;
	
	public ProcessedBackOrderDTOImpl() {
		processedBackOrderDataBeanList = new ArrayList();
	}
	
	public ProcessedBackOrderDTOImpl(ArrayList beanList) {
		this.processedBackOrderDataBeanList = beanList;
	}
	
	public void setProcessedBackOrderDataBean(ArrayList beanList) {
		processedBackOrderDataBeanList = beanList;
	}
	
	public ArrayList getProcessedBackOrderDataBeanList() {
		return processedBackOrderDataBeanList;
	}
	
	public void addProcessedBackOrderDataBean(ProcessedBackOrderDataBean bean) {
		processedBackOrderDataBeanList.add(bean);
	}
	
	public Iterator iterator() {
		return processedBackOrderDataBeanList.iterator();
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
