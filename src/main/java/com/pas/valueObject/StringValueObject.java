package com.pas.valueObject;

/**
 * Title: StringValueObject
 * Project: Claims Replacement System
 * 
 * Description: This is a utility value object to hold a single String. This
 * class provides value where interfaces expect an argument of IValueObject type,
 * but only a String is needed to be passed
 * 
 * Copyright: Copyright (c) 2003
 * Company: Lincoln Life
 * @author psinghal
 * @version 
 */
public class StringValueObject implements IValueObject {

    private String containedString;

    public StringValueObject(String containedStringParam) {
        super();
        this.containedString = containedStringParam;
    }

    public String getContainedString() {
        return containedString;
    }

}
