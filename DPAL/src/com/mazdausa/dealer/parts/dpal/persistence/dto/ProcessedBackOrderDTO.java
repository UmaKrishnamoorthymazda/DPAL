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
public interface ProcessedBackOrderDTO {
	public void setProcessedBackOrderDataBean(ArrayList beanList);
	public ArrayList getProcessedBackOrderDataBeanList();
	public void addProcessedBackOrderDataBean(ProcessedBackOrderDataBean bean);
	public int getTotalRecordsCount();
	public void setTotalRecordsCount(int totalRecordsCount);
	public Iterator iterator();	
}
