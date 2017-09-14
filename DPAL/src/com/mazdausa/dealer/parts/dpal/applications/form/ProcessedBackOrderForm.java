package com.mazdausa.dealer.parts.dpal.applications.form;

import java.util.ArrayList;
import java.util.Map;

import org.apache.struts.action.ActionForm;

import com.mazdausa.dealer.parts.dpal.persistence.dto.ProcessedBackOrderDTO;

public class ProcessedBackOrderForm extends ActionForm {
	private ProcessedBackOrderDTO processedBackOrderDTO;
	private String sortType;
	//private Map idShipQuantityMap;

	public ProcessedBackOrderForm() {
		super();
	}

	public ProcessedBackOrderDTO getProcessedBackOrderDTO() {
		return processedBackOrderDTO;
	}

	public void setProcessedBackOrderDTO(ProcessedBackOrderDTO processedBackOrderDTO) {
		this.processedBackOrderDTO = processedBackOrderDTO;
	}	

	/**
	 * @return the sortType
	 */
	public String getSortType() {
		return sortType;
	}

	/**
	 * @param sortType the sortType to set
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
}
