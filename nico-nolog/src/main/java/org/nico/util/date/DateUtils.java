package org.nico.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getDateFormat(){
		return sdf.format(new Date());
	}
	
}
