/*
 * Created on Dec 10, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.log4j.Logger;

import com.mazdausa.common.DAO.JDBCDAOAbstract;
import com.mazdausa.common.constants.CommonConstants;
import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.dealer.parts.dpal.services.DPALConnectionException;
import com.mazdausa.common.util.ApplicationUtil;

/**
 * @author InterraIT
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProcessedBackOrderDAOAbstract extends JDBCDAOAbstract {
	private static Logger log = EMDCSLogger.getLogger(ProcessedBackOrderDAOAbstract.class);
	private String outputMessage;
	public Connection getConnection() throws DPALConnectionException{
		try {
			return super.getConnection(CommonConstants.EMDCS_CONFIG_PROP_COMMON_DATASOURCE_REF_DB01); //
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Exception in getConnectionFromFramework method: " + e);
			throw new DPALConnectionException("Exception in getConnection method: " + e);
		}
	}
	
	protected String callStoredProcedure(String storedProcedureName, String countryCode, String dealerCode,
			int pageNumber, int pageSize, String sortType) throws Exception {
		Connection connection = null;
		CallableStatement statement = null;
		String result = null;

		try {
			connection = this.getConnection("mazdaDS"); //retrieved from xml file
			
			String schemaName = ApplicationUtil.getDB2MainframeSchemaName();
			log.debug("ProcessedBackOrderDAOAbstract: schemaName: " + schemaName);
			log.debug("ProcessedBackOrderDAOAbstract: schemaName: " + storedProcedureName + " "
					+ countryCode + " " + dealerCode + " " + pageNumber + 
					" " + pageSize + " " + sortType);
			//String callString = "{ call " + schemaName + "." + storedProcedureName + " (?,?,?,?,?,?) }";
			String callString = "{ call  " + schemaName + "." + storedProcedureName + "(?, ?, ?, ?, ?, ?, ?) }";
			log.debug(callString);
			
			NumberFormat formatter = new DecimalFormat("000");
			String strPageNumber = formatter.format(pageNumber);
			
			formatter = new DecimalFormat("00");
			String strPageSize = formatter.format(pageSize);
			
			log.debug("ProcessedBackOrderDAOAbstract: pageNumber and size: " + 
					strPageNumber + " " + strPageSize);
			statement = connection.prepareCall(callString);
			//TODO: Remove this statement
			//countryCode = "US";
			
			statement.setString(1, countryCode.toUpperCase());
			statement.setString(2, dealerCode.toUpperCase());
			statement.setString(3, sortType.toUpperCase());
			statement.setString(4, strPageNumber);
			statement.setString(5, strPageSize);
			statement.registerOutParameter(6, Types.VARCHAR);
			statement.registerOutParameter(7, Types.VARCHAR);
			statement.execute();
			/*statement.setString(1, "US");
			statement.setString(2, "SELL2");
			statement.setString(3, "OA");
			statement.setString(4, "001");
			statement.setString(5, "10");
			statement.registerOutParameter(6, Types.VARCHAR);
			statement.registerOutParameter(7, Types.VARCHAR);
			statement.execute();*/
			
			//TODO: ERROR HANDLING
			String outputString1 = statement.getString(6);
			this.outputMessage = statement.getString(6);
			// Added to rectify condition when no data is found
			result = (statement.getString(7) != null && statement.getString(7).startsWith("0000"))? "" : statement.getString(7);
			
			log.debug("ProcessedBackOrderDAOAbstract: outputString1: " + outputString1);
			log.debug("ProcessedBackOrderDAOAbstract: outputString2: " + /*outputString2.trim()*/ result);
			
			//String result = outputString2;
			//return result;
			
		} catch(Exception e) {
			e.printStackTrace();
		} // Added to fix Bug id: 12 & 13
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
	
	/**
	 * @return Returns the outputMessage.
	 */
	public String getOutputMessage() {
		return outputMessage;
	}
	/**
	 * @param outputMessage The outputMessage to set.
	 */
	public void setOutputMessage(String outputMessage) {
		this.outputMessage = outputMessage;
	}
}
