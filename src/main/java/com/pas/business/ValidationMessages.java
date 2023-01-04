package com.pas.business;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CGI
 */

/**
 * This class is used to hold list of objects of validationError
 */
public class ValidationMessages
  {
	/**
     *  list to hold ValidationError objects
     */
    private List<ValidationMessage> l = new ArrayList<ValidationMessage>();

	public ValidationMessages()
        {
          super();
	}

	/**
	 * This method adds given validation error object to the list
	 * @param ve  ValidationError object
	 */
	public void add(ValidationMessage ve)
        {
        	l.add(ve);
	}

    /**
     *  @return l holds list of validationError objects
     */
	@SuppressWarnings("unchecked")
	public List getMessages()
        {
		return l;
	}
	
	public boolean isEmpty()
	{
		return l.isEmpty();
	}
  }
