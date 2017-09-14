package com.mazdausa.dealer.parts.dpal.ups.helper;

public class ShipmentConfirmRequest extends UPSHelperAbstract{

	public static final String REQUEST_ACTION_XPATH = "ShipmentConfirmRequest/Request/RequestAction";
	public static final String REQUEST_OPTION_XPATH = "ShipmentConfirmRequest/Request/RequestOption";
	
	// Label Specifications
	public static final String LABEL_CODE_XPATH = "ShipmentConfirmRequest/LabelSpecification/LabelPrintMethod/Code";
	public static final String LABEL_DESCRIPTION_XPATH = "ShipmentConfirmRequest/LabelSpecification/LabelPrintMethod/Description";
	public static final String HTTP_USER_AGENT_XPATH = "ShipmentConfirmRequest/LabelSpecification/HTTPUserAgent";
	
	// Label Specifications
	public static final String LABEL_IMAGE_FORMAT_CODE_XPATH = "ShipmentConfirmRequest/LabelSpecification/LabelImageFormat/Code";
	public static final String LABEL_IMAGE_FORMAT_DESCRIPTION_XPATH = "ShipmentConfirmRequest/LabelSpecification/LabelImageFormat/Description";

	// Shipper Information
	public static final String SHIPPER_NAME_XPATH = "ShipmentConfirmRequest/Shipment/Shipper/Name";
	public static final String SHIPPER_PHONE_NUMBER_XPATH = "ShipmentConfirmRequest/Shipment/Shipper/PhoneNumber";
	public static final String SHIPPER_NUMBER_XPATH = "ShipmentConfirmRequest/Shipment/Shipper/ShipperNumber";
	
	public static final String SHIPPER_ADDRESS_XPATH = "ShipmentConfirmRequest/Shipment/Shipper/Address/AddressLine1";
	public static final String SHIPPER_ADDRESS_CITY_XPATH = "ShipmentConfirmRequest/Shipment/Shipper/Address/City";
	public static final String SHIPPER_ADDRESS_STATE_CODE_XPATH = "ShipmentConfirmRequest/Shipment/Shipper/Address/StateProvinceCode";
	public static final String SHIPPER_ADDRESS_POSTAL_CODE_XPATH = "ShipmentConfirmRequest/Shipment/Shipper/Address/PostalCode";
	public static final String SHIPPER_ADDRESS_POST_CODE_EXTENDED_XPATH = "ShipmentConfirmRequest/Shipment/Shipper/Address/PostcodeExtendedLow";
	public static final String SHIPPER_ADDRESS_COUNTRY_CODE_XPATH = "ShipmentConfirmRequest/Shipment/Shipper/Address/CountryCode";

	// Shipped to Information
	public static final String SHIPTO_COMPANY_NAME_XPATH = "ShipmentConfirmRequest/Shipment/ShipTo/CompanyName";
	public static final String SHIPTO_ATTENTION_NAME_XPATH = "ShipmentConfirmRequest/Shipment/ShipTo/AttentionName";
	public static final String SHIPTO_PHONE_XPATH = "ShipmentConfirmRequest/Shipment/ShipTo/PhoneNumber";
	public static final String SHIPTO_ADDRESS_XPATH = "ShipmentConfirmRequest/Shipment/ShipTo/Address/AddressLine1";
	public static final String SHIPTO_CITY_XPATH = "ShipmentConfirmRequest/Shipment/ShipTo/Address/City";
	public static final String SHIPTO_STATE_CODE_XPATH = "ShipmentConfirmRequest/Shipment/ShipTo/Address/StateProvinceCode";
	public static final String SHIPTO_POSTAL_CODE_XPATH = "ShipmentConfirmRequest/Shipment/ShipTo/Address/PostalCode";
	public static final String SHIPTO_COUNTRY_CODE_XPATH = "ShipmentConfirmRequest/Shipment/ShipTo/Address/CountryCode";

	// Shipped from Information
	public static final String SHIPFROM_COMPANY_NAME_XPATH = "ShipmentConfirmRequest/Shipment/ShipFrom/CompanyName";
	public static final String SHIPFROM_ATTENTION_NAME_XPATH = "ShipmentConfirmRequest/Shipment/ShipFrom/AttentionName";
	public static final String SHIPFROM_PHONE_NUMBER_XPATH = "ShipmentConfirmRequest/Shipment/ShipFrom/PhoneNumber";
	public static final String SHIPFROM_ADDRESS_XPATH = "ShipmentConfirmRequest/Shipment/ShipFrom/Address/AddressLine1";
	public static final String SHIPFROM_CITY_XPATH = "ShipmentConfirmRequest/Shipment/ShipFrom/Address/City";
	public static final String SHIPFROM_STATE_CODE_XPATH = "ShipmentConfirmRequest/Shipment/ShipFrom/Address/StateProvinceCode";
	public static final String SHIPFROM_POSTAL_CODE_XPATH = "ShipmentConfirmRequest/Shipment/ShipFrom/Address/PostalCode";
	public static final String SHIPFROM_COUNTRY_CODE_XPATH = "ShipmentConfirmRequest/Shipment/ShipFrom/Address/CountryCode";
	
	//Payment Information
	public static final String PAYMENTINFO_ACCOUNT_NUMBER_XPATH = "ShipmentConfirmRequest/Shipment/PaymentInformation/Prepaid/BillShipper/AccountNumber";
	
	// Service description
	public static final String SERVICE_CODE_XPATH = "ShipmentConfirmRequest/Shipment/Service/Code";
	public static final String SERVICE_DESCRIPTION_XPATH = "ShipmentConfirmRequest/Shipment/Service/Description";
	
	// Packaging Information
	public static final String PACKAGINGTYPE_CODE_XPATH = "ShipmentConfirmRequest/Shipment/Package/PackagingType/Code";
	public static final String PACKAGINGTYPE_DESCRIPTION_XPATH = "ShipmentConfirmRequest/Shipment/Package/PackagingType/Description";

	public static final String PACKAGE_DESCRIPTION_XPATH = "ShipmentConfirmRequest/Shipment/Package/Description";
	public static final String PACKAGE_REFERENCENO_CODE_XPATH = "ShipmentConfirmRequest/Shipment/Package/ReferenceNumber/Code";
	public static final String PACKAGE_REFERENCENO_VALUE_XPATH = "ShipmentConfirmRequest/Shipment/Package/ReferenceNumber/Value";
	
	public static final String PACKAGE_PACKAGEWEIGHT_MEASUREMENT_XPATH = "ShipmentConfirmRequest/Shipment/Package/PackageWeight/UnitOfMeasurement";
	public static final String PACKAGE_PACKAGEWEIGHT_WEIGHT_XPATH = "ShipmentConfirmRequest/Shipment/Package/PackageWeight/Weight";

	public static final String PACKAGE_ADDITIONAL_HANDLING_XPATH = "ShipmentConfirmRequest/Shipment/Package/AdditionalHandling";


	public String requestAction = "";
	public String requestOption = "";

	public String labelCode = "";
	public String labelDescription = "";
	public String httpUserAgent = "";

	public String labelImageFormatCode = "";
	public String labelImageFormatDescription = "";

	public String shipperName = "";
	public String shipperPhoneNumber = "";
	public String shipperNumber = "";
	public String shipperAddress = "";
	public String shipperAddressCity = "";
	public String shipperAddressStateCode = "";
	public String shipperAddressPostalCode = "";
	public String shipperAddressPostCodeExtended = "";
	public String shipperAddressCountryCode = "";

	public String shiptoCompanyName = "";
	public String shiptoAttentionName = "";
	public String shiptoPhone = "";
	public String shiptoAddress = "";
	public String shiptoCity = "";
	public String shiptoStateCode = "";
	public String shiptoPostalCode = "";
	public String shiptoCountryCode = "";

	public String shipfromCompanyName = "";
	public String shipfromAttentionName = "";
	public String shipfromPhoneNumber = "";
	public String shipfromAddress = "";
	public String shipfromCity = "";
	public String shipfromStateCode = "";
	public String shipfromPostalCode = "";
	public String shipfromCountryCode = "";

	public String paymentinfoAccountNumber = "";
	public String serviceCode = "";
	public String serviceDescription = "";

	public String packagingtypeCode = "";
	public String packagingtypeDescription = "";

	public String packageDescription = "";
	public String packageReferencenoCode = "";
	public String packageReferencenoValue = "";

	public String packageWeightMeasurement = "";
	public String packageWeight = "";

	public String packageAdditionalHandling = "";

	public String confirmRequestSkelXMLPath = "/xml/ShipmentConfirmRequest_template.xml";
	
	public void populate() throws Exception {
		// Label Specifications
		setValueInXPath(REQUEST_ACTION_XPATH, requestAction);
		setValueInXPath(REQUEST_OPTION_XPATH, requestOption);
		 
		// Label Specifications
		setValueInXPath(LABEL_CODE_XPATH, labelCode);
		setValueInXPath(LABEL_DESCRIPTION_XPATH, labelDescription);
		setValueInXPath(HTTP_USER_AGENT_XPATH, httpUserAgent);
		setValueInXPath(LABEL_IMAGE_FORMAT_CODE_XPATH, labelImageFormatCode);
		setValueInXPath(LABEL_IMAGE_FORMAT_DESCRIPTION_XPATH, labelImageFormatDescription);
		 
		// Shipper Information
		setValueInXPath(SHIPPER_NAME_XPATH, shipperName);
		setValueInXPath(SHIPPER_PHONE_NUMBER_XPATH, shipperPhoneNumber);
		setValueInXPath(SHIPPER_NUMBER_XPATH, shipperNumber);
		setValueInXPath(SHIPPER_ADDRESS_XPATH, shipperAddress);
		setValueInXPath(SHIPPER_ADDRESS_CITY_XPATH, shipperAddressCity);
		setValueInXPath(SHIPPER_ADDRESS_STATE_CODE_XPATH, shipperAddressStateCode);
		setValueInXPath(SHIPPER_ADDRESS_POSTAL_CODE_XPATH, shipperAddressPostalCode);
		setValueInXPath(SHIPPER_ADDRESS_POST_CODE_EXTENDED_XPATH, shipperAddressPostCodeExtended);
		setValueInXPath(SHIPPER_NUMBER_XPATH, shipperAddressCountryCode);
		
		//Shipto Information
		setValueInXPath(SHIPTO_COMPANY_NAME_XPATH, shiptoCompanyName);
		setValueInXPath(SHIPTO_ATTENTION_NAME_XPATH, shiptoAttentionName);
		setValueInXPath(SHIPTO_PHONE_XPATH, shiptoPhone);
		setValueInXPath(SHIPTO_ADDRESS_XPATH, shiptoAddress);
		setValueInXPath(SHIPTO_CITY_XPATH, shiptoCity);
		setValueInXPath(SHIPTO_STATE_CODE_XPATH, shiptoStateCode);
		setValueInXPath(SHIPTO_POSTAL_CODE_XPATH, shiptoPostalCode);
		setValueInXPath(SHIPTO_COUNTRY_CODE_XPATH, shiptoCountryCode);
		
		//Shipfrom Information
		setValueInXPath(SHIPFROM_COMPANY_NAME_XPATH, shipfromCompanyName);
		setValueInXPath(SHIPFROM_ATTENTION_NAME_XPATH, shipfromAttentionName);
		setValueInXPath(SHIPFROM_PHONE_NUMBER_XPATH, shipfromPhoneNumber);
		setValueInXPath(SHIPFROM_ADDRESS_XPATH, shipfromAddress);
		setValueInXPath(SHIPFROM_CITY_XPATH, shipfromCity);
		setValueInXPath(SHIPFROM_STATE_CODE_XPATH, shipfromStateCode);
		setValueInXPath(SHIPFROM_POSTAL_CODE_XPATH, shipfromPostalCode);
		setValueInXPath(SHIPFROM_COUNTRY_CODE_XPATH, shipfromCountryCode);
		
		//Payment Information
		setValueInXPath(PAYMENTINFO_ACCOUNT_NUMBER_XPATH, paymentinfoAccountNumber);
		 
		// Service description
		setValueInXPath(SERVICE_CODE_XPATH, serviceCode);
		setValueInXPath(SERVICE_DESCRIPTION_XPATH, serviceDescription);
		
		// Packaging Information
		setValueInXPath(PACKAGINGTYPE_CODE_XPATH, packagingtypeCode);
		setValueInXPath(PACKAGINGTYPE_DESCRIPTION_XPATH, packagingtypeDescription);
		
		setValueInXPath(PACKAGE_DESCRIPTION_XPATH, packageDescription);
		setValueInXPath(PACKAGE_REFERENCENO_CODE_XPATH, packageReferencenoCode);
		setValueInXPath(PACKAGE_REFERENCENO_VALUE_XPATH, packageReferencenoValue);
		
		setValueInXPath(PACKAGE_PACKAGEWEIGHT_MEASUREMENT_XPATH, packageWeightMeasurement);
		setValueInXPath(PACKAGE_PACKAGEWEIGHT_WEIGHT_XPATH, packageWeight);
		
		setValueInXPath(PACKAGE_ADDITIONAL_HANDLING_XPATH, packageAdditionalHandling);
	}
	
}
