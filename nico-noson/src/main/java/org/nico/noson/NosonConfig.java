package org.nico.noson;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.nico.noson.scanner.NoScanner;
import org.nico.noson.scanner.impl.SimpleScanner;

/** 
 * The config of noson working guide
 * 
 * @author nico
 * @version createTime：2018年3月24日 下午9:49:17
 */

public class NosonConfig {

	public static boolean ALLOW_EMPTY = true;
	
	public static boolean ALLOW_EMPTY_TO_NULL = true;
	
	public static int ALLOW_CYCLE_MAX_COUNT = 0;
	
	public static NoScanner DEFAULT_SCANNER = new SimpleScanner();
	
	public static Long DEFAULT_CACHE_DELAY = 100L;
	
	public static final Set<String> ALLOW_MODIFY;
	
	public static SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	
	static{
		ALLOW_MODIFY = new HashSet<String>();
		ALLOW_MODIFY.add("private");
		ALLOW_MODIFY.add("public");
		ALLOW_MODIFY.add("private static");
		ALLOW_MODIFY.add("public static");
	}
}
