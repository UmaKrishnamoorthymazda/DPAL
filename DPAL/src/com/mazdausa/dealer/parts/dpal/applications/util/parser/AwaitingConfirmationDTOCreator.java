/*
 * Created on Dec 11, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.applications.util.parser;

import java.util.ArrayList;
import java.util.Iterator;

import com.mazdausa.dealer.parts.dpal.applications.databeans.AwaitingConfirmationDataBean;
import com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean;
import com.mazdausa.dealer.parts.dpal.applications.databeans.PartsDataBean;
import com.mazdausa.dealer.parts.dpal.persistence.dto.AwaitingConfirmationDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.AwaitingConfirmationDTOImpl;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AwaitingConfirmationDTOCreator implements DTOCreator {
	private static final int PARTS_NUMBER = 3;
	private static final int PARTS_DESC = 4;
	private static final int PARTS_OQ = 5;
	private static final int PART_SQ = 6;
	
	private static final int DEALER_CODE = 7;
	private static final int DEALER_NAME = 8;
	private static final int DEALER_CITY = 9;
	private static final int DEALER_STATE = 10;
	private static final int DEALER_ZIP = 11;
	
	private static final int AW_SO = 0;
	private static final int AW_LN = 1;
	private static final int AW_DATE = 2;
	/* (non-Javadoc)
	 * @see com.mazdausa.dealer.parts.dpal.applications.util.parser.DTOCreator#createDTO(java.util.ArrayList)
	 */
	public Object createDTO(ArrayList parsedRecords) {
		AwaitingConfirmationDTO dto = new AwaitingConfirmationDTOImpl();
		
		for(Iterator it=parsedRecords.iterator(); it.hasNext();) {
			String[] record = (String[])it.next();
			
			PartsDataBean pdb = new PartsDataBean(record[PARTS_NUMBER], record[PARTS_DESC], record[PARTS_OQ], record[PART_SQ]);
			
			DealerDataBean ddb = new DealerDataBean(record[DEALER_CODE], record[DEALER_NAME], record[DEALER_CITY], record[DEALER_STATE], record[DEALER_ZIP]);
			
			String salesOrder = record[AW_SO];
			String lineNumber = record[AW_LN];
			String backOrderDate = record[AW_DATE];
			
			
			AwaitingConfirmationDataBean bean = new AwaitingConfirmationDataBean(salesOrder, lineNumber, backOrderDate, pdb, ddb);
			dto.addAwaitingConfirmationDataBean(bean);
		}
		
		return dto;
	}
}
