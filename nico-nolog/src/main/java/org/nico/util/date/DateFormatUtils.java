package org.nico.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年12月14日 下午10:37:19
 */

public class DateFormatUtils {
	
	private static final String FORMAT = "YYYY-MM-dd HH:mm:ss";
	
	private static final int MAX_MOD = 6;
	
	/**
	 * Get format of the date by mod!
	 * @param date the date you want to format
	 * @param mod the format type [1,2,3,4,5,6]
	 * @return format str
	 */
	public static String getDateFormat(Date date, int mod){
		SimpleDateFormat format = new SimpleDateFormat(getFormat(mod));
		return format.format(date);
	}
	
	/**
	 * Get format by mod
	 * @param mod the format type [1,2,3,4,5,6]
	 * @return format
	 */
	private static String getFormat(int mod){
		mod = mod < 0 ? 0 : mod;
		mod = mod >= MAX_MOD ? MAX_MOD : mod;
		mod = mod == 1 ? 4 : (5 + 3 * (mod - 1));
		mod = mod > FORMAT.length() ? FORMAT.length() : mod - 1;
		return FORMAT.substring(0, mod);
	}
}
