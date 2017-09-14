package com.mazdausa.dealer.parts.dpal.applications.form;

import org.apache.struts.action.ActionForm;
import com.mazdausa.dealer.parts.dpal.applications.dto.ShippingConfirmationDTO;
import java.util.ArrayList;

public class ShippingConfirmationForm extends ActionForm {
	private String id;
	private ArrayList shippingConfirmationDTO;

	public ShippingConfirmationForm() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList getShippingConfirmationDTO() {
		return shippingConfirmationDTO;
	}

	public void setShippingConfirmationDTO(ArrayList shippingConfirmationDTO) {
		this.shippingConfirmationDTO = shippingConfirmationDTO;
	}
}
