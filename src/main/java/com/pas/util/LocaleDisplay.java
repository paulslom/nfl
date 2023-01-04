package com.pas.util;

import java.util.Currency;
import java.util.Locale;

public class LocaleDisplay
{

    public LocaleDisplay()
    {
    }

    public static void main(String args[])
    {
        Locale locale = Locale.getDefault();
        System.out.println((new StringBuilder("Locale is: ")).append(locale.getCountry()).toString());
        Currency currency = Currency.getInstance(locale);
        System.out.println((new StringBuilder("Currency symbol is: ")).append(currency.getSymbol()).toString());
    }
}
