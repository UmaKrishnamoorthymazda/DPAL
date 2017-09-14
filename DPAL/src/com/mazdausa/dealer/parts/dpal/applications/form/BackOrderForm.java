package com.mazdausa.dealer.parts.dpal.applications.form;

import java.util.ArrayList;
import java.util.Map;

import org.apache.struts.action.ActionForm;

import com.mazdausa.dealer.parts.dpal.persistence.dto.BackOrderDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailDTO;

public class BackOrderForm extends ActionForm {
	private String id;
	private BackOrderDTO backOrderDTO;
	private DealerDetailDTO dealerDetailDTO;
	private ArrayList idArrayList;
	private String stage;
	// Begin: Added to fix Bug id: 2
	private ArrayList errorIdArrayList;
	private String errorIdList;
	// End changes
	private Map errorType;
	private Map idQuantityMap;
	private boolean updateStatus;
	private boolean showUpdateStatus;
	private boolean showUpdateStatusShip;
	// Added to Fix Bug id: 7
	private String sortType;
	
	//private Map idShipQuantityMap;
	
	private boolean disableButtonConfirmAndCancel;
	//private boolean disableButtonProcessMoreShipment;

	/**
	 * @return Returns the disableButtonConfirmAndCancel.
	 */
	public boolean isDisableButtonConfirmAndCancel() {
		return disableButtonConfirmAndCancel;
	}
	/**
	 * @param disableButtonConfirmAndCancel The disableButtonConfirmAndCancel to set.
	 */
	public void setDisableButtonConfirmAndCancel(
			boolean disableButtonConfirmAndCancel) {
		this.disableButtonConfirmAndCancel = disableButtonConfirmAndCancel;
	}
	/**
	 * @return Returns the disableButtonProcessMoreShipment.
	 */
	public boolean isDisableButtonProcessMoreShipment() {
		return !disableButtonConfirmAndCancel;//disableButtonProcessMoreShipment;
	}
	/**
	 * @param disableButtonProcessMoreShipment The disableButtonProcessMoreShipment to set.
	 */
	public void setDisableButtonProcessMoreShipment(
			boolean disableButtonProcessMoreShipment) {
		//this.disableButtonProcessMoreShipment = disableButtonProcessMoreShipment;
		this.disableButtonConfirmAndCancel = !disableButtonProcessMoreShipment;
	}
	public BackOrderForm() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BackOrderDTO getBackOrderDTO() {
		return backOrderDTO;
	}

	public void setBackOrderDTO(BackOrderDTO backOrderDTO) {
		this.backOrderDTO = backOrderDTO;
	}
	/**
	 * @return Returns the idArrayList.
	 */
	public ArrayList getIdArrayList() {
		return idArrayList;
	}
	/**
	 * @param idArrayList The idArrayList to set.
	 */
	public void setIdArrayList(ArrayList idArrayList) {
		this.idArrayList = idArrayList;
	}
	/**
	 * @return Returns the stage.
	 */
	public String getStage() {
		return stage;
	}
	/**
	 * @param stage The stage to set.
	 */
	public void setStage(String stage) {
		this.stage = stage;
	}
	/**
	 * @return Returns the errorIdList.
	 */
	public ArrayList getErrorIdArrayList() {
		return errorIdArrayList;
	}
	/**
	 * @param errorIdList The errorIdList to set.
	 */
	public void setErrorIdArrayList(ArrayList errorIdArrayList) {
		this.errorIdArrayList = errorIdArrayList;
	}
	/**
	 * @return Returns the errorType.
	 */
	public Map getErrorType() {
		return errorType;
	}
	/**
	 * @param errorType The errorType to set.
	 */
	public void setErrorType(Map errorType) {
		this.errorType = errorType;
	}
	/**
	 * @return Returns the idQuantityMap.
	 */
	public Map getIdQuantityMap() {
		return idQuantityMap;
	}
	/**
	 * @param idQuantityMap The idQuantityMap to set.
	 */
	public void setIdQuantityMap(Map idQuantityMap) {
		this.idQuantityMap = idQuantityMap;
	}

	/**
	 * @return the dealerDetailDTO
	 */
	/*public DealerDetailDTO getDealerDetailDTO() {
		return dealerDetailDTO;
	}

	/**
	 * @param dealerDetailDTO the dealerDetailDTO to set
	 */
	/*public void setDealerDetailDTO(DealerDetailDTO dealerDetailDTO) {
		this.dealerDetailDTO = dealerDetailDTO;
	}*/
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
	 * @return the updateStatus
	 */
	public boolean isUpdateStatus() {
		return updateStatus;
	}
	/**
	 * @return the showUpdateStatus
	 */
	public boolean isShowUpdateStatus() {
		return showUpdateStatus;
	}
	/**
	 * @param updateStatus the updateStatus to set
	 */
	public void setUpdateStatus(boolean updateStatus) {
		this.updateStatus = updateStatus;
	}
	/**
	 * @param showUpdateStatus the showUpdateStatus to set
	 */
	public void setShowUpdateStatus(boolean showUpdateStatus) {
		this.showUpdateStatus = showUpdateStatus;
	}
	/**
	 * @return the showUpdateStatusShip
	 */
	public boolean isShowUpdateStatusShip() {
		return showUpdateStatusShip;
	}
	/**
	 * @param showUpdateStatusShip the showUpdateStatusShip to set
	 */
	public void setShowUpdateStatusShip(boolean showUpdateStatusShip) {
		this.showUpdateStatusShip = showUpdateStatusShip;
	}
	/**
	 * @return Returns the sortType.
	 */
	public String getSortType() {
		return sortType;
	}
	/**
	 * @param sortType The sortType to set.
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
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
}
