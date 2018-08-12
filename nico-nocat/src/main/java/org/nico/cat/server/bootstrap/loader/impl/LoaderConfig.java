package org.nico.cat.server.bootstrap.loader.impl;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.server.bootstrap.loader.ServerLoader;
import org.nico.cat.server.bootstrap.loader.ServerLoaderConfig;
import org.nico.fig.center.bean.NocatBean;

public class LoaderConfig extends ServerLoaderConfig implements ServerLoader{

	@Override
	public void loader(NocatBean nocatCenter) {
		ConfigKey.server_resource_path = nocatCenter.getResourcePath();		
		ConfigKey.server_so_timeout = nocatCenter.getTimeout();
		ConfigKey.server_port = nocatCenter.getPort();
		ConfigKey.server_charset = nocatCenter.getCharset();
		ConfigKey.server_revice_buffer_size = nocatCenter.getReviceBufferSize();
	}
	
}
