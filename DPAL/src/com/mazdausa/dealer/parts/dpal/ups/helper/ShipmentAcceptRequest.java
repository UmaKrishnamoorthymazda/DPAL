package com.mazdausa.dealer.parts.dpal.ups.helper;

public class ShipmentAcceptRequest extends UPSHelperAbstract{

	public static final String SHIPMENTACCEPTREQUEST_DIGEST_XPATH = "ShipmentAcceptRequest/ShipmentDigest";
	
	public String shipmentAcceptRequestSkelXMLPath = "/xml/ShipmentAcceptRequest_template.xml";
	public String shipmentacceptrequestDigest = "";
	
	public void populate() throws Exception {
		setValueInXPath(SHIPMENTACCEPTREQUEST_DIGEST_XPATH, shipmentacceptrequestDigest);		
	}
}
