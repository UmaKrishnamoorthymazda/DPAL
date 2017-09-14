package com.mazdausa.dealer.parts.dpal.ups.helper;

import org.w3c.dom.Document;

public class AccessRequest extends UPSHelperAbstract{

	// AccessRequest
	public static final String ACCESS_LICENSE_NUMBER_XPATH = "AccessRequest/AccessLicenseNumber";
	public static final String USER_ID_XPATH = "AccessRequest/UserId";
	public static final String PASSWORD_XPATH = "AccessRequest/Password";

	public Document doc = null;
	public String accessRequestSkelXMLPath = "/xml/AccessRequest_template.xml";

	public String accessLicenseNumber = "";
	public String userId = "";
	public String password = "";

	public void populate() throws Exception {
		setValueInXPath(ACCESS_LICENSE_NUMBER_XPATH, accessLicenseNumber);
		setValueInXPath(USER_ID_XPATH, userId);
		setValueInXPath(PASSWORD_XPATH, password);
	}
}
