package com.pas.business;

/**
 * @author CGI
 *
 */

/**
 *  This class is used to wrap any business validation error that
 *  occur in business tier
 */
public class ValidationMessage {

    private String screenFieldName;
    private String messageKey;
    private Object[] dynamicReplacementValues;

    /**
     * constructor
     *
     * @param messageKey - error or information or warning message key that should be present 
     * in Applicationresources.props file
     */
    public ValidationMessage(String messageKey) {
        this.messageKey = messageKey;
    }
    /**
     * constructor
     *
     * @param fieldName	screen filed name
    	 * @param messageKey - error or information or warning message key that should be present 
     * in Applicationresources.props file
    */
    public ValidationMessage(String fieldName, String messageKey) {

        this.screenFieldName = fieldName;
        this.messageKey = messageKey;
    }

    /**
     * 
     * @param fieldName screen filed name
     * @param messageKey error or information or warning message key that should be present 
     * in Applicationresources.props file
     * @param dynamicReplacementValuesParam array containing Strings to replace placeholders
     * in error messages (from properties file). Place holders are identified by {0}, {1}, etc.
     */
    public ValidationMessage(String fieldName, String messageKey, Object[] dynamicReplacementValuesParam) {
        this(fieldName, messageKey);
        this.dynamicReplacementValues = dynamicReplacementValuesParam;
    }

    /**
     * @return message key
     */
    public String getKey() {
        return messageKey;
    }

    /**
     * @return screen field name
     */
    public String screenFieldName() {
        return screenFieldName;
    }

    public Object[] getDynamicReplacementValues() {
        return this.dynamicReplacementValues;
    }

}
