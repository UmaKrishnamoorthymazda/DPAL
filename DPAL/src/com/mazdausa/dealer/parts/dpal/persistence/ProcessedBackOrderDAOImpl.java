/*
 * Created on Dec 10, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import org.apache.log4j.Logger;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.common.util.ApplicationUtil;
import com.mazdausa.dealer.parts.dpal.applications.databeans.MessageOutputDataBean;
import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;
import com.mazdausa.dealer.parts.dpal.applications.util.parser.RecordParser;
import com.mazdausa.dealer.parts.dpal.persistence.dto.ProcessedBackOrderDTO;
import com.mazdausa.dealer.parts.dpal.services.DPALDAOException;

/**
 * @author InterraIT
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProcessedBackOrderDAOImpl extends ProcessedBackOrderDAOAbstract implements ProcessedBackOrderDAO{
	private static Logger log = EMDCSLogger.getLogger(ProcessedBackOrderDAOImpl.class);
	/* (non-Javadoc)
	 * @see com.mazdausa.dealer.parts.dpal.persistence.BackOrderDAO#findDTO(java.lang.String, java.lang.String, int, int, java.lang.String)
	 */
	public ProcessedBackOrderDTO findDTO(String countryCode, String dealerCode, String statusFlag, 
			String pageNumber, String pageSize, String sortType) throws DPALDAOException{
		log.debug("ProcessedBackOrderDAOImpl : findDTO : statusFlag = " + statusFlag);
		ProcessedBackOrderDTO dto = null;
		String result;
		try {
			log.debug("ProcessedBackOrderDTO: " + countryCode + " " + dealerCode + " " + statusFlag + " " 
					+ pageNumber + " " + pageSize + " " + sortType);
			countryCode = "us";
			result = (String)this.callStoredProcedure("SB60013", countryCode, dealerCode, Integer.parseInt(pageNumber), Integer.parseInt(pageSize), sortType);
			String outputMessage = this.getOutputMessage();
			log.debug("ProcessedBackOrderDTO: Result: " + (result != null?result.trim():result));
			dto = createProcessedBackOrderDTO(result, outputMessage, statusFlag);			
		} catch(Exception e) {
			e.printStackTrace();
			throw new DPALDAOException(e);
		}
		log.debug("ProcessedBackOrderDAOImpl : findDTO : [finddto]: dto = " + dto);
		return dto;
	}
	
	private ProcessedBackOrderDTO createProcessedBackOrderDTO(String result, String outputMessage, String statusFlag) throws Exception{
		ProcessedBackOrderDTO dto = null;
		
		RecordParser parser = new RecordParser("MessageOutput");
		log.debug("parser: " + parser);
		MessageOutputDataBean msg = (MessageOutputDataBean)parser.parseRecords(outputMessage);
		log.debug("record parsed. result = " + msg);
		
		if(msg == null || !msg.isSuccess()) return null;
		
		String parserName = WebUtils.getRecordParserNameForFlag(statusFlag);
		log.debug("BackOrderDAOImpl : createBackOrderDTO: parser name = " + parserName);
		RecordParser rp = new RecordParser(parserName);
		try {
			// skip the first few bytes that hold the total record count
			String recordCountLength = ApplicationUtil.getSystemProperty("DPAL", "record.total.count.length");
			dto = (ProcessedBackOrderDTO)rp.parseRecords(result.substring(/*4*/Integer.parseInt(recordCountLength)));
			
			// set the total record count in the DTO
			dto.setTotalRecordsCount(Integer.parseInt(result.substring(0,Integer.parseInt(recordCountLength))));
		}catch(Exception e){
			e.printStackTrace();
		}
		return dto;
	}
}
