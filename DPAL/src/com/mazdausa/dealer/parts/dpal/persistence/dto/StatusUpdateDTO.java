/*
 * Created on Dec 16, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence.dto;

import java.util.ArrayList;

import com.mazdausa.dealer.parts.dpal.applications.databeans.MessageOutputDataBean;
import com.mazdausa.dealer.parts.dpal.applications.databeans.StatusUpdateDataBean;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface StatusUpdateDTO {
	public ArrayList getStatusUpdateDataList();
	public void setStatusUpdateDataList(ArrayList statusUpdateDataList);
	public void addStatusUpdateDataBean(StatusUpdateDataBean bean);
	//public ArrayList/*MessageOutputDataBean*/ /*getMessageOutputDataBean*/ getMessageOutputDataList();
	//public void setMessageOutputDataBean(/*MessageOutputDataBean*/ ArrayList /*messageOutputDataBean*/ messageOutputDataList);
	public boolean isUpdateSuccess();
	public void setUpdateSuccess(boolean updateSuccess);
	public String getErrorIdList();
	public void setErrorIdList(String errorIdList);
	public void addErrorId(String errorIdList);
}
