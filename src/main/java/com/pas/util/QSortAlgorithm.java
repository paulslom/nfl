package com.pas.util;

/**
 *
 * @author www.sun.com
 * @version 	@(#)QSortAlgorithm.java	1.3, 29 Feb 1996
 */
public class QSortAlgorithm
{
	void sort(String a[])
	{
		for (int i = a.length; --i>=0; )
		{
		    boolean swapped = false;
		    for (int j = 0; j<i; j++)
		    {
				if (a[j].compareTo(a[j+1]) > 0)
				{
				    String T = a[j];
				    a[j] = a[j+1];
				    a[j+1] = T;
				    swapped = true;
				}
		    }
		    if (!swapped)
				return;
		}
	}
}