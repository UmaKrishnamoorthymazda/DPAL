package com.mazdausa.dealer.parts.dpal.persistence.dto;

import java.util.ArrayList;
import java.util.Iterator;

import com.mazdausa.dealer.parts.dpal.applications.databeans.AwaitingConfirmationDataBean;

public interface AwaitingConfirmationDTO {

	public void setAwaitingConfirmationDataBean(ArrayList beanList);
	public void addAwaitingConfirmationDataBean(AwaitingConfirmationDataBean bean);
	public int getTotalRecordsCount();
	public void setTotalRecordsCount(int totalRecordsCount);
	public Iterator iterator();
}
