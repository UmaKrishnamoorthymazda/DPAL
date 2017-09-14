/*
 * Created on Jan 6, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.applications.form;

import org.apache.struts.action.ActionForm;

import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerListDTO;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealerDetailForm extends ActionForm {
	private DealerDetailDTO dealerDetailDTO;
	private DealerListDTO dealerListDTO;
	boolean updateStatus;
	boolean showUpdateStatus;
	
	/**
	 * @return Returns the dealerDetailDTO.
	 */
	public DealerDetailDTO getDealerDetailDTO() {
		return dealerDetailDTO;
	}
	/**
	 * @param dealerDetailDTO The dealerDetailDTO to set.
	 */
	public void setDealerDetailDTO(DealerDetailDTO dealerDetailDTO) {
		this.dealerDetailDTO = dealerDetailDTO;
	}
	/**
	 * @return Returns the showUpdateStatus.
	 */
	public boolean isShowUpdateStatus() {
		return showUpdateStatus;
	}
	/**
	 * @param showUpdateStatus The showUpdateStatus to set.
	 */
	public void setShowUpdateStatus(boolean showUpdateStatus) {
		this.showUpdateStatus = showUpdateStatus;
	}
	/**
	 * @return Returns the updateStatus.
	 */
	public boolean isUpdateStatus() {
		return updateStatus;
	}
	/**
	 * @param updateStatus The updateStatus to set.
	 */
	public void setUpdateStatus(boolean updateStatus) {
		this.updateStatus = updateStatus;
	}
	/**
	 * @return Returns the dealerListDTO.
	 */
	public DealerListDTO getDealerListDTO() {
		return dealerListDTO;
	}
	/**
	 * @param dealerListDTO The dealerListDTO to set.
	 */
	public void setDealerListDTO(DealerListDTO dealerListDTO) {
		this.dealerListDTO = dealerListDTO;
	}
}
