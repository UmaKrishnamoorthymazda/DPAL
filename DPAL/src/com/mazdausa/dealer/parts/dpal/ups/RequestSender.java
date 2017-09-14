/*
 * Created on Dec 24, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.ups;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.common.util.ApplicationUtil;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RequestSender {
	
	private static Logger log = EMDCSLogger.getLogger(RequestSender.class);
	private String url;
	private String xml;
	
	public RequestSender(String url, String xml) {
		this.url = url;
		this.xml = xml;
	}

	public String sendRequest() throws Exception{
		StringBuffer responseBuffer = new StringBuffer();
		
		String userId = "";
		String password = "";
		String proxyServer = "";
		int proxyPort = 0;
		
		HttpClient httpclient = new HttpClient();
		
		String useProxy = ApplicationUtil.getSystemProperty("COMMON", "USE_PROXY");
		log.debug("use proxy = " + useProxy);
		if(useProxy != null && useProxy.toLowerCase().equals("yes")) {
			userId = ApplicationUtil.getSystemProperty("COMMON", "MNAO_PROXY_ID");
			password = ApplicationUtil.getSystemProperty("COMMON", "MNAO_PROXY_PWD");
			proxyServer = ApplicationUtil.getSystemProperty("COMMON", "MNAO_PROXY_SERVER");
			proxyPort = new Integer(ApplicationUtil.getSystemProperty("COMMON", "MNAO_PROXY_PORT")).intValue();
			
			log.debug("using proxy " + proxyServer + ":" + proxyPort);
			
			httpclient.getHostConfiguration().setProxy(proxyServer, proxyPort);
			httpclient.getState().setProxyCredentials(
					new AuthScope(proxyServer, proxyPort),
					new UsernamePasswordCredentials(userId, password));		
		}
		
		PostMethod httppost = new PostMethod(url);
		httppost.setRequestEntity(new StringRequestEntity(xml, "text/xml", null));
		try {
			httpclient.executeMethod(httppost);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httppost.getResponseBodyAsStream()));
			
			int letter = 0;
			while ((letter = reader.read()) != -1) {
				responseBuffer.append((char) letter);
			}
			//this.getUpsShippingPrice(responseBuffer.toString());
			log.debug("\n\nUPS Response" + responseBuffer.toString());
		} catch (Exception e) {
			log.debug("Exception: " + e);
		} finally {
			httppost.releaseConnection();
		}
		return responseBuffer.toString();
	}	
	
	
	// Getters-Setters
	
	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return Returns the xml.
	 */
	public String getXml() {
		return xml;
	}
	/**
	 * @param xml The xml to set.
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}
}
