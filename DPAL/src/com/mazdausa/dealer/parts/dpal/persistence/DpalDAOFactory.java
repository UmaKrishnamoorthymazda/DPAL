/*
 * Created on Dec 7, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import java.net.URL;

import org.apache.mazdalog4j.Logger;

import com.mazdausa.common.DAO.DAOFactoryAbstract;
import com.mazdausa.common.DAO.JDBCDAOAbstract;

/**
 * @author InterraIT
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DpalDAOFactory extends DAOFactoryAbstract {
	private static Logger logger = Logger.getLogger(DpalDAOFactory.class);
	
	public DpalDAOFactory() {
		super();
	}
	
	public DpalDAOFactory(String factoryName) {
		super();
		init(factoryName);		
	}

	protected JDBCDAOAbstract createDAOImplementation(String daoImplClassName)
			throws Exception {
		logger.debug("Create new instance of DAO Class Name - " + daoImplClassName);
		return(JDBCDAOAbstract) Class.forName(daoImplClassName).newInstance();
	}

	public String getConfigResourceURL() {
		logger.debug("getConfigResourceURL() - Get the ConfigResourceURL File Name");
		String daoConfigFileName = null;
		URL file = this.getClass().getResource("/xml/dpalDAO.xml");
		logger.debug("URL for ConfigResourceURL File Name = " + file);
		daoConfigFileName = file.getFile();
		logger.debug("daoConfigFileName to be retutned = " + daoConfigFileName);
		return daoConfigFileName;	 
	}
}
