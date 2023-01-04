package com.pas.util;
public class DollarValue {

	
	/**
	 * Internal double value
	 */
	private double value;
	
	/**
	 * Constructor for DollarValue
	 */
	public DollarValue(double value) {
		super();
		this.value = value;
	}
	
	/**
	 * Constructor for DollarValue
	 */
	public DollarValue(Double value) {
		super();
		this.value = value.doubleValue();
	}
	
	/**
	 * Constructor for DollarValue
	 */
	public DollarValue(String value) {
		super();
		this.value = Double.parseDouble(value);
	}

	/**
	 * Get value
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * Set value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * Format string using DecimalFormat
	 */
	public String toString() {
		
		java.text.DecimalFormat dollarFormat = new java.text.DecimalFormat("#########0.00");
		return dollarFormat.format(this.value);
	}
	
}

