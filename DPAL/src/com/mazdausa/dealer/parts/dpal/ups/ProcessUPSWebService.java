package com.mazdausa.dealer.parts.dpal.ups;
import java.net.URL;

import org.apache.log4j.Logger;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.common.util.ApplicationUtil;
import com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean;
import com.mazdausa.dealer.parts.dpal.ups.helper.AccessRequest;
import com.mazdausa.dealer.parts.dpal.ups.helper.ShipmentAcceptRequest;
import com.mazdausa.dealer.parts.dpal.ups.helper.ShipmentAcceptResponse;
import com.mazdausa.dealer.parts.dpal.ups.helper.ShipmentConfirmRequest;
import com.mazdausa.dealer.parts.dpal.ups.helper.ShipmentConfirmResponse;

public class ProcessUPSWebService{
	
	private static Logger log = EMDCSLogger.getLogger(ProcessUPSWebService.class);
	private ShipmentConfirmRequest shipConfReq = null;
	private AccessRequest accessReq = null; 
	private ShipmentConfirmResponse shipConfRes = null;
	private ShipmentAcceptRequest shipAcceptReq = null;
	private ShipmentAcceptResponse shipAcceptRes = null;
	
	private String shipDigest = null;
	private String accessReqStr = null;
	
	private String trackingnumber = null;
	private String graphicimage = null;

	
	public String createShipmentConfirmRequest(DealerDataBean buyingDealerBean, DealerDataBean sellingDealerBean, String packageWeight) throws Exception{
		accessReq = new AccessRequest();
		shipConfReq = new ShipmentConfirmRequest();
		
		//read access information from properties file
		accessReq.accessLicenseNumber = ApplicationUtil.getSystemProperty("DPAL", "ups.license.number");
		
		log.debug("Access License Number" + accessReq.accessLicenseNumber);
		accessReq.userId = ApplicationUtil.getSystemProperty("DPAL", "ups.user.id");
		accessReq.password = ApplicationUtil.getSystemProperty("DPAL", "ups.user.password");
		
		//read shipment confirm request information from properties file
		shipConfReq.requestAction = ApplicationUtil.getSystemProperty("DPAL","ups.request.action");
		shipConfReq.requestOption = ApplicationUtil.getSystemProperty("DPAL","ups.request.option");
		
		// Label Specifications
		shipConfReq.labelCode = ApplicationUtil.getSystemProperty("DPAL","ups.label.code" );
		shipConfReq.labelDescription = ApplicationUtil.getSystemProperty("DPAL","ups.label.description" );
		shipConfReq.httpUserAgent = ApplicationUtil.getSystemProperty("DPAL","ups.http.useragent" );
		
		shipConfReq.labelImageFormatCode=ApplicationUtil.getSystemProperty("DPAL","ups.label.imagecode" );
		shipConfReq.labelImageFormatDescription =ApplicationUtil.getSystemProperty("DPAL","ups.label.imagedescription" );
		
		// Shipper Information
		//TODO: Amrit - what information is this?
		shipConfReq.shipperName=ApplicationUtil.getSystemProperty("DPAL","ups.shipper.name" );
		shipConfReq.shipperPhoneNumber=ApplicationUtil.getSystemProperty("DPAL","ups.shipper.phnumber" );
		shipConfReq.shipperNumber=ApplicationUtil.getSystemProperty("DPAL","ups.account.number" );

		shipConfReq.shipperAddress=ApplicationUtil.getSystemProperty("DPAL","ups.shipper.address" );
		shipConfReq.shipperAddressCity=ApplicationUtil.getSystemProperty("DPAL","ups.shipper.addresscity" );
		shipConfReq.shipperAddressStateCode=ApplicationUtil.getSystemProperty("DPAL","ups.shipper.state" );
		shipConfReq.shipperAddressPostalCode=ApplicationUtil.getSystemProperty("DPAL","ups.shipper.postalcode" );
		shipConfReq.shipperAddressPostCodeExtended=ApplicationUtil.getSystemProperty("DPAL","ups.shipper.postalcodeex" );
		
		//ship to
		//TODO: Should come from buying dealer bean
		shipConfReq.shiptoCompanyName = buyingDealerBean.getName(); //ApplicationUtil.getSystemProperty("DPAL",buyingDealerBean.getName());
		shipConfReq.shiptoAttentionName = buyingDealerBean.getContactName();//ApplicationUtil.getSystemProperty("DPAL",buyingDealerBean.getContactName());
		shipConfReq.shiptoPhone = buyingDealerBean.getContactNumber(); //ApplicationUtil.getSystemProperty("DPAL",buyingDealerBean.getContactNumber());
		shipConfReq.shiptoAddress = buyingDealerBean.getAddressLines();//ApplicationUtil.getSystemProperty("DPAL",buyingDealerBean.getAddressLines());
		shipConfReq.shiptoCity = buyingDealerBean.getCity();//ApplicationUtil.getSystemProperty("DPAL",buyingDealerBean.getCity());
		shipConfReq.shiptoStateCode = buyingDealerBean.getState(); //ApplicationUtil.getSystemProperty("DPAL",buyingDealerBean.getState());
		shipConfReq.shiptoPostalCode = buyingDealerBean.getZip(); //ApplicationUtil.getSystemProperty("DPAL", buyingDealerBean.getZip());
		shipConfReq.shiptoCountryCode = ApplicationUtil.getSystemProperty("DPAL","ups.shipto.country" );
		
		//ship from
		//TODO: Should come from selling dealer bean
		shipConfReq.shipfromCompanyName = sellingDealerBean.getName();//ApplicationUtil.getSystemProperty("DPAL","ups.shipfrom.companyname" );
		shipConfReq.shipfromAttentionName = sellingDealerBean.getContactName();//ApplicationUtil.getSystemProperty("DPAL","ups.shipfrom.attentionname" );
		shipConfReq.shipfromPhoneNumber = sellingDealerBean.getContactNumber();//ApplicationUtil.getSystemProperty("DPAL","ups.shipfrom.phone" );
		shipConfReq.shipfromAddress = sellingDealerBean.getAddressLines();//ApplicationUtil.getSystemProperty("DPAL","ups.shipfrom.address");
		shipConfReq.shipfromCity = sellingDealerBean.getCity();//ApplicationUtil.getSystemProperty("DPAL","ups.shipfrom.city" );
		shipConfReq.shipfromStateCode = sellingDealerBean.getState();//ApplicationUtil.getSystemProperty("DPAL","ups.shipfrom.state" );
		shipConfReq.shipfromPostalCode = sellingDealerBean.getZip();//ApplicationUtil.getSystemProperty("DPAL","ups.shipfrom.postal" );
		shipConfReq.shipfromCountryCode = ApplicationUtil.getSystemProperty("DPAL","ups.shipto.country" );
		
		//Payment Information
		shipConfReq.paymentinfoAccountNumber=ApplicationUtil.getSystemProperty("DPAL","ups.account.number" );
		
		// Service description
		shipConfReq.serviceCode=ApplicationUtil.getSystemProperty("DPAL","ups.service.code" );
		shipConfReq.serviceDescription=ApplicationUtil.getSystemProperty("DPAL","ups.service.description" );
		
		// Packaging Information
		shipConfReq.packagingtypeCode=ApplicationUtil.getSystemProperty("DPAL","ups.packaging.type" );
		shipConfReq.packagingtypeDescription=ApplicationUtil.getSystemProperty("DPAL","ups.packaging.description" );

		shipConfReq.packageDescription=ApplicationUtil.getSystemProperty("DPAL","ups.package.description" );
		shipConfReq.packageReferencenoCode=ApplicationUtil.getSystemProperty("DPAL","ups.package.refcode" );
		shipConfReq.packageReferencenoValue=ApplicationUtil.getSystemProperty("DPAL","ups.package.refvalue" );
		
		shipConfReq.packageWeightMeasurement=ApplicationUtil.getSystemProperty("DPAL","ups.package.measurement" );
		//shipConfReq.packagePackageweightWeight=ApplicationUtil.getSystemProperty("DPAL","ups.package.weight" );
		
		shipConfReq.packageAdditionalHandling=ApplicationUtil.getSystemProperty("DPAL","ups.package.handling" );
		shipConfReq.packageWeight = packageWeight;
		//read shipment request information from dealer info bean
		//get the values from buying dealer bean
		//TODO: All the mappings need to be verified
		/*shipConfReq.shiptoCompanyName = buyingDealerBean.getContactName();
		shipConfReq.shiptoAttentionName = buyingDealerBean.getContactName();
		shipConfReq.shiptoPhone = buyingDealerBean.getContactNumber();
		shipConfReq.shiptoAddress = buyingDealerBean.getAddress();
		shipConfReq.shiptoCity = buyingDealerBean.getCity();
		shipConfReq.shiptoStateCode = buyingDealerBean.getState();
		shipConfReq.shiptoPostalCode = buyingDealerBean.getZip();
		shipConfReq.shiptoCountryCode = buyingDealerBean.getCode();
		/*
		//get the values from selling dealer bean
		shipConfReq.shipfromAttentionName = sellingDealerBean.getContactName();
		shipConfReq.shipfromPhoneNumber = sellingDealerBean.getContactNumber();
		shipConfReq.shipfromAddress = sellingDealerBean.getAddress();
		shipConfReq.shipfromCity = sellingDealerBean.getCity();
		shipConfReq.shipfromStateCode = sellingDealerBean.getState();
		shipConfReq.shipfromPostalCode = sellingDealerBean.getZip();
		shipConfReq.shipfromCountryCode = sellingDealerBean.getCode();*/
		
		//convert access information doc to string
		URL path = this.getClass().getResource(accessReq.accessRequestSkelXMLPath);
		String fileNameAccess = path.getFile();
		accessReq.getDocumentFromFile(fileNameAccess);
		accessReq.populate();
		accessReqStr = accessReq.convertDocToString();
		
		//convert shipment confirm request doc to string
		URL pathreq = this.getClass().getResource(shipConfReq.confirmRequestSkelXMLPath);
		String fileNameConfirmreq = pathreq.getFile();
		shipConfReq.getDocumentFromFile(fileNameConfirmreq);
		shipConfReq.populate();
		String shipConfReqStr = shipConfReq.convertDocToString();
	
		//concatenate two strings to create the request
		log.debug(accessReqStr + shipConfReqStr);
		return accessReqStr + shipConfReqStr;
	}
	
	public void processShipmentConfirmResponse(String response) throws Exception{
		shipConfRes = new ShipmentConfirmResponse();
		//convert string to DOM doc
		shipConfRes.getDocumentFromString(response);
		//retrieve shipment digest from DOM doc
		shipConfRes.retrieve();
		shipDigest = shipConfRes.shipDigest;
	}
	
	public String createShipmentAcceptRequest() throws Exception{
		accessReq = new AccessRequest(); 
		shipAcceptReq = new ShipmentAcceptRequest();		
		
		//set shipment digest value into ship accept request xml
		shipAcceptReq.shipmentacceptrequestDigest = shipDigest;
		
		URL pathreq = this.getClass().getResource(shipAcceptReq.shipmentAcceptRequestSkelXMLPath);
		String fileNameAcceptreq = pathreq.getFile();
		shipAcceptReq.getDocumentFromFile(fileNameAcceptreq);
		shipAcceptReq.populate();
		
		String shipAcceptReqStr = shipAcceptReq.convertDocToString();
		return accessReqStr + shipAcceptReqStr ;
	}
	
	public void processShipmentAcceptResponse(String response) throws Exception{
		shipAcceptRes = new ShipmentAcceptResponse();
		//convert string to DOM doc
		shipAcceptRes.getDocumentFromString(response);
		//retrieve graphic image and tracking number from DOM doc
		shipAcceptRes.retrieve();
		trackingnumber = shipAcceptRes.shipTrackingNumber;
		graphicimage = shipAcceptRes.shipGraphicImage;
		log.debug("Tracking Number" + trackingnumber + "Graphic Image" + graphicimage );
	}
	
	public static void main(String args[]) throws Exception{
		/*ProcessUPSWebService prcUPSWS = new ProcessUPSWebService();
		String confirmRequest = prcUPSWS.createShipmentConfirmRequest(null, null);
		RequestSender confirmReqSend = new RequestSender( "https://wwwcie.ups.com/ups.app/xml/ShipConfirm", confirmRequest );
		String confirmresp = confirmReqSend.sendRequest();
		prcUPSWS.processShipmentConfirmResponse(confirmresp);
		
		String acceptRequest = prcUPSWS.createShipmentAcceptRequest();
		RequestSender acceptReqSend = new RequestSender( "https://wwwcie.ups.com/ups.app/xml/ShipAccept", acceptRequest );
		String acceptresp = acceptReqSend.sendRequest();
		prcUPSWS.processShipmentAcceptResponse( acceptresp);
		
		prcUPSWS.getTrackingNumber();
		prcUPSWS.getGraphicImage();*/
	}

	/**
	 * @return the trackingnumber
	 */
	public String getTrackingNumber() {
		return trackingnumber;
	}

	/**
	 * @return the graphicimage
	 */
	public String getGraphicImage() {
		return graphicimage;
	}

	/**
	 * @return Returns the accessReq.
	 */
	public ShipmentConfirmRequest getShipmentConfirmRequestBean() {
		return shipConfReq;
	}
	/**
	 * @param accessReq The accessReq to set.
	 */
	public void setShipmentConfirmRequestBean(ShipmentConfirmRequest shipConfReq) {
		this.shipConfReq = shipConfReq;
	}
}
