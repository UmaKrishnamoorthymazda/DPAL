package com.mazdausa.dealer.parts.dpal.applications.util.parser;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mazdausa.common.log.EMDCSLogger;

public class RecordParser {
	private static Logger log = EMDCSLogger.getLogger(RecordParser.class);
	private DocumentBuilderFactory domFactory = null;
	private DocumentBuilder builder = null;
	private XPath xpath = null;
	
	private String pageName;
	private Document doc = null;
	
	private String creatorClass = null;
	
	public RecordParser(String argPageName) {
		try {
			domFactory = DocumentBuilderFactory.newInstance();
			builder = domFactory.newDocumentBuilder();
			URL file = this.getClass().getResource("/xml/recordParserConfig.xml");
			//URL file = this.getClass().getResource("recordParserConfig.xml");
			String fileName = file.getFile();
			doc = builder.parse(fileName);
			xpath = XPathFactory.newInstance().newXPath();
			this.pageName = argPageName;
			this.creatorClass = (String)getValue("//page[@name='" + pageName + "'" + "]/@creator", XPathConstants.STRING);
			log.debug("RecordParser : creator class = " + creatorClass);
		} catch(Exception e) {
			//TODO: pass this to the logger.
			e.printStackTrace();
		}
	}
	
	
	public Object parseRecords(String concatenatedData)throws Exception {
		ArrayList parsedRecords = new ArrayList();
		int startIndex = 0;
		String[][] recordLengthAndType = recordLengthAndType();
		String[] records = null;
		
		//Check the expected recordlength to the concatednated Data length.
		int expectedRecordLength = 0;
		for(int i = 0; i < recordLengthAndType.length; i++){
			int columnSize = Integer.parseInt(recordLengthAndType[i][0]);
			expectedRecordLength += columnSize;
		}
		log.debug("RecordParser : parseRecords : " + expectedRecordLength + " " + concatenatedData.length());
		//TODO: Need to revisit this condition
		//if(concatenatedData.length()%expectedRecordLength != 0)
			//throw new Exception("Unexpected recordset length. Parsing will fail.");
		
		StringBuffer recordSet = new StringBuffer(concatenatedData);
		while(recordSet.toString().trim().length() != 0) {
			startIndex = 0;
			records = new String[recordLengthAndType.length];
			for(int i = 0; i < recordLengthAndType.length; i++){
				int columnSize = Integer.parseInt(recordLengthAndType[i][0]);
				records[i] = recordSet.substring(startIndex, startIndex + columnSize).trim();
				if(recordLengthAndType[i][1].equalsIgnoreCase("Date")){
					//to convert to the format required by the user
				}
				if(recordLengthAndType[i][1].equalsIgnoreCase("Float")){
					//to remove leading zeros
					try{
						double flt= Double.parseDouble(records[i]);
						records[i] = Double.toString(flt);
					}catch(Exception e){
						
					}
				}
				
				startIndex = startIndex + columnSize;
			}
			parsedRecords.add(records);
			recordSet = recordSet.delete(0, expectedRecordLength);			
		}
		
		return getDTOList(parsedRecords);
	}
	
	private Object getDTOList(ArrayList parsedRecords) {
		Object dto = null;
		try {
			Class cl = Class.forName(creatorClass);
			DTOCreator creator = (DTOCreator)cl.newInstance();
			dto = creator.createDTO(parsedRecords);
		} catch(Exception e){
			e.printStackTrace();
		}
		return dto;
	}

	//sending back the length of the records
	private String[][] recordLengthAndType()throws Exception{
		NodeList columnSequence = (NodeList)getValue("//page[@name='" + pageName + "']/columnSequence", XPathConstants.NODESET);
		String[][] recordLengthAndType = new String[columnSequence.getLength()][2];
		log.debug("Record Parser: recordLengthAndType: lenght: " + columnSequence.getLength());
		for(int i = 1; i <= columnSequence.getLength(); i++){
			String attributeName = (String)getValue("//page[@name='" + pageName + "']/columnSequence[@item='" + i + "']/text()",XPathConstants.STRING);

			Node lengthNode = (Node)getValue("//columns/columnName[@name='" + attributeName + "']/@length", XPathConstants.NODE);
			
			Node typeNode = (Node)getValue("//columns/columnName[@name='" + attributeName + "']/@type", XPathConstants.NODE);
				
			
			recordLengthAndType[i-1][0] = lengthNode.getNodeValue();
			recordLengthAndType[i-1][1] = typeNode.getNodeValue();
			
		}
		return recordLengthAndType;
	}
	
	private Object getValue(String path, QName type) throws Exception{
		XPathExpression expr = xpath.compile(path);
		return expr.evaluate(doc, type);
	}

	public static void main(String args[]) throws Exception
	{
		new RecordParser("CurrentBackOrderList").parseRecords("this string is just used for the testing purpose, no logic intended. Java Rational Application Developer For Websphere Sof");
	}
}
