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
public class BackOrderDAOAbstract extends JDBCDAOAbstract {
	private static Logger log = EMDCSLogger.getLogger(BackOrderDAOAbstract.class);
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
			String statusFlag, int pageNumber, int pageSize, String sortType) throws Exception {
		Connection connection = null;
		CallableStatement statement = null;
		String result = null;
		
		try {
			connection = this.getConnection("mazdaDS"); //retrieved from xml file
			
			String schemaName = ApplicationUtil.getDB2MainframeSchemaName();
			log.debug("BackOrderDAOAbstract: schemaName: " + schemaName);
			log.debug("BackOrderDAOAbstract: schemaName: " + storedProcedureName + " "
					+ countryCode + " " + dealerCode + " " + statusFlag + " " + pageNumber + 
					" " + pageSize + " " + sortType);

			String callString = "{ call  " + schemaName + "." + storedProcedureName + "(?, ?, ?, ?, ?, ?, ?, ?) }";
			log.debug(callString + ":: " + dealerCode);
			
			NumberFormat formatter = new DecimalFormat("000");
			String strPageNumber = formatter.format(pageNumber);
			
			formatter = new DecimalFormat("00");
			String strPageSize = formatter.format(pageSize);
			
			log.debug("BackOrderDAOAbstract: pageNumber and size: " + 
					strPageNumber + " " + strPageSize);
			statement = connection.prepareCall(callString);
			//TODO: Remove this statement
			//countryCode = "US";
			
			statement.setString(1, countryCode.toUpperCase());
			statement.setString(2, dealerCode.toUpperCase());
			statement.setString(3, statusFlag.toUpperCase());
			statement.setString(4, sortType.toUpperCase());
			statement.setString(5, strPageNumber);
			statement.setString(6, strPageSize);
			statement.registerOutParameter(7, Types.VARCHAR);
			statement.registerOutParameter(8, Types.VARCHAR);
			statement.execute();
			
			//TODO: ERROR HANDLING
			String outputString1 = statement.getString(7);
			String outputString2 = statement.getString(8);
			
			log.debug("BackOrderDAOAbstract: outputString1: " + outputString1);
			log.debug("BackOrderDAOAbstract: outputString2: " + outputString2.trim());
			
			/*String*/ result = outputString2;
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
	
}
