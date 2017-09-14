/*
 * Created on Dec 24, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mazdausa.dealer.parts.dpal.ups.helper;

//import com.mazdausa.dealer.parts.dpal.ups.helper.ShipmentConfirmRequestHelper;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

/**
 * @author ashanka2
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class UPSHelperAbstract {
	protected Document doc;
	
	public String getFilename(String fileName){
		return null;
	}
	
	//Create a DOM object from the template file.
	public void getDocumentFromFile(String xmlContent) throws Exception {
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory .newInstance();
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			doc = builder.parse(xmlContent);
			} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void getDocumentFromString(String fileName) throws Exception {
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory .newInstance();
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			StringReader reader = new StringReader( fileName );
			InputSource inputSource = new InputSource( reader );
			doc = builder.parse(inputSource);
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void setValueInXPath(String argXpath, String value) throws Exception {
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile(argXpath);
		//log.debug(doc.);
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		if(value == null || value.indexOf("null")!=-1){
			
		}else{
			Text txt = doc.createTextNode(value.trim());
			node.appendChild(txt);
		}		
	}
	
	public String getValueFromXPath(String argXpath) throws Exception {
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile(argXpath);
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		NodeList nodelist = node.getChildNodes();
		String nodeValue = "";
		for(int i = 0; i < nodelist.getLength(); i++){
			Node childNode = nodelist.item(i);
			if(childNode.getNodeType() == Node.TEXT_NODE)
				nodeValue = childNode.getNodeValue();
		}
		return nodeValue;
	}
		
	public String convertDocToString() throws Exception {
		Source source = new DOMSource(doc);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Result result = new StreamResult(baos);
		Transformer xformer = TransformerFactory.newInstance().newTransformer();
		xformer.transform(source, result);
		return baos.toString();
	}
}
