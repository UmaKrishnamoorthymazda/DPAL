package com.mazdausa.dealer.parts.dpal.ups.helper;

public class ShipmentConfirmResponse extends UPSHelperAbstract{

	public static final String SHIPMENTDIGEST_XPATH = "ShipmentConfirmResponse/ShipmentDigest";
	
	public String shipDigest = "";
	
	public void retrieve() throws Exception {
		shipDigest = getValueFromXPath(SHIPMENTDIGEST_XPATH);
		
	}
}
