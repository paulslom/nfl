package com.pas.valueObject;

/**
 * Title: IntegerValueObject
 * Project: Claims Replacement System
 * 
 * Description: This is a utility value object to hold a single integer value. This
 * class provides value where interfaces expect an argument of IValueObject type,
 * but only an integer is needed to be passed
 * 
 */

public class IntegerValueObject implements IValueObject {

    private Integer containedInteger;

	public IntegerValueObject(Integer containedIntegerParam) {
		super();
		this.containedInteger = containedIntegerParam;
	}

	public Integer getContainedInteger() {
		return containedInteger;
	}
}
