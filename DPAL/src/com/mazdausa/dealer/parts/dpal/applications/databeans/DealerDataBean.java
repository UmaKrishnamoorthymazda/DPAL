/*
 * Created on Dec 10, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.applications.databeans;

/**
 * @author InterraIT
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealerDataBean {
	private String code;
	private String name;
	private String contactName;
	private String contactNumber;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String state;
	private String zip;
	private String[] shippingDealer;
	private String[] buyingDealer;
	
	public DealerDataBean() {
		
	}
	
	/**
	 * @param code
	 * @param name
	 * @param city
	 * @param state
	 * @param zip
	 */
	public DealerDataBean(String code, String name, String city, String state,
			String zip) {
		super();
		this.code = code;
		this.name = name;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	/**
	 * @param code
	 * @param name
	 * @param contactName
	 * @param contactNumber
	 */
	public DealerDataBean(String code, String name, String contactName,
			String contactNumber) {
		this.code = code;
		this.name = name;
		this.contactName = contactName;
		this.contactNumber = contactNumber;
	}
	/*private String contactName;
	private String contactNumber;*/
	
	public DealerDataBean(String code, String name, String addressLine1,String addressLine2,String addressLine3, 
			String city, String state, String zip/*, String contactName, String contactNumber*/) {
		this.code = code;
		this.name = name;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
		this.city = city;
		this.state = state;
		this.zip = zip;
		/*this.contactName = contactName;
		this.contactNumber = contactNumber;*/
	}
	
	/**
	 * @return Returns the address.
	 */
	public String getFullAddress() {
		StringBuffer address = new StringBuffer();
		address.append(getAddressLines());
		/*address.append(addressLine1);
		if(addressLine2 != null && addressLine2.trim() != "")
			address.append(addressLine2).append(", ");
		if(addressLine3 != null && addressLine3.trim() != "")
			address.append(addressLine3).append(", ");*/
		address.append(city);
		address.append(state);
		address.append(zip);
		return address.toString();
	}
	
	public String getAddressLines() {
		StringBuffer address = new StringBuffer();
		address.append(addressLine1.trim());

		if(addressLine2 != null && !"".equals(addressLine2.trim()))
			address.append(addressLine2.trim()).append(", ");
		if(addressLine3 != null && !"".equals(addressLine3.trim()))
			address.append(addressLine3.trim()).append(", ");
		
		return address.toString();
	}
	/**
	 * @param address The address to set.
	 */
	/*public void setAddress(String address) {
		this.address = address;
	}*/
	/**
	 * @return Returns the city.
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the contactName.
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * @param contactName The contactName to set.
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * @return Returns the contactNumber.
	 */
	public String getContactNumber() {
		return contactNumber;
	}
	/**
	 * @param contactNumber The contactNumber to set.
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return Returns the zip.
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip The zip to set.
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return Returns the addressLine1.
	 */
	public String getAddressLine1() {
		return addressLine1;
	}
	/**
	 * @param addressLine1 The addressLine1 to set.
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	/**
	 * @return Returns the addressLine2.
	 */
	public String getAddressLine2() {
		return addressLine2;
	}
	/**
	 * @param addressLine2 The addressLine2 to set.
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	/**
	 * @return Returns the addressLine3.
	 */
	public String getAddressLine3() {
		return addressLine3;
	}
	/**
	 * @param addressLine3 The addressLine3 to set.
	 */
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	/**
	 * @return Returns the buyingDealer.
	 */
	public String[] getBuyingDealer() {
		return buyingDealer;
	}
	/**
	 * @param buyingDealer The buyingDealer to set.
	 */
	public void setBuyingDealer(String[] buyingDealer) {
		this.buyingDealer = buyingDealer;
	}
	/**
	 * @return Returns the shippingDealer.
	 */
	public String[] getShippingDealer() {
		return shippingDealer;
	}
	/**
	 * @param shippingDealer The shippingDealer to set.
	 */
	public void setShippingDealer(String[] shippingDealer) {
		this.shippingDealer = shippingDealer;
	}
}
