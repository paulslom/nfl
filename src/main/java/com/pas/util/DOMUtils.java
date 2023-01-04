package com.pas.util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Attr;

public class DOMUtils {

  private static String NS_URI_XMLNS = "http://www.w3.org/2000/xmlns/";

  static public String getChildCharacterData (Element parentEl) {
    if (parentEl == null) {
      return null;
    } 
    Node          tempNode = parentEl.getFirstChild();
    StringBuffer  strBuf   = new StringBuffer();
    CharacterData charData;

    while (tempNode != null) {
      switch (tempNode.getNodeType()) {
        case Node.TEXT_NODE :
        case Node.CDATA_SECTION_NODE : charData = (CharacterData)tempNode;
                                       strBuf.append(charData.getData());
                                       break;
      }
      tempNode = tempNode.getNextSibling();
    }
    return strBuf.toString();
  }
  
  
  public static String getNamespaceURIFromPrefix (Node context,
                                                  String prefix) {
    short nodeType = context.getNodeType ();
    Node tempNode = null;

    switch (nodeType)
    {
      case Node.ATTRIBUTE_NODE :
      {
        tempNode = ((Attr) context).getOwnerElement ();
        break;
      }
      case Node.ELEMENT_NODE :
      {
        tempNode = context;
        break;
      }
      default :
      {
        tempNode = context.getParentNode ();
        break;
      }
    }

    while (tempNode != null && tempNode.getNodeType () == Node.ELEMENT_NODE)
    {
      Element tempEl = (Element) tempNode;
      String namespaceURI = (prefix == null)
                            ? getAttribute (tempEl, "xmlns")
                            : getAttributeNS (tempEl, NS_URI_XMLNS, prefix);

      if (namespaceURI != null)
      {
        return namespaceURI;
      }
      
      tempNode = tempEl.getParentNode ();
      
    }

    return null;
  }


  static public String getAttribute (Element el, String attrName) {
    String sRet = null;
    Attr   attr = el.getAttributeNode(attrName);

    if (attr != null) {
      sRet = attr.getValue();
    }
    return sRet;
  }

  static public String getAttributeNS (Element el,
                                       String namespaceURI,
                                       String localPart) {
    String sRet = null;
    Attr   attr = el.getAttributeNodeNS (namespaceURI, localPart);

    if (attr != null) {
      sRet = attr.getValue ();
    }

    return sRet;
  }

  public static Element getFirstChildElement (Element elem) {
    for (Node n = elem.getFirstChild (); n != null; n = n.getNextSibling ()) {
      if (n.getNodeType () == Node.ELEMENT_NODE) {
        return (Element) n;
      }
    }
    return null;
  }

  public static Element getElementByID(Element el, String id)
  {
	  if (el == null)
		  return null;
	  String thisId = el.getAttribute("id");
	  if (id.equals(thisId))
		  return el;
	  
	  NodeList list = el.getChildNodes();
	  for (int i = 0; i < list.getLength(); i++) {
		  Node node = list.item(i);
		  if (node instanceof Element) {
			  Element ret = getElementByID((Element)node, id);
			  if (ret != null)
				  return ret;
		  }
	  }
	  
	  return null;
  }

  public static Element getNextSiblingElement (Element elem) {
    for (Node n = elem.getNextSibling (); n != null; n = n.getNextSibling ()) {
      if (n.getNodeType () == Node.ELEMENT_NODE) {
        return (Element) n;
      }
    }
    return null;
  }

  public static int countKids (Element elem, short nodeType) {
    int nkids = 0;
    for (Node n = elem.getFirstChild (); n != null; n = n.getNextSibling ()) {
      if (n.getNodeType () == nodeType) {
        nkids++;
      }
    }
    return nkids;
  }

}

