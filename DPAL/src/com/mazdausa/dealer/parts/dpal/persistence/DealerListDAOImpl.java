/*
 * Created on Jan 8, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mazdausa.common.ldap.command.LDAPServiceImpl;
import com.mazdausa.common.ldap.domain.LDAPPerson;
import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerListDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.DealerListDTOImpl;
import com.mazdausa.dealer.parts.dpal.services.DPALDAOException;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealerListDAOImpl implements DealerListDAO {
	private static Logger log = EMDCSLogger.getLogger(DealerListDAOImpl.class);
	/* (non-Javadoc)
	 * @see com.mazdausa.dealer.parts.dpal.persistence.DealerListDAO#findDTO(java.lang.String)
	 */
	public DealerListDTO findDTO(String dealerCode) throws DPALDAOException {
		DealerListDTO dto = null;
		try {
			LDAPServiceImpl ldapService = LDAPServiceImpl.getInstance();
			//LDAPPerson person = ldapService.findActiveUserByUserId("bramos");
			//person.getJobDescription(person.getJobCtgryCd()); person.getUserid(); person.getEmail();person.getFirstName();person.getLastName();
			//Returns all active users from dealer 10102
			Map personsMap = ldapService.findActiveUsersFromLocation(dealerCode);
			/*for (Iterator iter = personsMap.values().iterator(); iter.hasNext();) {
				//Display the user id of the person in this location
				log.debug(((LDAPPerson) iter.next()).getUserid());
			}*/
			log.debug("converting to arraylist");
			ArrayList list = new ArrayList(Arrays.asList(personsMap.values().toArray()));
			log.debug("arraylist = " + list);
			dto = new DealerListDTOImpl();
			//dto.setLdapPersonMap(personsMap);
			dto.setLdapPersonList(list);
			
		} catch (Exception e) {
			//logger.error("Exception caught: " + e);
			log.debug("An exception was encountered while retrieving dealer persons from location " + dealerCode);
			e.printStackTrace();
		}
		return dto;
	}
}
