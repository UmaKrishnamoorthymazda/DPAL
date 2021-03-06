package com.mazdausa.dealer.parts.dpal.applications.common;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;

public class GlobalExceptionHandler extends ExceptionHandler{

 private static final Logger logger = 
     Logger.getLogger(GlobalExceptionHandler.class);

 public ActionForward execute(Exception ex, ExceptionConfig ae,
	ActionMapping mapping, ActionForm formInstance,
	HttpServletRequest request, HttpServletResponse response)
	throws ServletException {

	//log the error message
	logger.error(ex);
	if(WebUtils.showStackTrace())
		request.setAttribute("error", ex);
	return super.execute(ex, ae, mapping, formInstance, request, response);
 }
}