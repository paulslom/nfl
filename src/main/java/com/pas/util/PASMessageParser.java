package com.pas.util;


import java.io.IOException;
import java.io.StringReader;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


import com.pas.util.XMLTagIds;


/**
 * The Utility is used to assist in parsing an PASMessage
 */


public class PASMessageParser {
	
	private Document document = null; 
	
	/**
	 * Constructor takes a dom Document.
	 * @param doc Document
	 */
	public PASMessageParser(Document doc) {
		
		setDocument(doc);
	}


	/**
	 * Constructor build a dom Document from the passed
	 * XML String.
	 * 
	 * @param xmlString String
	 */
	public PASMessageParser(String xmlString) throws SAXParseException, SAXException, ParserConfigurationException, IOException {


		// Use a DocumentBuilderFactory to create the Document builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 		
		// Use the DocumentBuilder to build the Document
    	DocumentBuilder builder = factory.newDocumentBuilder();


		// Build and set the document from the passed String
    	setDocument(builder.parse(new InputSource(new StringReader(xmlString))));
	}	
	


	/**
	 * Return document
	 * @return Document
	 */
	public Document getDocument() {
		return document;
	}
	
	/**
	 * Pull First Message Number from document
	 * @return String
	 */
	public String getMessageNumber() {
		
    	// Pull off Message Number element
   		NodeList messageNumbers = getDocument().getElementsByTagName(XMLTagIds.MESSAGE_NO);
 
 		if ((messageNumbers == null) || (messageNumbers.getLength()==0)) {
			return null;
    	}	
    
    	Node messageNumberNode = messageNumbers.item(0).getFirstChild();
    	CharacterData charData = (CharacterData)messageNumberNode;
    	return charData.getData();
    
	}
    
	/**
	 * Pull First Message Text from document
	 * @return String
	 */
	public String getMessageText() {
		
    	// Pull off Message Text element
   		NodeList messageTexts = getDocument().getElementsByTagName(XMLTagIds.MESSAGE_TEXT);
 
 		if ((messageTexts == null) || (messageTexts.getLength()==0)) {
			return null;
    	}	
    
    	Node messageTextNode = messageTexts.item(0).getFirstChild();
    	CharacterData charData = (CharacterData)messageTextNode;
    	return charData.getData();
    
	}
   
	/**
	 * Pull Node for passed elementTagName from document
	 * @param elementTagName String
	 * @return org.w3c.dom.Node
	 */
	public Node getNode(String elementTagName) {
    
    	// Pull off element
    	NodeList nodeList = document.getElementsByTagName(elementTagName);
    
    	if (nodeList.getLength()==0) {
 	  	 	return null;
    	}
    	
    	return nodeList.item(0);
    	
	}
	
	/**
	 * Sets document
	 * @param doc Document
	 */
	private void setDocument(Document doc) {
		document = doc;
	}


}


