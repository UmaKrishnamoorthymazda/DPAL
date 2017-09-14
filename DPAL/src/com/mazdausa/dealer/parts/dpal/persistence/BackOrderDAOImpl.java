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
import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;
import com.mazdausa.dealer.parts.dpal.applications.util.parser.RecordParser;
import com.mazdausa.dealer.parts.dpal.persistence.dto.BackOrderDTO;
import com.mazdausa.dealer.parts.dpal.services.DPALDAOException;

/**
 * @author InterraIT
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BackOrderDAOImpl extends BackOrderDAOAbstract implements BackOrderDAO{
	private static Logger log = EMDCSLogger.getLogger(BackOrderDAOImpl.class);
	/* (non-Javadoc)
	 * @see com.mazdausa.dealer.parts.dpal.persistence.BackOrderDAO#findDTO(java.lang.String, java.lang.String, int, int, java.lang.String)
	 */
	public BackOrderDTO findDTO(String countryCode, String dealerCode, String statusFlag, 
			String pageNumber, String pageSize, String sortType) throws DPALDAOException{
		log.debug("BackOrderDAOImpl : findDTO : statusFlag = " + statusFlag + ", pageNumber = " + pageNumber + ", pageSize = " + pageSize);
		BackOrderDTO dto = null;
		String result;
		try {			
			result = (String)this.callStoredProcedure("SB60010", countryCode, dealerCode, statusFlag, Integer.parseInt(pageNumber), Integer.parseInt(pageSize), sortType);
			log.debug("BackOrderDTO: Result: " + (result!=null?result.trim():result));
			if(result != null)
				dto = createBackOrderDTO(result, statusFlag);			
		} catch(Exception e) {
			e.printStackTrace();
			throw new DPALDAOException(e);
		}
		log.debug("BackOrderDAOImpl : findDTO : [finddto]: dto = " + dto);
		return dto;
	}
	
	private BackOrderDTO createBackOrderDTO(String result, String statusFlag) throws Exception{
		BackOrderDTO dto = null;
		log.debug("createBackOrderDTO:: result = " + result);
		
		String parserName = WebUtils.getRecordParserNameForFlag(statusFlag);
		log.debug("BackOrderDAOImpl : createBackOrderDTO: parser name = " + parserName);
		RecordParser rp = new RecordParser(parserName);
		try {
			// skip the first few bytes that hold the total record count
			String recordCountLength = ApplicationUtil.getSystemProperty("DPAL", "record.total.count.length");
			dto = (BackOrderDTO)rp.parseRecords(result.substring(/*4*/Integer.parseInt(recordCountLength)));
			
			// set the total record count in the DTO
			if(dto != null)
				dto.setTotalRecordsCount(Integer.parseInt(result.substring(0,Integer.parseInt(recordCountLength))));
		}catch(Exception e){
			e.printStackTrace();
			// throw exception so that it is propagated to the global exception handler
			throw e;
		}
		return dto;
	}
}
