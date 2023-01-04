package com.pas.business;

import java.util.ArrayList;
import java.util.List;



/**
 * @author CGI
 *
 * This class is a place holder for value objects and
 * validation errors that need to be deligated to presentation layer
 */
public class BusinessComposite {

	ValidationMessages bcValidationMessage = null;
    
    List<Object> bcList = new ArrayList<Object>();

    /**
     * @param list  holds list of value objects
     */
    public void setValueObjectList(List<Object> list) {
        bcList = list;
    }

    /**
     * @param list  holds list of value objects
     */
    public void addValueObjectToList(Object ivo) {
        if (ivo != null) {
            bcList.add(ivo);
        }
    }

    /**
     * @return bcList holds list of value objects
     */
    @SuppressWarnings("unchecked")
	public List getValueObjectList() {
        return bcList;
    }
   
	/**
	* @return bcValidationMessage holds ValidationMessage objects
	*/
	public ValidationMessages getValidationMessages() {
		return bcValidationMessage;
	}

   /**
	* @param vm  holds ValidationMessage objects
	*/
   public void setValidationMessages(ValidationMessages vm) {
	   bcValidationMessage = vm;
   }
   
   public void setValueObject(Object valueObject){
   		this.addValueObjectToList(valueObject);
   }
   
	public Object getValueObject(){
		if( bcList.isEmpty())
			return null;
		return bcList.get(0);
	}
	
	public boolean hasValidationMessages(){
		if( bcValidationMessage!= null && !bcValidationMessage.isEmpty())
			return true;
		return false;
	}
	
   

}
