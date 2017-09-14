/*
 * Created on Dec 20, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.apache.mazdalog4j.Logger;
import com.mazdausa.common.util.ApplicationUtil;

import com.mazdausa.common.DAO.JDBCDAOAbstract;
import com.mazdausa.common.constants.CommonConstants;
import com.mazdausa.dealer.parts.dpal.applications.databeans.ItemAttributeDataBean;
import com.mazdausa.dealer.parts.dpal.services.DPALConnectionException;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ItemAttributeDAOAbstract extends JDBCDAOAbstract {
	private static Logger log = Logger.getLogger(ItemAttributeDAOAbstract.class);
	
	public Connection getConnection() throws DPALConnectionException{
		try {
			return super.getConnection(CommonConstants.EMDCS_CONFIG_PROP_COMMON_DATASOURCE_REF_DB01); //
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in getConnectionFromFramework method: " + e);
			throw new DPALConnectionException("Exception in getConnection method: " + e);
		}
	}
	
	protected ItemAttributeDataBean callStoredProcedure(String storedProcedureName, String countryCode, String buyingDealerCode,
			String salesOrder, String lineNumber) throws Exception {
		Connection connection = null;
		CallableStatement statement = null;
		ItemAttributeDataBean itemAttributeDataBean = null;
		
		try {
			connection = this.getConnection("mazdaDS"); //retrieved from xml file
			
		
			String schemaName = ApplicationUtil.getDB2MainframeSchemaName();
			
			String callString = "{ call " + schemaName + "." + storedProcedureName + " (?,?,?,?,?,?) }"; 
			log.info(callString);
			
			//TODO: Remove this statement
			//countryCode = "US";
			
			statement = connection.prepareCall(callString);
			statement.setString(1, countryCode);
			statement.setString(2, buyingDealerCode);
			statement.setString(3, salesOrder);
			statement.setString(4, lineNumber);
			
			log.debug("ItemAttributeDAOAbstract: " + buyingDealerCode + " " + salesOrder + " " + lineNumber);
			statement.registerOutParameter(5, Types.VARCHAR);
			statement.registerOutParameter(6, Types.VARCHAR);
			statement.registerOutParameter(7, Types.VARCHAR);
			statement.registerOutParameter(8, Types.VARCHAR);
			statement.registerOutParameter(9, Types.VARCHAR);
			
			statement.execute();
			
			String backOrderQuantity = statement.getString(6);
			String itemWeight = statement.getString(7);
			String unitPrice = statement.getString(8);
			String promoUnitDiscount = statement.getString(9);
			
			log.debug("ItemAttributeDAOAbstract: " + backOrderQuantity);
			log.debug("ItemAttributeDAOAbstract: " + itemWeight);
			log.debug("ItemAttributeDAOAbstract: " + unitPrice);
			log.debug("ItemAttributeDAOAbstract: " + promoUnitDiscount);
			
			/*ItemAttributeDataBean*/ itemAttributeDataBean = new ItemAttributeDataBean();
			itemAttributeDataBean.setLineNumber(lineNumber);
			itemAttributeDataBean.setSalesOrder(salesOrder);
			itemAttributeDataBean.setQuantity(backOrderQuantity!=null&&backOrderQuantity.trim().equals("")?0:Double.parseDouble(backOrderQuantity));
			itemAttributeDataBean.setWeight(itemWeight!=null&&itemWeight.trim().equals("")?0:Double.parseDouble(itemWeight));
			itemAttributeDataBean.setOuBasePrice(unitPrice!=null&&unitPrice.trim().equals("")?0:Double.parseDouble(unitPrice));
			itemAttributeDataBean.setPromoUnitDiscount(promoUnitDiscount!=null&&promoUnitDiscount.trim().equals("")?0:Double.parseDouble(promoUnitDiscount));
			
			//return itemAttributeDataBean;
			
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
		return itemAttributeDataBean;
	}
}
