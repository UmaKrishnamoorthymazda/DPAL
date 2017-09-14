/*
 * Created on Jan 8, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.persistence.dto;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealerListDTOImpl implements DealerListDTO{
	Map /*LDAPPerson*/ ldapPersonMap;
	ArrayList /*LDAPPerson*/ ldapPersonList;
	/**
	 * @return Returns the ldapPersonMap.
	 */
	public Map getLdapPersonMap() {
		return ldapPersonMap;
	}
	/**
	 * @param ldapPersonMap The ldapPersonMap to set.
	 */
	public void setLdapPersonMap(Map ldapPersonMap) {
		this.ldapPersonMap = ldapPersonMap;
	}
	/**
	 * @return Returns the ldapPersonList.
	 */
	public ArrayList getLdapPersonList() {
		return ldapPersonList;
	}
	/**
	 * @param ldapPersonList The ldapPersonList to set.
	 */
	public void setLdapPersonList(ArrayList ldapPersonList) {
		this.ldapPersonList = ldapPersonList;
	}
}
