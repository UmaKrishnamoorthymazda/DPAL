/*
 * Created on Dec 16, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.dealer.parts.dpal.applications.databeans.MessageOutputDataBean;
import com.mazdausa.dealer.parts.dpal.applications.databeans.StatusUpdateDataBean;
import com.mazdausa.dealer.parts.dpal.applications.util.parser.RecordParser;
import com.mazdausa.dealer.parts.dpal.persistence.dto.StatusUpdateDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.StatusUpdateDTOImpl;
import com.mazdausa.dealer.parts.dpal.services.DPALDAOException;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StatusUpdateDAOImpl extends StatusUpdateDAOAbstract implements StatusUpdateDAO{
	private static Logger log = EMDCSLogger.getLogger(StatusUpdateDAOImpl.class);
	/* (non-Javadoc)
	 * @see com.mazdausa.dealer.parts.dpal.persistence.BackOrderCommitDAO#findDTO(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public StatusUpdateDTO findDTO(String countryCode, String dealerCode,
			String idList, String wslId, String statusFlag, String previousStatusFlag) throws DPALDAOException{
		
		StatusUpdateDTO dto = new StatusUpdateDTOImpl();
		//ArrayList /*MessageOutputDataBean*/ msgOutputList = new ArrayList();
		StatusUpdateDataBean bean = null;
		String result = null;
		String[] ids = idList.split(",");
		
		for(int z=0; z<ids.length; z++) {
			String[] id = ids[z].split("\\|");
			try {
				result = this.callStoredProcedure("SB60008", countryCode, dealerCode, 
						id[0], id[1], wslId, statusFlag, previousStatusFlag);
				log.debug(z + ". creating status update dto");
				bean = createStatusUpdateDTO(id[0], id[1], result);
				log.debug(z + ". status update dto created. now adding to dto");
				// Modified to fix Bug id: 2
				//dto.setMessageOutputDataBean(bean);
				//msgOutputList.add(bean);
				dto.addStatusUpdateDataBean(bean);
				log.debug("added to dto. checking if this update was success");
				if(!bean.getMessageOutput().isSuccess()) {
					dto.setUpdateSuccess(false);
					dto.addErrorId(bean.getSalesOrder() + "|" + bean.getLineNumber());					
				}
				log.debug(z + ". done");
			} catch(Exception e) {
				throw new DPALDAOException(e);
			}
		}
		//dto.setMessageOutputDataBean(msgOutputList);
		log.debug(" StatusUpdateDAOImpl : findDTO : now returning");
		return dto;
	}
	
	public StatusUpdateDTO findDTO(String countryCode, String dealerCode,
			String idList, String wslId, String dpboStatusFlag, String dpdrStatusFlag, String shippedQuantity) throws DPALDAOException{
		log.debug("Params: " + "countryCode = " + countryCode +  ", dealerCode = " + dealerCode +  ", idList = " + idList +  ", wslId = " + wslId +  ", dpboStatusFlag = " + dpboStatusFlag +  ", dpdrStatusFlag = " + dpdrStatusFlag +  ", shippedQuantity = " + shippedQuantity);
		StatusUpdateDTO dto = new StatusUpdateDTOImpl();
		// Modified to fix Bug id: 2
		//MessageOutputDataBean bean = null;
		StatusUpdateDataBean bean = null;
		
		String result = null;
		String[] ids = idList.split(",");
		
		for(int z=0; z<ids.length; z++) {
			String[] id = ids[z].split("\\|");
			try {
				log.debug("Calling SB60009 with: " + "countryCode = " + countryCode +  ", dealerCode = " + dealerCode +  ", id[0] = " + id[0] + ", id[1] = " + id[1] + ", wslId = " + wslId +  ", dpboStatusFlag = " + dpboStatusFlag +  ", dpdrStatusFlag = " + dpdrStatusFlag +  ", shippedQuantity = " + shippedQuantity);
				result = this.callStoredProcedure("SB60009", countryCode, dealerCode, 
						id[0], id[1], wslId, dpboStatusFlag, dpdrStatusFlag, shippedQuantity);
				log.debug(z + ". creating status update dto");
				bean = createStatusUpdateDTO(id[0], id[1], result);
				//dto = new StatusUpdateDTOImpl();
				//dto.setMessageOutputDataBean(bean);
				dto.addStatusUpdateDataBean(bean);
				if(!bean.getMessageOutput().isSuccess()) {
					dto.setUpdateSuccess(false);
					dto.addErrorId(bean.getSalesOrder() + "|" + bean.getLineNumber());					
				}
				//log.debug(z + ". status update dto created. now adding to dto");
				//dto.setMessageOutputDataBean(bean);
				log.debug(z + ". done");
			} catch(Exception e) {
				throw new DPALDAOException(e);
			}
		}
		log.debug(" StatusUpdateDAOImpl : findDTO : now returning");
		return dto;
	}
	
	// Modified to fix Bug id: 2
	//private MessageOutputDataBean createStatusUpdateDTO(String salesOrder, String lineNumber, String result) throws Exception{
	private StatusUpdateDataBean createStatusUpdateDTO(String salesOrder, String lineNumber, String result) throws Exception{
		log.debug("createStatusUpdateDTO: [" + salesOrder + ", " + lineNumber + ", " + result);
		StatusUpdateDataBean bean = new StatusUpdateDataBean();
		log.debug("setting sales order");
		bean.setSalesOrder(salesOrder);
		log.debug("setting line number");
		bean.setLineNumber(lineNumber);
		log.debug("parsing record");
		RecordParser parser = new RecordParser("MessageOutput");
		log.debug("parser: " + parser);
		MessageOutputDataBean msg = (MessageOutputDataBean)parser.parseRecords(result);
		log.debug("record parsed. result = " + msg);
		bean.setMessageOutput(msg);
		
		// Modified to fix Bug id: 2
		//return msg;
		return bean;
	}
}
