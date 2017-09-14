/*
 * Created on Dec 11, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.applications.util.parser;

import java.util.ArrayList;
import java.util.Iterator;

import com.mazdausa.dealer.parts.dpal.applications.databeans.BackOrderDataBean;
import com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean;
import com.mazdausa.dealer.parts.dpal.applications.databeans.PartsDataBean;
import com.mazdausa.dealer.parts.dpal.persistence.dto.BackOrderDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.BackOrderDTOImpl;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BackOrderDTOCreator implements DTOCreator {
	private static final int BO_SO = 0;
	private static final int BO_LN = 1;
	private static final int BO_DATE = 2;
	
	private static final int PARTS_OQ = 3; // Back Order Quantity
	private static final int PART_AQ = 4; // Available Quantity (My Inventory)
	private static final int PARTS_NUMBER = 5;
	private static final int PARTS_DESC = 6;
	
	private static final int DEALER_CODE = 7;
	private static final int DEALER_NAME = 8;
	private static final int DEALER_ADDRESS_LINE1 = 9;
	private static final int DEALER_ADDRESS_LINE2 = 10;
	private static final int DEALER_ADDRESS_LINE3 = 11;
	private static final int DEALER_CITY = 12;
	private static final int DEALER_STATE = 13;
	private static final int DEALER_ZIP = 14;
	
	/* (non-Javadoc)
	 * @see com.mazdausa.dealer.parts.dpal.applications.util.parser.DTOCreator#createDTO(java.util.ArrayList)
	 */
	public Object createDTO(ArrayList parsedRecords) {
		BackOrderDTO dto = null; 
		
		for(Iterator it=parsedRecords.iterator(); it.hasNext();) {
			if(dto == null) dto = new BackOrderDTOImpl();
			String[] record = (String[])it.next();
			
			PartsDataBean pdb = new PartsDataBean(record[PARTS_NUMBER], record[PARTS_DESC], record[PARTS_OQ], record[PART_AQ]);
			
			DealerDataBean ddb = new DealerDataBean(record[DEALER_CODE], record[DEALER_NAME], record[DEALER_ADDRESS_LINE1], 
					record[DEALER_ADDRESS_LINE2], record[DEALER_ADDRESS_LINE3], record[DEALER_CITY], record[DEALER_STATE], record[DEALER_ZIP]);
			
			String salesOrder = record[BO_SO];
			String lineNumber = record[BO_LN];
			String backOrderDate = record[BO_DATE];
			
			
			BackOrderDataBean bean = new BackOrderDataBean(salesOrder, lineNumber, backOrderDate, pdb, ddb);
			dto.addBackOrderDataBean(bean);
		}
		
		return dto;
	}
}
