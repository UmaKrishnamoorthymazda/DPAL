/*
 * Created on Dec 16, 2010
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

import org.apache.mazdalog4j.Logger;

import com.mazdausa.common.DAO.JDBCDAOAbstract;
import com.mazdausa.common.constants.CommonConstants;
import com.mazdausa.common.util.ApplicationUtil;
import com.mazdausa.dealer.parts.dpal.applications.util.WebUtils;
import com.mazdausa.dealer.parts.dpal.services.DPALConnectionException;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StatusUpdateDAOAbstract extends JDBCDAOAbstract {
	private static Logger log = Logger.getLogger(StatusUpdateDAOAbstract.class);

	public Connection getConnection() throws DPALConnectionException{
		try {
			return super.getConnection(CommonConstants.EMDCS_CONFIG_PROP_COMMON_DATASOURCE_REF_DB01); //
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in getConnectionFromFramework method: " + e);
			throw new DPALConnectionException("Exception in getConnection method: " + e);
		}
	}

	protected String callStoredProcedure(String storedProcedureName, String countryCode, String dealerCode,
			String salesOrder, String lineNumber, String wslId, String statusFlag, String previousStatusFlag) 
			throws Exception { 

		Connection connection = null;
		CallableStatement statement = null;
		String resultString = null;
		
		try {
			connection = this.getConnection("mazdaDS"); //retrieved from xml file

			String schemaName = ApplicationUtil.getDB2MainframeSchemaName();
			log.debug("StatusUpdateDAOAbstract: schemaName: " + storedProcedureName + " "
					+ countryCode + " " + dealerCode + " " + salesOrder + " " + lineNumber + 
					" " + wslId + " " + statusFlag + " " + previousStatusFlag);

			String callString = "{ call " + schemaName + "." + storedProcedureName + " (?, ?, ?, ?, ?, ?, ?, ?) }"; 
			log.info(callString);

			statement = connection.prepareCall(callString);
			
			//TODO: Remove this statement
			//countryCode = "US";
			
			statement.setString(1, countryCode);
			statement.setString(2, dealerCode);
			statement.setString(3, salesOrder);
			statement.setString(4, lineNumber);
			statement.setString(5, previousStatusFlag);
			statement.setString(6, statusFlag);
			statement.setString(7, wslId);
			
			statement.registerOutParameter(8, Types.VARCHAR);
			statement.execute();
			
			/*String*/ resultString = statement.getString(8);

			//return resultString;

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
		return resultString;
	}
	
	protected String callStoredProcedure(String storedProcedureName, String countryCode, String dealerCode,
			String salesOrder, String lineNumber, String wslId, String dpboStatusFlag, String dpdrStatusFlag, String shippedQuantity) 
			throws Exception { 
		
		Connection connection = null;
		CallableStatement statement = null;		
		String resultString = null;
		
		try {
			connection = this.getConnection("mazdaDS"); //retrieved from xml file

			String schemaName = ApplicationUtil.getDB2MainframeSchemaName();
			log.debug("Shipped Quantity: " + shippedQuantity);
			double dShippedQuantity = Double.parseDouble(shippedQuantity);
			boolean negative = false;
			if(dShippedQuantity < 0) {
				negative = true;
				dShippedQuantity = 0 - dShippedQuantity; 
			}
			shippedQuantity = (negative?"-":"+") + WebUtils.padNumber(Double.toString(dShippedQuantity), 8, false);
			if(shippedQuantity.length()==9)shippedQuantity = shippedQuantity + "0";
			log.debug("Shipped Quantity: " + shippedQuantity);
			
			log.debug("StatusUpdateDAOAbstract: schemaName: " + storedProcedureName + " "
					+ countryCode + " " + dealerCode + " " + salesOrder + " " + lineNumber + 
					" " + wslId + " " + dpboStatusFlag + " " + dpdrStatusFlag + " "  + shippedQuantity);

			String callString = "{ call " + schemaName + "." + storedProcedureName + " (?, ?, ?, ?, ?, ?, ?, ?) }"; 
			log.info(callString);

			statement = connection.prepareCall(callString);
			
			//TODO: Remove this statement
			//countryCode = "US";
			
			/*NumberFormat formatter = new DecimalFormat("0000000.00");
			shippedQuantity = formatter.format(Double.parseDouble(shippedQuantity));*/
			
			statement.setString(1, countryCode);
			statement.setString(2, dealerCode);
			statement.setString(3, salesOrder);
			statement.setString(4, lineNumber);
			statement.setString(5, dpboStatusFlag);
			statement.setString(6, dpdrStatusFlag);
			statement.setString(7, shippedQuantity);
			statement.setString(8, wslId);
			
			statement.registerOutParameter(9, Types.VARCHAR);
			statement.execute();
			
			/*String*/ resultString = statement.getString(9);

			//return resultString;

		} catch(Exception e) {
			e.printStackTrace();
		} // Added to fix Bug id: 10 & 11
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
		return resultString;
	}
}
