/*
 * Created on Dec 17, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.apache.log4j.Logger;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.common.util.ApplicationUtil;
import com.mazdausa.common.DAO.JDBCDAOAbstract;
import com.mazdausa.common.constants.CommonConstants;
import com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean;
import com.mazdausa.dealer.parts.dpal.services.DPALConnectionException;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealerDetailDAOAbstract extends JDBCDAOAbstract {
	
	private static Logger log = EMDCSLogger.getLogger(DealerDetailDAOAbstract.class);
	
	public Connection getConnection() throws DPALConnectionException{
		try {
			return super.getConnection(CommonConstants.EMDCS_CONFIG_PROP_COMMON_DATASOURCE_REF_DB01); //
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error("Exception in getConnectionFromFramework method: " + e);
			throw new DPALConnectionException("Exception in getConnection method: " + e);
		}
	}
	
	protected DealerDataBean callStoredProcedure(String storedProcedureName, String countryCode, String buyingDealerCode, String operation) throws Exception { 
		
		Connection connection = null;
		CallableStatement statement = null;
		DealerDataBean dealerDataBean = null;
		
		try {
			connection = this.getConnection("mazdaDS"); //retrieved from xml file
			
			//[Jeril]: Uncomment the following block and modify as per requirement
			
			String schemaName = ApplicationUtil.getDB2MainframeSchemaName();
			
			if(operation.equals(DealerDetailDAOImpl.CONTACT_DETAILS)){
				String callString = "{ call " + schemaName + "." + storedProcedureName + " (?, ?, ?, ?, ?, ?) }"; 
				log.debug("DealerDetailDAOAbstract: " + callString + " " + buyingDealerCode);
				
				//TODO: Remove this statement
				//countryCode = "US";
				
				statement = connection.prepareCall(callString);
				statement.setString(1, countryCode);
				statement.setString(2, buyingDealerCode);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.registerOutParameter(6, Types.VARCHAR);
				statement.execute();
				String dealerName = statement.getString(4);
				String contactName = statement.getString(5);
				String contactNumber = statement.getString(6);
				
				log.debug("DealerDetailDAOAbstract: callStoredProcedure: " + dealerName + " " + contactName + " " + contactNumber);
				/*DealerDataBean*/ dealerDataBean = new DealerDataBean();
				dealerDataBean.setCode(buyingDealerCode);
				dealerDataBean.setName(dealerName);
				dealerDataBean.setContactName(contactName);
				dealerDataBean.setContactNumber(contactNumber);
				
				//return dealerDataBean;
			}
			
			if(operation.equals(DealerDetailDAOImpl.ADMIN_DETAILS)){
				//String callString = "{ call " + schemaName + "." + storedProcedureName + " (?, ?, ?, ?, ?, ?) }";
				String callString = "{ call " + schemaName + "." + storedProcedureName + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
				log.debug("DealerDetailDAOAbstract: " + callString + " " + buyingDealerCode);
				
				//TODO: Remove this statement
				//countryCode = "US";
				
				statement = connection.prepareCall(callString);
				statement.setString(1, countryCode);
				statement.setString(2, buyingDealerCode);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.registerOutParameter(6, Types.VARCHAR);
				statement.registerOutParameter(7, Types.VARCHAR);
				statement.registerOutParameter(8, Types.VARCHAR);
				statement.registerOutParameter(9, Types.VARCHAR);
				statement.registerOutParameter(10, Types.VARCHAR);
				statement.registerOutParameter(11, Types.VARCHAR);
				statement.registerOutParameter(12, Types.VARCHAR);
				statement.registerOutParameter(13, Types.VARCHAR);
				statement.registerOutParameter(14, Types.VARCHAR);
				statement.registerOutParameter(15, Types.VARCHAR);
				
				statement.execute();
				
				//log.debug("DealerDetailDAOAbstract: callStoredProcedure: " + dealerName + " " + contactName + " " + contactNumber);
				/*DealerDataBean*/ dealerDataBean = new DealerDataBean();
				dealerDataBean.setCode(buyingDealerCode);
				dealerDataBean.setContactName(statement.getString(14).trim());
				dealerDataBean.setContactNumber(statement.getString(15).trim());
				
				String[] shippingDealer = new String[5];
				shippingDealer[0] = statement.getString(4).trim();
				shippingDealer[1] = statement.getString(5).trim();
				shippingDealer[2] = statement.getString(6).trim();
				shippingDealer[3] = statement.getString(7).trim();
				shippingDealer[4] = statement.getString(8).trim();
				/*dealerDataBean.setSellingDealerWSL1(statement.getString(4));
				dealerDataBean.setSellingDealerWSL1(statement.getString(5));
				dealerDataBean.setSellingDealerWSL1(statement.getString(6));
				dealerDataBean.setSellingDealerWSL1(statement.getString(7));
				dealerDataBean.setSellingDealerWSL1(statement.getString(8));*/
				
				String[] buyingDealer = new String[5];
				buyingDealer[0] = statement.getString(9).trim();
				buyingDealer[1] = statement.getString(10).trim();
				buyingDealer[2] = statement.getString(11).trim();
				buyingDealer[3] = statement.getString(12).trim();
				buyingDealer[4] = statement.getString(13).trim();
				/*dealerDataBean.setBuyingDealerWSL1(statement.getString(9));
				dealerDataBean.setBuyingDealerWSL2(statement.getString(10));
				dealerDataBean.setBuyingDealerWSL3(statement.getString(11));
				dealerDataBean.setBuyingDealerWSL4(statement.getString(12));
				dealerDataBean.setBuyingDealerWSL5(statement.getString(13));*/
				
				dealerDataBean.setShippingDealer(shippingDealer);
				dealerDataBean.setBuyingDealer(buyingDealer);
				log.debug("Name" + dealerDataBean.getName());
				log.debug("Contact Number" + dealerDataBean.getContactName());
				//return dealerDataBean;
			}
			
			if(operation.equals(DealerDetailDAOImpl.SHIPPING_DETAILS)) {
				String callString = "{ call " + schemaName + "." + storedProcedureName + "(?,?,?,?,?,?,?,?,?,?,?,?) }";
				statement = connection.prepareCall(callString);
				log.debug("[ASL]: DealerDetailDAOAbstract: " + callString );
				
				//TODO: Remove this statement
				//countryCode = "US"; 
				
				statement = connection.prepareCall(callString);
				statement.setString(1, countryCode);
				statement.setString(2, buyingDealerCode);
				statement.registerOutParameter(3, Types.VARCHAR);
				statement.registerOutParameter(4, Types.VARCHAR);
				statement.registerOutParameter(5, Types.VARCHAR);
				statement.registerOutParameter(6, Types.VARCHAR);
				statement.registerOutParameter(7, Types.VARCHAR);
				statement.registerOutParameter(8, Types.VARCHAR);
				statement.registerOutParameter(9, Types.VARCHAR);
				statement.registerOutParameter(10, Types.VARCHAR);
				statement.registerOutParameter(11, Types.VARCHAR);
				statement.registerOutParameter(12, Types.VARCHAR);

				statement.execute();
				String status = statement.getString(3);
				log.debug(statement.getString(3));
				
				/*DealerDataBean*/ dealerDataBean = new DealerDataBean();
				dealerDataBean.setName(statement.getString(4));
				dealerDataBean.setAddressLine1(statement.getString(5));
				dealerDataBean.setAddressLine2(statement.getString(6));
				dealerDataBean.setAddressLine3(statement.getString(7));
				dealerDataBean.setCity(statement.getString(8));
				dealerDataBean.setState(statement.getString(9));
				dealerDataBean.setZip(statement.getString(10));
				dealerDataBean.setContactName(statement.getString(11));
				dealerDataBean.setContactNumber(statement.getString(12));
				
				//Fix for Bug 26
				if(status.indexOf("   ")!=0){
					log.debug("### Entering the fix of Bug 26");
					dealerDataBean.setName("");
					dealerDataBean.setContactName("");
					dealerDataBean.setContactNumber("");
					
				}
				
				dealerDataBean.setCode(buyingDealerCode);
				log.debug("SHIPPING_DETAILS: Name" + statement.getString(4));
				log.debug("SHIPPING_DETAILS: Contact Number" + statement.getString(12));
				
				//return dealerDataBean;
			}
			
			
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
		return dealerDataBean;
	}

	
}
