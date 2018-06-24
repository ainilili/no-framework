package org.nico.cat.config;

import org.nico.util.jar.JarToolUtils;

/** 
 * The configuration of server
 * @author nico
 * @version createTime：2018年1月4日 下午9:48:18
 */
public class ConfigKey {
	
	/** Fixed attribute **/
	public static final String CLASSPATH = ConfigKey.class.getResource("/").getPath();
	
	public static final String CONFIGURATION = "cat.xml";
	
	public static final String RESOURCE_URI_ROOT = JarToolUtils.getJarDir();

	public static final String RESOURCE_URI_CONFIG = RESOURCE_URI_ROOT + "/" + CONFIGURATION;
	
	public static final String SESSIONID = "DONTFORGETME";

	/** Float attribute **/
	public static Integer server_port; 

	public static Integer server_revice_buffer_size;
	
	public static Integer server_so_timeout;
	
	public static String server_charset;
	
	public static String server_resource_path = CLASSPATH;
	
}
