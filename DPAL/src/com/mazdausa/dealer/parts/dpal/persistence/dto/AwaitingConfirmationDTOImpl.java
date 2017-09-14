package com.mazdausa.dealer.parts.dpal.persistence.dto;

import java.util.ArrayList;
import java.util.Iterator;

import com.mazdausa.dealer.parts.dpal.applications.databeans.AwaitingConfirmationDataBean;

/**
 * @author InterraIT
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AwaitingConfirmationDTOImpl implements AwaitingConfirmationDTO {

	private ArrayList /*BackOrderDataBean*/ awaitingConfirmationDataBeanList;
	private int totalRecordsCount;
	
	public AwaitingConfirmationDTOImpl() {
		awaitingConfirmationDataBeanList = new ArrayList();
	}
	
	public AwaitingConfirmationDTOImpl(ArrayList beanList) {
		this.awaitingConfirmationDataBeanList = beanList;
	}
	
	public void setAwaitingConfirmationDataBean(ArrayList beanList) {
		awaitingConfirmationDataBeanList = beanList;
	}
	
	public ArrayList getAwaitingConfirmationDataBeanList() {
		return awaitingConfirmationDataBeanList;
	}
	
	public void addAwaitingConfirmationDataBean(AwaitingConfirmationDataBean bean) {
		awaitingConfirmationDataBeanList.add(bean);
	}
	
	public Iterator iterator() {
		return awaitingConfirmationDataBeanList.iterator();
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
