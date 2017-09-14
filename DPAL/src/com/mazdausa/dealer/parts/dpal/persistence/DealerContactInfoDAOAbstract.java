package com.mazdausa.dealer.parts.dpal.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;

import org.apache.mazdalog4j.Logger;

import com.mazdausa.common.DAO.JDBCDAOAbstract;
import com.mazdausa.common.constants.CommonConstants;
import com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean;
import com.mazdausa.dealer.parts.dpal.services.DPALConnectionException;

public class DealerContactInfoDAOAbstract extends JDBCDAOAbstract{
	private static Logger logger = Logger.getLogger(DealerDetailDAOAbstract.class);

	public Connection getConnection() throws DPALConnectionException{
		try {
			return super.getConnection(CommonConstants.EMDCS_CONFIG_PROP_COMMON_DATASOURCE_REF_DB01); //
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in getConnectionFromFramework method: " + e);
			throw new DPALConnectionException("Exception in getConnection method: " + e);
		}
	}
	protected DealerDataBean callStoredProcedure(String storedProcedureName,String countryCode, String dealerCode, String shippingemail1, String shippingemail2, String shippingemail3, String shippingemail4, String shippingemail5, String buyingemail1,String buyingemail2, String buyingemail3,String buyingemail4,String buyingemail5, String name, String number) throws Exception { 

		Connection connection = null;
		CallableStatement statement = null;

		try {
			/*connection = this.getConnection("mazdaDS"); //retrieved from xml file

			//[Jeril]: Uncomment the following block and modify as per requirement

			String schemaName = ApplicationUtil.getDB2MainframeSchemaName();

			String callString = "{ call " + schemaName + "." + storedProcedureName + " (?,?,?,?,?,?) }"; 
			logger.info(callString);

			statement = connection.prepareCall(callString);
			statement.setString(1, countryCode);
			statement.setString(2, dealerCode);
			statement.setInt(3, pageNumber);
			statement.setInt(4, pageSize);
			statement.setString(5, sortType);
			statement.registerOutParameter(6, Types.VARCHAR);
			statement.execute();
			String resultString = statement.getString(6);

			//String resultString = null;
			return resultString;*/

			return null;
			//String result = db.getRows(DummyDatabase.TABLE_BACKORDER, "O", pageNumber, pageSize, sortType);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
