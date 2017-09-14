package com.mazdausa.dealer.parts.dpal.applications.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.mazdausa.common.application.actions.UserContext;
import com.mazdausa.common.ldap.command.LDAPServiceImpl;
import com.mazdausa.common.ldap.domain.LDAPPerson;
import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.common.user.model.User;
import com.mazdausa.common.util.ApplicationUtil;
import com.mazdausa.dealer.parts.dpal.applications.databeans.StatusUpdateDataBean;

public class WebUtils {
	
	private static Logger log = EMDCSLogger.getLogger(WebUtils.class);
	/*private static HttpServletRequest req;*/
	
	
	/*public static void setRequest(HttpServletRequest request) {
		req = request;
	}*/
	public static void setUserContext(UserContext userContext) {
	}
	public static String getImagesFolder(/*HttpServletRequest request*/) {
		return getFolder(/*request,*/ DPALConstants.IMG_DIR);
	}
	
	public static String getCssFolder(/*HttpServletRequest request*/) {
		return getFolder(/*request,*/ DPALConstants.CSS_DIR);
	}
	
	public static String getJsFolder(/*HttpServletRequest request*/) {
		return getFolder(/*request,*/ DPALConstants.JS_DIR);
	}
	
	private static String getFolder(/*HttpServletRequest request,*/ String folder) {
		return /*request.getContextPath()*/ DPALConstants.CONTEXT_PATH + DPALConstants.RESOURCE_BASE_DIR + folder;
	}
	
	public static String getAppUrl(HttpServletRequest request, String url) {
		StringBuffer returnUrl = new StringBuffer();
		if(url.indexOf("?") == -1) {
			returnUrl.append(url).append("?");
		}
		returnUrl.append("&fromCntryCd=").append(request.getParameter("fromCntryCd"));
		returnUrl.append("&language=").append(request.getParameter("language"));
		
		return returnUrl.toString();
	}
	
	public static String getDealerCode(HttpServletRequest request, UserContext userContext) {
		log.debug("getting dealer code");
		String dealerCode = null;
		if(WebUtils.isDebug()) {
			log.debug("debug mode is on. getting from props file");
			dealerCode = ApplicationUtil.getSystemProperty("DPAL", "debug.selling.dealer");
		}
		else {
			log.debug("debug mode is off. Retrieving from session");
			if(!isDealer(request, userContext)) {
				// return the dealer code that was chosen by the non-dealer user
				log.debug("Getting from session");
				dealerCode = (String)request.getSession().getAttribute("selectedDealer");
			}
			else {
				log.debug("Getting from user context");
				// return the actual logged on dealer
				dealerCode = userContext.getUser().getLoctnCd();
			}
		}
		
		return dealerCode;
	}
	
	public static boolean isDealer(HttpServletRequest request, UserContext userContext) {
		log.debug("checking isDealer"); 
		if(WebUtils.isDebug()) return true;
		User user = userContext.getUser();
		if(user.isCorporatePerson() || user.isDistrictPerson() || user.isRegionalPerson())
			return false;
		else
			return true;
	}
	
	public static String getWSLId(HttpServletRequest request, UserContext userContext) {
		String wsl = null;
		
		if(WebUtils.isDebug()) {
			log.debug("debug mode is on. getting from props file");
			wsl = ApplicationUtil.getSystemProperty("DPAL", "debug.selling.wslId");
		}
		else{
			User loggedOnUser = userContext.getUser();
			wsl = loggedOnUser.getUserid();
		}
		return wsl;
	}
	
	public static String getPageSize() {
		return ApplicationUtil.getSystemProperty("DPAL", "view.page.size");
	}
	
	public static String getShippingConfirmationPageSize() {
		return ApplicationUtil.getSystemProperty("DPAL", "shipping.page.size");
	}
	
	public static String getDSortIndicatorImage(/*HttpServletRequest request*/) {
		//HttpServletRequest r = (request == null)?req:request; 
		return getImagesFolder(/*r*/) + "/sort-dsc.png";
	}
	public static String getASortIndicatorImage(/*HttpServletRequest request*/) {
		// HttpServletRequest r = (request == null)?req:request; 
		return getImagesFolder(/*r*/) + "/sort-asc.png";
	}
	
	public static String getRecordParserNameForFlag(String statusFlag) {
		String parserName = null;
		if("O".equals(statusFlag)) {
			parserName = ApplicationUtil.getSystemProperty("DPAL", "parser.current.backorder.list");
		} else if("W".equals(statusFlag)) {
			parserName = ApplicationUtil.getSystemProperty("DPAL", "parser.committed.orders.awaiting.shipment");
		} else if("G".equals(statusFlag)) {
			parserName = ApplicationUtil.getSystemProperty("DPAL", "parser.shipping.confirmation");
		} else if("P".equals(statusFlag)) {
			parserName = ApplicationUtil.getSystemProperty("DPAL", "parser.today.shipped.part.list");
		} 
		return parserName;
	}
	
	public static ArrayList getErrorIds(ArrayList /*StatusUpdateDataBean*/ beanList) {
		// to ensure that a null is returned if no error ids are found
		ArrayList errorIdList = null;
		
		for(Iterator it = beanList.iterator(); it.hasNext(); ) {
			if(errorIdList == null) errorIdList = new ArrayList();
			StatusUpdateDataBean bean  = (StatusUpdateDataBean) it.next();
			
			if("".equals(bean.getMessageOutput().getMessage().trim())) {
				errorIdList.add(bean.getSalesOrder() + "|" + bean.getLineNumber());
			}
		}
		
		return errorIdList;
	}
	
	// Used as a taglib function
	public static boolean contains(Collection coll, Object o){
		log.debug("WebUtils: contains: coll = " + coll);
		log.debug("WebUtils: contains: o = " + o);
		
		boolean ret = false;
		if(coll != null) 		
			ret = coll.contains(o);
		
		log.debug("WebUtils: contains: returning: " + ret);
		return ret;
	}
	
	// Used as a taglib function
	public static String getMapValueForKey(Map map, String key) {
		log.debug("WebUtils: getMapValueForKey: key: " + key);
		String ret = null;
		if(map == null) 
			ret = null;
		else 
			if(map.get(key)!=null)
				ret = map.get(key).toString();
		
		log.debug("WebUtils: getMapValueForKey: returning: " + ret);
		return ret;
	}
	
	// Used as a taglib function
	public static boolean compareMapValueForKey(Map map, String key, String compareValue) {
		boolean ret = false;
		
		if(compareValue == null) ret = false;
		else if(compareValue.equals(getMapValueForKey(map, key))) ret = true;
		
		return ret;
	}
	
	// Used as a taglib function
	public static String getTodaysDate() {
		Calendar cal = Calendar.getInstance(); 
		// Modified to fix Bug id: 15
		//DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date today = cal.getTime();
		String date = df.format(today);
		return date;
	}
	
	public static String convertToCommaDelimited(String[] list) {
        StringBuffer ret = new StringBuffer("");
        for (int i = 0; list != null && i < list.length; i++) {
            ret.append(list[i]);
            if (i < list.length - 1) {
                ret.append(',');
            }
        }
        return ret.toString();
    }
	
	public static boolean isDebug() {
		Boolean b = new Boolean(ApplicationUtil.getSystemProperty("DPAL", "debug"));
		return b.booleanValue();
	}
	
	/* used also as a taglib function */
	public static String getEmailByWSLId(String wslId) {
		String email = null;
		try {
			LDAPServiceImpl ldapService = LDAPServiceImpl.getInstance();
			LDAPPerson person = ldapService.findActiveUserByUserId(wslId);
			email = person.getEmail();
		} catch(Exception e) {
			log.debug("Unable to find email id for wsl: " + wslId);
		}
		return email;
	}
	
	/* used as a taglib function */
	public static boolean isValidEmail(String emailId) {
		/*Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+[\\._-]?[a-zA-Z0-9]+@[a-zA-Z0-9]+[\\._-]?[a-zA-Z0-9]+\\.[a-zA-Z]{2,5}$");
		Matcher matcher = pattern.matcher(emailId);
		return matcher.matches();*/
		
		return Pattern.matches("^[a-zA-Z0-9]+[\\._-]?[a-zA-Z0-9]+@[a-zA-Z0-9]+[\\._-]?[a-zA-Z0-9]+\\.[a-zA-Z]{2,5}$", emailId);
	}
	
	/* used as a taglib function */
	public static boolean compareString(String str1, String str2) {
		return str1.equals(str2);
	}
	
	public static String getCountryCode(HttpServletRequest req) {
		if(isDebug())
			return "US";
		return (String)req.getSession().getAttribute("countryCode");
		//return userContext.getUserLocale().getCountry();
	}
	
	public static String getLanguage(HttpServletRequest req) {
		if(isDebug())
			return "en";
		return (String)req.getSession().getAttribute("language");
	}
	
	/* used as a taglib function */
	public static int getListSize(List list) {
		return list == null? 0: list.size();
	}
	
	public static boolean showStackTrace() {
		log.debug("ApplicationUtil.getSystemProperty(\"DPAL\", \"debug.error.showstacktrace\") = " + ApplicationUtil.getSystemProperty("DPAL", "debug.error.showstacktrace"));
		log.debug("equivalent boolean value = " + Boolean.valueOf(ApplicationUtil.getSystemProperty("DPAL", "debug.error.showstacktrace")).booleanValue());
		return Boolean.valueOf(ApplicationUtil.getSystemProperty("DPAL", "debug.error.showstacktrace")).booleanValue();
	}
	
	public static String padString(String string, int size) {
		String padChar = " ";
		// for character data, pad with space on right
		StringBuffer sb = new StringBuffer(string);
		int len = string.length();
		if(len < size) {
			for(int z=0; z<size-len; z++){
				sb.append(padChar);
			}
		}
		
		return sb.toString();
	}
	
	public static String padNumber(String number, int size, boolean truncate) {
		String padChar = "0";
		// 1. for numbers, remove decimal point
		// 2. for number, pad with zero on left
		StringBuffer num = new StringBuffer(number);
		
		// when truncate is true, truncate everything after decimal point
		// when truncate is false, just remove the decimal point
		if(truncate) {
			if(num.indexOf(".") > -1) 
				num = new StringBuffer(num.substring(0, num.indexOf(".")));
		} else if(num.indexOf(".") > -1) num.deleteCharAt(num.indexOf("."));
		
		StringBuffer sb = new StringBuffer(); 
		int len = /*Integer.toString(number)*/num.length();
		if(len < size) {
			for(int z=0; z<size-len; z++){
				sb.append(padChar);
			}
		}
		sb.append(num);
		
		return sb.toString();
	}

	public static String getLinkURL(String linkName) {
		return ApplicationUtil.getSystemProperty("APPLICATION_URLS", linkName);
	}
}
