package com.pas.util;

import java.io.IOException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;

public class Base64Extensions
{

    static final String TRANSPOSE_START = "0123456789";
    static final String TRANSPOSE_END = "1650798342";

    public Base64Extensions()
    {
    }

    public String changeCase(String strStart)
    {
        char c[] = strStart.toCharArray();
        int nLen = strStart.length();
        for(int i = 0; i < nLen; i++)
        {
            if(Character.isUpperCase(c[i]))
            {
                c[i] = Character.toLowerCase(c[i]);
                continue;
            }
            if(Character.isLowerCase(c[i]))
            {
                c[i] = Character.toUpperCase(c[i]);
            }
        }

        String strEnd = new String(c);
        return strEnd;
    }

    public String decryptBase64(String strEncrypted)
        throws IOException, DecoderException
    {
        String str = null;
        Base64 b64 = new Base64();
        str = String.valueOf(b64.decode(str));
        String strTemp = changeCase(str);
        strTemp = transposeDigitsFrom(strTemp);
        return strTemp;
    }

    public String encryptBase64(String str)
    {
        String strTemp = changeCase(str);
        strTemp = transposeDigitsTo(strTemp);
        String strEncrypted = null;
        Base64 b64 = new Base64();
        strEncrypted = String.valueOf(b64.encode(strTemp.getBytes())).trim();
        return strEncrypted;
    }

    public String transposeDigitsFrom(String strStart)
    {
        char c[] = strStart.toCharArray();
        int nLen = strStart.length();
        for(int i = 0; i < nLen; i++)
        {
            if(Character.isDigit(c[i]))
            {
                int j = "1650798342".indexOf(c[i]);
                c[i] = "0123456789".charAt(j);
            }
        }

        String strEnd = new String(c);
        return strEnd;
    }

    public String transposeDigitsTo(String strStart)
    {
        char c[] = strStart.toCharArray();
        int nLen = strStart.length();
        for(int i = 0; i < nLen; i++)
        {
            if(Character.isDigit(c[i]))
            {
                int j = "0123456789".indexOf(c[i]);
                c[i] = "1650798342".charAt(j);
            }
        }

        String strEnd = new String(c);
        return strEnd;
    }
}
