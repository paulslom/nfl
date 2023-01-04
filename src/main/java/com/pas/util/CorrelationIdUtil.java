package com.pas.util;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Insert the type's description here.
 * Creation date: (9/21/00 2:25:32 PM)
 * @author: Administrator
 */
public class CorrelationIdUtil {

	protected static Logger logger = LogManager.getLogger(CorrelationIdUtil.class);

	/**
	 * CorrelationIdUtil constructor comment.
	 */
	public CorrelationIdUtil() {
		super();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (9/21/00 2:28:10 PM)
	 * @return byte[]
	 */
	public static byte[] getCorrelationId(String id) {

		// Format UserId = 8
		//         Month = 2
		//     DayOfWeek = 2
		//           Day = 2
		//          Hour = 2
		//        Minute = 2
		//        Second = 2
		//  Milliseconds = 4

		StringBuffer correlId = new StringBuffer();

		//			correlId.append("VIP");

		// This is the real code, so we will uncomment when appropriate for multi-user.

		Calendar c = Calendar.getInstance();

		correlId.append(StringUtil.padStringEnd(id.toUpperCase(), 8, " "));
		correlId.append(
			StringUtil.padStringBegin(
				new Integer(c.get(Calendar.MONTH) + 1).toString(),
				2,
				"0"));
		correlId.append(
			StringUtil.padStringBegin(
				new Integer(c.get(Calendar.DAY_OF_WEEK)).toString(),
				2,
				"0"));
		correlId.append(
			StringUtil.padStringBegin(
				new Integer(c.get(Calendar.DAY_OF_MONTH)).toString(),
				2,
				"0"));
		correlId.append(
			StringUtil.padStringBegin(
				new Integer(c.get(Calendar.HOUR_OF_DAY)).toString(),
				2,
				"0"));
		correlId.append(
			StringUtil.padStringBegin(
				new Integer(c.get(Calendar.MINUTE)).toString(),
				2,
				"0"));
		correlId.append(
			StringUtil.padStringBegin(
				new Integer(c.get(Calendar.SECOND)).toString(),
				2,
				"0"));
		correlId.append(
			StringUtil.padStringBegin(
				new Integer(c.get(Calendar.MILLISECOND)).toString(),
				4,
				"0"));

		// */

		return correlId.toString().getBytes();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (9/27/00 10:37:13 AM)
	 * @param args java.lang.String[]
	 */
	public static void main(String[] args) {

		byte[] b = CorrelationIdUtil.getCorrelationId("bpcrue");
		logger.debug("CorrelationIdUtil.main()   Corr id: " + b.toString());

	}
}