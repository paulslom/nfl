package com.pas.valueObject;

/**
 * Title: LongValueObject
 * Project: Claims Replacement System
 * 
 * Description: This is a utility value object to hold a single long value. This
 * class provides value where interfaces expect an argument of IValueObject type,
 * but only a long is needed to be passed
 * 
 */

public class LongValueObject implements IValueObject {

    private Long containedLong;

	public LongValueObject(Long containedLongParam) {
		super();
		this.containedLong = containedLongParam;
	}

	public Long getContainedLong() {
		return containedLong;
	}
}
