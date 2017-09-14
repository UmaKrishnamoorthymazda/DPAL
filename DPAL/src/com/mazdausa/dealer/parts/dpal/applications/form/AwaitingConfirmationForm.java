package com.mazdausa.dealer.parts.dpal.applications.form;

import org.apache.struts.action.ActionForm;
import com.mazdausa.dealer.parts.dpal.persistence.dto.AwaitingConfirmationDTO;
import java.util.ArrayList;

public class AwaitingConfirmationForm extends ActionForm {
	private String id;
	private AwaitingConfirmationDTO awaitingConfirmationDTO;
	private ArrayList idArrayList;
	
	public AwaitingConfirmationForm() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AwaitingConfirmationDTO getAwaitingConfirmationDTO() {
		return awaitingConfirmationDTO;
	}

	public void setAwaitingConfirmationDTO(AwaitingConfirmationDTO dto) {
		this.awaitingConfirmationDTO = dto;
	}
	
	public ArrayList getIdArrayList() {
		return idArrayList;
	}
	/**
	 * @param idArrayList The idArrayList to set.
	 */
	public void setIdArrayList(ArrayList idArrayList) {
		this.idArrayList = idArrayList;
	}
}
