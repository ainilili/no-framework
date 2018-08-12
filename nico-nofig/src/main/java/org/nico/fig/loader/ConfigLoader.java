package org.nico.fig.loader;

import java.io.InputStream;

import org.nico.fig.center.ConfigCenter;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.util.resource.ResourceUtils;
import org.nico.util.string.StringUtils;
import org.yaml.snakeyaml.Yaml;

public class ConfigLoader {

	public Logging logging = LoggingHelper.getLogging(ConfigLoader.class);
	
	public final static String CONFIG_NAME = "hi";
	
	public final static String CONFIG_SUFFIX = ".yml";
	
	public static void loader(String contains) throws Exception{
		String resourceName = StringUtils.isBlank(contains) ? (CONFIG_NAME + CONFIG_SUFFIX) : (CONFIG_NAME + "-" + contains + CONFIG_SUFFIX);	
		InputStream ymlStream = ResourceUtils.getClasspathResource(resourceName);;
		if(ymlStream == null) {
			throw new NullPointerException(resourceName + " not found !");
		}
		Yaml yaml = new Yaml();
		ConfigCenter center = yaml.loadAs(ymlStream, ConfigCenter.class);
		ConfigCenter.copy(center);
	}
}
