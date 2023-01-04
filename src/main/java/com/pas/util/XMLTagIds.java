package com.pas.util;


import com.pas.util.QName;
/**
 * Insert the type's description here.
 * Creation date: (7/9/2002 2:42:21 PM)
 * @author: Administrator
 */
public class XMLTagIds {
	
	public final static java.lang.String RESPONSE_TAG 		= "Response";
	
	public final static java.lang.String ERROR_MESSAGE 		= "ErrorMessage";
	public final static java.lang.String ERRORS 			= "Errors";
	public final static java.lang.String HIGH_SEVERITY_ERROR 	= "HighSeverityErrors";
	public final static java.lang.String MEDIUM_SEVERITY_ERROR 	= "MediumSeverityErrors";
	public final static java.lang.String LOW_SEVERITY_ERROR = "LowSeverityErrors";

	public final static java.lang.String PARAMETERS 		= "Parameters";
	
	public static final String NS_URI_CURRENT_SCHEMA_XSD = "http://www.w3.org/2001/XMLSchema";
	public static final String NS_URI_CURRENT_SCHEMA_XSI = "http://www.w3.org/2001/XMLSchema-instance";
	public final static java.lang.String NAMESPACE_PREFIX = "LFG";
	public final static java.lang.String NAMESPACE_URI = "http://www.lfg.com/";
	public final static java.lang.String RESPONSE 			= "response";

	
  public static final QName stringQName =
    new QName(XMLTagIds.NAMESPACE_URI, "java.lang.String");
  public static final QName intQName =
    new QName(XMLTagIds.NAMESPACE_URI, "int");
  public static final QName decimalQName =
    new QName(XMLTagIds.NAMESPACE_URI, "decimal");
  public static final QName floatQName =
    new QName(XMLTagIds.NAMESPACE_URI, "java.lang.Float");
  public static final QName doubleQName =
    new QName(XMLTagIds.NAMESPACE_URI, "java.lang.Double");
  public static final QName dateQName =
    new QName(XMLTagIds.NAMESPACE_URI, "date");
  public static final QName booleanQName =
    new QName(XMLTagIds.NAMESPACE_URI, "boolean");
  public static final QName longQName =
    new QName(XMLTagIds.NAMESPACE_URI, "long");
  public static final QName shortQName =
    new QName(XMLTagIds.NAMESPACE_URI, "short");
  public static final QName byteQName =
    new QName(XMLTagIds.NAMESPACE_URI, "byte");
  public static final QName hexQName =
    new QName(XMLTagIds.NAMESPACE_URI, "hexBinary");
  public static final QName qNameQName =
    new QName(XMLTagIds.NAMESPACE_URI, "QName");
  public static final QName timeInstQName =
    new QName(XMLTagIds.NAMESPACE_URI, "dateTime");
  public static final QName objectQName =
    new QName(XMLTagIds.NAMESPACE_URI, "anyType");
    
  public static final QName responseQName = 
    new QName(XMLTagIds.NAMESPACE_URI, "com.lfg.common.model.Response");
	
  public static final QName messageQName = 
    new QName(XMLTagIds.NAMESPACE_URI, "com.lfg.common.model.Message");
	
    
  public static final QName dollarValueQName =
  	new QName(XMLTagIds.NAMESPACE_URI, "com.lfg.common.util.DollarValue");
  	
    
  public static final QName arrayListQName =
  	new QName(XMLTagIds.NAMESPACE_URI, "java.util.ArrayList");
  	
  public static final QName mapQName =
  	new QName(XMLTagIds.NAMESPACE_URI, "java.util.HashMap");
  	
  public static final QName hashtableQName =
  	new QName(XMLTagIds.NAMESPACE_URI, "java.util.Hashtable");
  	
  public static final QName lfgDateQName = 
  	new QName(XMLTagIds.NAMESPACE_URI, "com.lfg.common.util.LFGDate");
  	
  public static final QName personQName = 
    new QName(XMLTagIds.NAMESPACE_URI, "com.lfg.common.model.Person");

  public static final QName nameQName = 
    new QName(XMLTagIds.NAMESPACE_URI, "com.lfg.common.model.Name");
  
  public static final QName addressQName = 
    new QName(XMLTagIds.NAMESPACE_URI, "com.lfg.common.model.Address");

  public static final QName stateQName = 
    new QName(XMLTagIds.NAMESPACE_URI, "com.lfg.common.model.State");

  public static final QName socialSecurityNumberQName = 
    new QName(XMLTagIds.NAMESPACE_URI, "com.lfg.common.model.SocialSecurityNumber");


  public static final QName nameLocalName =              	
    new QName(XMLTagIds.NAMESPACE_URI,"name"); 
  public static final QName addressLocalName =              	
    new QName(XMLTagIds.NAMESPACE_URI,"address"); 
    
  public static final QName messageLocalName =              	
    new QName(XMLTagIds.NAMESPACE_URI,"message");
   

  // Attribute names.
  public static final String ATTR_TYPE = "type";
  public static final String ATTR_NULL = "null";
  public static final String ATTR_ARRAY_TYPE = "arrayType";
  public static final String ATTR_REFERENCE = "href";
  public static final String ATTR_ID = "id";
  public static String ATTRVAL_TRUE = "true";
  
  public static final String MESSAGE_NO = "messageNo";
  public static final String MESSAGE_TEXT = "messageText";
  public static final String DATA = "DATA";


	
/**
 * XMLTagIds constructor comment.
 */
public XMLTagIds() {
	super();
}
}
