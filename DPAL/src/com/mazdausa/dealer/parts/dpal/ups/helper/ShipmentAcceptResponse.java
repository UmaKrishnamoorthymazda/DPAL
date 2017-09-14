package com.mazdausa.dealer.parts.dpal.ups.helper;

public class ShipmentAcceptResponse extends UPSHelperAbstract {

	public static final String SHIPTRACKING_NUMBER_XPATH = "ShipmentAcceptResponse/ShipmentResults/PackageResults/TrackingNumber";
	public static final String SHIPACCEPTRES_GRAPHIC_IMAGE = "ShipmentAcceptResponse/ShipmentResults/PackageResults/LabelImage/GraphicImage";
	
	public String shipTrackingNumber = "";
	public String shipGraphicImage = "";

	public void retrieve() throws Exception {
		shipTrackingNumber = getValueFromXPath(SHIPTRACKING_NUMBER_XPATH);
		shipGraphicImage = getValueFromXPath(SHIPACCEPTRES_GRAPHIC_IMAGE);		
	}
}
