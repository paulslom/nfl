package com.pas.util;
public class FloatPointDeserialUtil {

    private FloatPointDeserialUtil()
    {
    }

    // Deserialize INF, -INF, and NaN, too.
    public static Float newFloat(String value)
    {
        Float res = null;
        try {
            res = new Float(value);
        } catch (NumberFormatException e) {
            if (value.equals("INF")
            ||  value.toLowerCase().equals("infinity")) {
                return new Float(Float.POSITIVE_INFINITY);
            } else if (value.equals("-INF")
            ||  value.toLowerCase().equals("-infinity")) {
                return new Float(Float.NEGATIVE_INFINITY);
            } else if (value.equals("NaN")) {
                return new Float(Float.NaN);
            }
            throw e;
        }
        return res;
    }

    public static Double newDouble(String value)
    {
        Double res = null;
        try {
            res = new Double(value);
        } catch (NumberFormatException e) {
            if (value.equals("INF")
            ||  value.toLowerCase().equals("infinity")) {
                return new Double(Double.POSITIVE_INFINITY);
            } else if (value.equals("-INF") 
            ||  value.toLowerCase().equals("-infinity")) {
                return new Double(Double.NEGATIVE_INFINITY);
            } else if (value.equals("NaN")) {
                return new Double(Double.NaN);
            }
            throw e;
        }
        return res;
    }


}

