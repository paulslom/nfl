/*
 * Created on Mar 9, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author SGanapathy
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SaxReader extends DefaultHandler
{

	StringBuffer parentElement;
	private Logger log = LogManager.getLogger(this.getClass()); 
	static final String DEFAULT_PARSER_NAME = "org.apache.xerces.parsers.SAXParser";

	HashMap<String, String> hashMap;

	public SaxReader()
	{
		hashMap = new HashMap<String, String>();
		parentElement = new StringBuffer();
	}

	public HashMap<String, String> parse(String fileName) throws SAXException, IOException
	{
		Reader reader = loadFileFromJarOrFile(fileName);
		InputSource source = new InputSource(reader);
		return parseInputSource(source);
	}

	public HashMap<String, String> parse(String fileName, HashMap<String,String> hashMapToUse)
			throws SAXException, IOException
	{
		hashMap = hashMapToUse;
		return this.parse(fileName);
	}

	public HashMap<String, String> parseInputSource(InputSource source) throws SAXException,IOException
	{
		String method = "parseInputSource - only source parameter::";
		log.debug(method + "in");
				
		XMLReader xmlReader = XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
		
		xmlReader.setContentHandler(this);		
		xmlReader.setErrorHandler(this);
		xmlReader.parse(source);
		
		log.debug(method + "out");
		
		return hashMap;
	}

	public HashMap<String, String> parseInputSource(InputSource source, HashMap<String, String> hashMapToUse)
			throws SAXException, IOException
	{
		String method = "parseInputSource - source and hashmap parameters::";
		log.debug(method + "in");
		
		hashMap = hashMapToUse;
		log.debug(method + "out");
		
		return this.parseInputSource(source);
	}

	public void error(SAXParseException e)
	{
		//System.out.println("Error parsing the file: "+e.getMessage());
	}

	public void warning(SAXParseException e) {
		//System.out.println("Problem parsing the file: "+e.getMessage());
	}

	public void fatalError(SAXParseException e) {
		//System.out.println("Error parsing the file: "+e.getMessage());
		//System.out.println("Cannot continue.");
	}

	public void startDocument() throws SAXException {
		//System.out.println( "Tallying results...");
	}

	public void endDocument() throws SAXException
	{
		//System.out.println( "Tallying results..." + hashMap.size() );
		// Print all the elements in the hash map.
		Set<String> keySet = hashMap.keySet();
		Iterator<String> itr = keySet.iterator();
		
		for (; itr.hasNext();)
		{
			String key = (String) itr.next();
			String value = hashMap.get(key);
			//if( value.equals(""))
			//  itr.remove();
			//else
			log.debug(key + " " + value );
		}
		//System.out.println( "Tallying results..." + hashMap.size() );

	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {

		//System.out.print("Start : ");
		//System.out.print( " Local name =" + localName );

		//Append the element to the Parent element. While Appending Add [0] to
		// the end of the element. Check if [0] exists in the Hashmap. If so
		// increment
		// loop till you get a miss. Now add that element the parent Buffer.

		if (!parentElement.toString().equals("")) {
			parentElement.append('.');
		}
		int i = 0;

		for (StringBuffer currentElement;; i++) {
			currentElement = new StringBuffer(parentElement.toString());
			currentElement.append(localName);
			currentElement.append('[');
			currentElement.append(i);
			currentElement.append(']');
			if (!hashMap.containsKey(currentElement.toString()))
				break;
		}
		parentElement.append(localName);
		parentElement.append('[');
		parentElement.append(i);
		parentElement.append(']');

	}

	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		//System.out.print( " End: "+localName + " PEB " +
		// parentElement.toString());

		// In end element, find the occurence of the last '.' Remove the String
		// including
		// the Dot and assign that to the parent buffer

		// Check if the element exist in the hashMap. If not add them
		// temporarily and remove them at the
		// end.
		if (!hashMap.containsKey(parentElement.toString()))
			hashMap.put(parentElement.toString(), "");

		int length = parentElement.length();
		int i = length - 1;
		for (; i >= 0; i--) {
			if (parentElement.charAt(i) == '.')
				break;

		}
		if (i >= 0)
			parentElement.delete(i, length);
		else if (length > 0)
			parentElement = new StringBuffer("");

		//System.out.println( " PEA " + parentElement.toString());

	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {

		//System.out.print( " Ch=" + new String(ch, start, length));
		// Convert the character array to string
		// Check if the string is empty or spaces. IF so do nothing.
		// Otherwise add the element and string to the hash map

		//StringBuffer value = new StringBuffer( );
		String value = (new String(ch, start, length)).trim();

		if (value.length() > 0) {
			String keyName = parentElement.toString();
			if (hashMap.containsKey(keyName)) {
				String value1 = hashMap.get(keyName);
				value = value1 + value;
			}
			hashMap.put(keyName, value);
		}
	}

	public static InputStreamReader loadFileFromJar(String s) {
		String s1 = forceToJar(s);
		InputStreamReader inputstreamreader = null;
		InputStream inputstream = ClassLoader.getSystemResourceAsStream(s1);
		if (inputstream != null)
			inputstreamreader = new InputStreamReader(inputstream);
		return inputstreamreader;
	}

	public static Reader loadFileFromJarOrFile(String s) throws IOException {
		Object obj = loadFileFromJar(s);
		if (obj == null) {
			s = forceToJar(s);
			s = getFullFileName(s);
			obj = new FileReader(s);
		}
		return ((Reader) (obj));
	}

	public static String getFullFileName(String masterPropertiesFile) {

		String jarFile = masterPropertiesFile;
		URL url = ClassLoader.getSystemResource(masterPropertiesFile);
		if (url != null) {
			String urlStr = url.getFile();
			int i = urlStr.indexOf('!');
			if (i != -1)
				jarFile = (urlStr.substring(0, i)).substring(6);
			else
				jarFile = urlStr;
		}
		//System.out.println("Absolute Path of jarFile is " + jarFile);
		return jarFile;
	}

	public static String forceToJar(String s) {
		StringBuffer stringbuffer = new StringBuffer(s);
		for (int i = 0; i < stringbuffer.length(); i++)
			if (stringbuffer.charAt(i) == '\\')
				stringbuffer.setCharAt(i, '/');

		return stringbuffer.toString();
	}

}
