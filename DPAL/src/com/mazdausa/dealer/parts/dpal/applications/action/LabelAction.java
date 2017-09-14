package com.mazdausa.dealer.parts.dpal.applications.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.xerces.impl.dv.util.Base64;

import com.mazdausa.common.application.actions.EmazdaActionAbstract;
import com.mazdausa.common.log.EMDCSLogger;


public class LabelAction extends EmazdaActionAbstract {
	private static Logger log = EMDCSLogger.getLogger(LabelAction.class);
	protected ActionForward executeAction (
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws Exception{
		log.debug("Label Action: Entering into Label Action");
		
		HttpSession session = req.getSession(true);
		String imageStr = (String) session.getAttribute("image");
		
		resp.setContentType("image/gif");
		byte[] image = image(imageStr);
		resp.setContentLength(image.length);
		resp.getOutputStream().write(image);
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
		
		return mapping.findForward(null);
	}
	public byte[] image(String name) throws Exception {
		byte []image = null;
		image = Base64.decode(name);
		return image;
	}
}
