package com.mazdausa.dealer.parts.dpal.applications.util.parser;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.mazdausa.common.log.EMDCSLogger;
import com.mazdausa.dealer.parts.dpal.applications.databeans.DealerDataBean;
import com.mazdausa.dealer.parts.dpal.applications.databeans.ProcessedBackOrderDataBean;
import com.mazdausa.dealer.parts.dpal.persistence.dto.ProcessedBackOrderDTO;
import com.mazdausa.dealer.parts.dpal.persistence.dto.ProcessedBackOrderDTOImpl;

public class ProcessedBackOrderDTOCreator implements DTOCreator {
	private static Logger log = EMDCSLogger.getLogger(ProcessedBackOrderDTOCreator.class);
	private static final int BO_DATE = 0;
	private static final int BO_SHIPPED_QTY = 1;
		
	private static final int BO_SO = 3;
	private static final int BO_LN = 4;
	
	private static final int PARTS_NUMBER = 5;
	private static final int PARTS_DESC = 6;
	
	private static final int SHIPPING_METHOD = 7;
	private static final int TRACKING_NUMBER = 8;
	
	private static final int BUYING_DEALER_CODE = 9;
	private static final int BUYING_DEALER_NAME = 10;
	private static final int BUYING_DEALER_ADDRESS_LINE1 = 11;
	private static final int BUYING_DEALER_ADDRESS_LINE2 = 12;
	private static final int BUYING_DEALER_ADDRESS_LINE3 = 13;
	private static final int BUYING_DEALER_CITY = 14;
	private static final int BUYING_DEALER_STATE = 15;
	private static final int BUYING_DEALER_ZIP = 16;
	

	public Object createDTO(ArrayList parsedRecords) {
		log.debug("ProcessedBackOrderDTOCreator: createDTO: Entering");
		ProcessedBackOrderDTO dto = new ProcessedBackOrderDTOImpl();
		
		for(Iterator it=parsedRecords.iterator(); it.hasNext();) {
			String[] record = (String[])it.next();

			DealerDataBean ddb = new DealerDataBean(record[BUYING_DEALER_CODE], record[BUYING_DEALER_NAME], 
					record[BUYING_DEALER_ADDRESS_LINE1],record[BUYING_DEALER_ADDRESS_LINE2], 
					record[BUYING_DEALER_ADDRESS_LINE3], record[BUYING_DEALER_CITY], 
					record[BUYING_DEALER_STATE], record[BUYING_DEALER_ZIP]);
			
			String salesOrder = record[BO_SO];
			String lineNumber = record[BO_LN];
			String backOrderDate = record[BO_DATE];
			String partsNumber = record[PARTS_NUMBER];
			String partsDesc = record[PARTS_DESC];
			String shippedQuantity = record[BO_SHIPPED_QTY];
			String shippingMethod = record[SHIPPING_METHOD];
			String trackingNumber = record[TRACKING_NUMBER];
			
			ProcessedBackOrderDataBean bean = new ProcessedBackOrderDataBean(salesOrder, lineNumber, backOrderDate, 
					partsNumber, partsDesc, shippedQuantity, shippingMethod, trackingNumber, ddb);
			dto.addProcessedBackOrderDataBean(bean);
		}
		
		return dto;
	}
}
