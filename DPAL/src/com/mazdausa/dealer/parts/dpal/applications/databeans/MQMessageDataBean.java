/*
 * Created on Jan 14, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.applications.databeans;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MQMessageDataBean {
	private static Logger log = EMDCSLogger.getLogger(MQMessageDataBean.class);
	
	private final String intfRecTypeCode = "DPSH";
	private final String intfCorpCode = "01";
	private String intfDateSent;
	private String intfTimeSent;
	private String countryCode;
	private String buyingDealerCode;
	private String sellingDealerCode;
	private String salesOrderNo;
	private String lineNo;
	private String itemNo;
	private final String shippingMethod = "UPSG";
	private String trackingNo;
	private String boQuantity;
	private String shippedQuantity;
	private String unitPrice;
	private String promoDiscount;
	
	private String dateFormat = "yyyyMMdd";
	private String timeFormat = "HHmmssSS";
	
	
	/**
	 * @return Returns the boQuantity.
	 */
	public String getBoQuantity() {
		return WebUtils.padNumber(new BigDecimal(Double.parseDouble(boQuantity)).setScale(2, BigDecimal.ROUND_HALF_UP).toString(), 9, false);
	}
	/**
	 * @param boQuantity The boQuantity to set.
	 */
	public void setBoQuantity(String boQuantity) {
		this.boQuantity = boQuantity;
	}
	/**
	 * @return Returns the buyingDealerCode.
	 */
	public String getBuyingDealerCode() {
		return WebUtils.padString(buyingDealerCode, 5);
	}
	/**
	 * @param buyingDealerCode The buyingDealerCode to set.
	 */
	public void setBuyingDealerCode(String buyingDealerCode) {
		this.buyingDealerCode = buyingDealerCode;
	}
	/**
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return WebUtils.padString(countryCode, 2);
	}
	/**
	 * @param countryCode The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	private String getFormattedTime(String format) {
		Calendar cal = Calendar.getInstance(); 
		DateFormat df = new SimpleDateFormat(format);
		Date today = cal.getTime();
		String date = df.format(today);
		return date;
	}
	/**
	 * @return Returns the intfDateSent.
	 */
	public String getIntfDateSent() {
		if(intfDateSent == null) {
			return getFormattedTime(dateFormat);
		}
		return WebUtils.padNumber(intfDateSent, 8, false);
	}
	/**
	 * @param intfDateSent The intfDateSent to set.
	 */
	public void setIntfDateSent(String intfDateSent) {
		this.intfDateSent = intfDateSent;
	}
	/**
	 * @return Returns the intfTimeSent.
	 */
	public String getIntfTimeSent() {
		if(intfTimeSent == null) {
			return getFormattedTime(timeFormat).substring(0, 8);
		}
		return WebUtils.padNumber(intfTimeSent, 8, false);
	}
	/**
	 * @param intfTimeSent The intfTimeSent to set.
	 */
	public void setIntfTimeSent(String intfTimeSent) {
		this.intfTimeSent = intfTimeSent;
	}
	/**
	 * @return Returns the itemNo.
	 */
	public String getItemNo() {
		return WebUtils.padString(itemNo, 20);
	}
	/**
	 * @param itemNo The itemNo to set.
	 */
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	/**
	 * @return Returns the lineNo.
	 */
	public String getLineNo() {
		return WebUtils.padString(lineNo, 3);
	}
	/**
	 * @param lineNo The lineNo to set.
	 */
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	/**
	 * @return Returns the promoDiscount.
	 */
	public String getPromoDiscount() {
		return WebUtils.padNumber(new BigDecimal(Double.parseDouble(promoDiscount)).setScale(2, BigDecimal.ROUND_HALF_UP).toString(), 9, false);
	}
	/**
	 * @param promoDiscount The promoDiscount to set.
	 */
	public void setPromoDiscount(String promoDiscount) {
		this.promoDiscount = promoDiscount;
	}
	/**
	 * @return Returns the salesOrderNo.
	 */
	public String getSalesOrderNo() {
		return WebUtils.padString(salesOrderNo, 6);
	}
	/**
	 * @param salesOrderNo The salesOrderNo to set.
	 */
	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}
	/**
	 * @return Returns the sellingDealerCode.
	 */
	public String getSellingDealerCode() {
		return WebUtils.padString(sellingDealerCode, 5);
	}
	/**
	 * @param sellingDealerCode The sellingDealerCode to set.
	 */
	public void setSellingDealerCode(String sellingDealerCode) {
		this.sellingDealerCode = sellingDealerCode;
	}
	/**
	 * @return Returns the shippedQuantity.
	 */
	public String getShippedQuantity() {
		return WebUtils.padNumber(new BigDecimal(Double.parseDouble(shippedQuantity)).setScale(2, BigDecimal.ROUND_HALF_UP).toString(), 9, false);
	}
	/**
	 * @param shippedQuantity The shippedQuantity to set.
	 */
	public void setShippedQuantity(String shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}
	/**
	 * @return Returns the trackingNo.
	 */
	public String getTrackingNo() {
		return WebUtils.padString(trackingNo,18);
	}
	/**
	 * @param trackingNo The trackingNo to set.
	 */
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	/**
	 * @return Returns the unitPrice.
	 */
	public String getUnitPrice() {
		return WebUtils.padNumber(new BigDecimal(Double.parseDouble(unitPrice)).setScale(2, BigDecimal.ROUND_HALF_UP).toString(), 9, false);
	}
	/**
	 * @param unitPrice The unitPrice to set.
	 */
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	/**
	 * @return Returns the intCorpCode.
	 */
	public String getIntfCorpCode() {
		return WebUtils.padString(intfCorpCode, 2);
	}
	/**
	 * @return Returns the intfRecTypeCode.
	 */
	public String getIntfRecTypeCode() {
		return WebUtils.padString(intfRecTypeCode, 4);
	}
	/**
	 * @return Returns the shippingMethod.
	 */
	public String getShippingMethod() {
		return WebUtils.padString(shippingMethod, 7);
	}
	
	public static void main(String[] args) {
		MQMessageDataBean msg = new MQMessageDataBean();
		msg.setUnitPrice("0000005.464");
		//log.debug(msg.getIntfDateSent());
		//log.debug(msg.getIntfTimeSent());
	}
	
	public String toString() {
		StringBuffer stringValue = new StringBuffer();
		
		stringValue.append(this.getIntfRecTypeCode())
					.append(this.getIntfCorpCode())
					.append(this.getIntfDateSent())
					.append(this.getIntfTimeSent())
					.append(this.getCountryCode())
					.append(this.getBuyingDealerCode())
					.append(this.getSellingDealerCode())
					.append(this.getSalesOrderNo())
					.append(this.getLineNo())
					.append(this.getItemNo())
					.append(this.getShippingMethod())
					.append(this.getTrackingNo())
					.append(this.getBoQuantity())
					.append(this.getShippedQuantity())
					.append(this.getUnitPrice())
					.append(this.getPromoDiscount());
		
		log.debug("total length of MQ message = " + stringValue.length());
		return stringValue.toString();
	}
}
