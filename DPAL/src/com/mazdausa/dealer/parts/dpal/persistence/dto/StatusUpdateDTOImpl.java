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
public class StatusUpdateDTOImpl implements StatusUpdateDTO {
	private ArrayList statusUpdateDataList;
	// Modified to fix Bug id: 2
	//private ArrayList /*MessageOutputDataBean*/ messageOutputDataBean;
	//private ArrayList messageOutputDataList;

	// to be initialized by the developer when creating DTO
	private boolean updateSuccess = true;
	
	private String errorIdList;
	
	public StatusUpdateDTOImpl() {
		statusUpdateDataList = new ArrayList();
	}
	
	/**
	 * @return Returns the statusUpdateDataList.
	 */
	public ArrayList getStatusUpdateDataList() {
		return statusUpdateDataList;
	}
	/**
	 * @param statusUpdateDataList The statusUpdateDataList to set.
	 */
	public void setStatusUpdateDataList(ArrayList statusUpdateDataList) {
		this.statusUpdateDataList = statusUpdateDataList;
	}
	
	public void addStatusUpdateDataBean(StatusUpdateDataBean bean) {
		statusUpdateDataList.add(bean);
	}
	
	// Begin: Modified and added to fix Bug id: 2
	/**
	 * @return the messageOutputDataBean
	 */
	/*public MessageOutputDataBean getMessageOutputDataBean() {
		return messageOutputDataBean;
	}*/

	/**
	 * @param messageOutputDataBean the messageOutputDataBean to set
	 */
	/*public void setMessageOutputDataBean(MessageOutputDataBean messageOutputDataBean) {
		this.messageOutputDataBean = messageOutputDataBean;
	}*/
	/**
	 * @return Returns the messageOutputDataBean.
	 */
	/*public ArrayList getMessageOutputDataList() {
		return messageOutputDataList;
	}*/
	/**
	 * @param messageOutputDataBean The messageOutputDataBean to set.
	 */
	/*public void setMessageOutputDataBean(ArrayList messageOutputDataList) {
		this.messageOutputDataList = messageOutputDataList;
	}*/
	// End changes
	/**
	 * @return Returns the updateSuccess.
	 */
	public boolean isUpdateSuccess() {
		return updateSuccess;
	}
	/**
	 * @param updateSuccess The updateSuccess to set.
	 */
	public void setUpdateSuccess(boolean updateSuccess) {
		this.updateSuccess = updateSuccess;
	}
	/**
	 * @return Returns the errorIdList.
	 */
	public String getErrorIdList() {
		return errorIdList;
	}
	/**
	 * @param errorIdList The errorIdList to set.
	 */
	public void setErrorIdList(String errorIdList) {
		this.errorIdList = errorIdList;
	}
	public void addErrorId(String errorIdList) {
		if(this.errorIdList == null || this.errorIdList.trim().equals("")) {
			this.errorIdList = errorIdList;
		} else {
			this.errorIdList += ("," + errorIdList);
		}
	}
}
