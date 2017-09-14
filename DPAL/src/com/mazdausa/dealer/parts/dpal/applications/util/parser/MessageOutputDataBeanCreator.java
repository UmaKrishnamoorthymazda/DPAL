/*
 * Created on Dec 16, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.applications.util.parser;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.dealer.parts.dpal.applications.databeans.MessageOutputDataBean;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MessageOutputDataBeanCreator implements DTOCreator {
	private static Logger log = EMDCSLogger.getLogger(MessageOutputDataBeanCreator.class);
	/* (non-Javadoc)
	 * @see com.mazdausa.dealer.parts.dpal.applications.util.parser.DTOCreator#createDTO(java.util.ArrayList)
	 */
	public Object createDTO(ArrayList parsedRecords) {
		MessageOutputDataBean modb = new MessageOutputDataBean();
		String[] data = (String[])parsedRecords.get(0);
		
		modb.setStatus(data[0]); log.debug("MessageOutputDataBeanCreator]: 0 = " + data[0]);
		modb.setSqlCode(data[1]); log.debug("MessageOutputDataBeanCreator]: 1 = " + data[1]);
		modb.setMessage(data[2]); log.debug("MessageOutputDataBeanCreator]: 2 = " + data[2]);
		
		log.debug("MessageOutputDataBeanCreator : createDTO : bean created. returning bean");
		return modb;
	}

}
