/*
 * Created on Jan 7, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.apache.log4j.Logger;

import com.mazdausa.common.DAO.JDBCDAOAbstract;
import com.mazdausa.common.constants.CommonConstants;
import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.common.util.ApplicationUtil;
import com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean;
import com.mazdausa.dealer.parts.dpal.applications.databeans.MessageOutputDataBean;
import com.mazdausa.dealer.parts.dpal.applications.util.parser.RecordParser;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailUpdateDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerDetailUpdateDTOImpl;
import com.mazdausa.dealer.parts.dpal.services.DPALConnectionException;
import com.mazdausa.dealer.parts.dpal.services.DPALDAOException;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealerDetailUpdateDAOImpl extends JDBCDAOAbstract implements DealerDetailUpdateDAO {
	private static Logger log = EMDCSLogger.getLogger(DealerDetailUpdateDAOImpl.class);
	
	public Connection getConnection() throws DPALConnectionException{
		try {
			return super.getConnection(CommonConstants.EMDCS_CONFIG_PROP_COMMON_DATASOURCE_REF_DB01); //
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error("Exception in getConnectionFromFramework method: " + e);
			throw new DPALConnectionException("Exception in getConnection method: " + e);
		}
	}
	
	protected String callStoredProcedure(String storedProcedureName, String countryCode, String buyingDealerCode, String wslId, DealerDataBean bean) throws Exception { 
		Connection connection = null;
		CallableStatement statement = null;
		String result = null;
		
		try {
			connection = this.getConnection("mazdaDS"); //retrieved from xml file
			
			//[Jeril]: Uncomment the following block and modify as per requirement
			
			String schemaName = ApplicationUtil.getDB2MainframeSchemaName();
			
			//String callString = "{ call " + schemaName + "." + storedProcedureName + " (?, ?, ?, ?, ?, ?) }";
			String callString = "{ call " + schemaName + "." + storedProcedureName + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			log.debug("DealerDetailUpdateDAOImpl: " + callString + " " + buyingDealerCode);
			
			//TODO: Remove this statement
			//countryCode = "US";
			log.debug("DealerDetailUpdateDAOImpl: callStoredProcedure: printing params");
			log.debug("storedProcedureName = " + storedProcedureName);
			log.debug("countryCode = " + countryCode);
			log.debug("buyingDealerCode = " + buyingDealerCode);
			log.debug("wslId = " + wslId);
			log.debug("contact Name = " + bean.getContactName());
			log.debug("contact Name = " + bean.getContactNumber());
			log.debug("ship 1" + bean.getShippingDealer()[0]);
			log.debug("ship 2" + bean.getShippingDealer()[1]);
			log.debug("ship 3" + bean.getShippingDealer()[2]);
			log.debug("ship 4" + bean.getShippingDealer()[3]);
			log.debug("ship 5" + bean.getShippingDealer()[4]);
			log.debug("buy 1" + bean.getBuyingDealer()[0]);
			log.debug("buy 2" + bean.getBuyingDealer()[1]);
			log.debug("buy 3" + bean.getBuyingDealer()[2]);
			log.debug("buy 4" + bean.getBuyingDealer()[3]);
			log.debug("buy 5" + bean.getBuyingDealer()[4]);
			
			
			statement = connection.prepareCall(callString);
			statement.setString(1, countryCode);
			statement.setString(2, buyingDealerCode);
			statement.setString(3, bean.getShippingDealer()[0]);
			statement.setString(4, bean.getShippingDealer()[1]);
			statement.setString(5, bean.getShippingDealer()[2]);
			statement.setString(6, bean.getShippingDealer()[3]);
			statement.setString(7, bean.getShippingDealer()[4]);
			statement.setString(8, bean.getBuyingDealer()[0]);
			statement.setString(9, bean.getBuyingDealer()[1]);
			statement.setString(10, bean.getBuyingDealer()[2]);
			statement.setString(11, bean.getBuyingDealer()[3]);
			statement.setString(12, bean.getBuyingDealer()[4]);
			statement.setString(13, bean.getContactName());
			statement.setString(14, bean.getContactNumber());
			statement.setString(15, wslId);
			statement.registerOutParameter(16, Types.VARCHAR);
			
			statement.execute();
			
			/*String*/ result = statement.getString(16);
			log.debug("DealerDetailUpdateImpl: callStoredProcedure: result = " + result);
			
			//return result;
		} catch(Exception e) {
			e.printStackTrace();
		}// Added to fix Bug id: 12 & 13
		finally {
			 try {
		      	if (statement != null) {
		       		statement.close();
		       	}
		       	if (connection != null) {
		       		if (!connection.getAutoCommit()) {
		       			connection.commit();
		       		}
		       		connection.close();
		       	}
			 } catch (Exception e) {
		       	log.error("An exception occured while closing the connection/statement", e);
		        e.printStackTrace();
			 }
		}
		//return null;
		return result;
	}
	
	private MessageOutputDataBean createStatusUpdateBean(String result) throws Exception{
		log.debug("DealerDetailUpdateDAOImpl: createStatusUpdateDTO: result = " + result);
		//StatusUpdateDataBean bean = new StatusUpdateDataBean();
		
		log.debug("DealerDetailUpdateDAOImpl: createStatusUpdateDTO: parsing record");
		RecordParser parser = new RecordParser("MessageOutput");
		log.debug("DealerDetailUpdateDAOImpl: createStatusUpdateDTO: parser: " + parser);
		MessageOutputDataBean msg = (MessageOutputDataBean)parser.parseRecords(result);
		log.debug("DealerDetailUpdateDAOImpl: createStatusUpdateDTO: record parsed. result = " + msg);
		//bean.setMessageOutput(msg);
		
		return msg;
	}	
	/* (non-Javadoc)
	 * @see com.mazdausa.dealer.parts.dpal.persistence.DealerDetailUpdateDAO#findDTO(java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean)
	 */
	public DealerDetailUpdateDTO findDTO(String countryCode, String buyingDealerCode, String wslId, DealerDataBean bean) throws DPALDAOException {
		log.debug("DealerDetailUpdateDAOImpl: findDTO: entered");
		String result = null;
		DealerDetailUpdateDTO dto = null;
		
		try {
			log.debug("DealerDetailUpdateDAOImpl: findDTO: calling stored proc");
			result = this.callStoredProcedure("SB60016", countryCode, buyingDealerCode, wslId, bean);
			log.debug("DealerDetailUpdateDAOImpl: findDTO: result = " + result);
			MessageOutputDataBean statusUpdateBean = createStatusUpdateBean(result);
			dto = new DealerDetailUpdateDTOImpl();
			dto.setMessageOutputDataBean(statusUpdateBean);
		} catch(Exception e) {
			log.error("An exception occured while attempting to update dealer details", e);
		}
		
		return dto;
	}
}
