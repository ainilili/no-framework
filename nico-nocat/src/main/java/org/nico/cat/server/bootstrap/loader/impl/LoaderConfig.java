package org.nico.cat.server.bootstrap.loader.impl;

import java.util.List;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.server.bootstrap.loader.ServerLoader;
import org.nico.cat.server.bootstrap.loader.ServerLoaderConfig;
import org.nico.cat.server.exception.runtime.LoaderMoudleException;
import org.nico.seeker.dom.DomBean;
import org.nico.seeker.searcher.SeekerSearcher;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.string.StringUtils;

public class LoaderConfig extends ServerLoaderConfig implements ServerLoader{

	@Override
	public void loader(SeekerSearcher searcher) {
		List<DomBean> results = searcher.searching(CAT_CONFIGS).searching(CAT_CONFIGS_PROPERTY).getResults();
		if(CollectionUtils.isNotBlank(results)){
			for(DomBean result: results){
				String fieldName = result.get("field");
				if(StringUtils.isNotBlank(fieldName)){
					String value = result.get("value");
					if(value == null){
						throw new LoaderMoudleException("Config field:" + fieldName + "'s value is null");
					}
					try{
						switch(fieldName){
						case "server_resource_path":ConfigKey.server_resource_path = value;break;
						case "server_so_timeout":ConfigKey.server_so_timeout = Integer.parseInt(value);break;
						case "server_port":ConfigKey.server_port = Integer.parseInt(value);break;
						case "server_charset":ConfigKey.server_charset = value;break;
						case "server_revice_buffer_size":ConfigKey.server_revice_buffer_size = Integer.parseInt(value);break;
						}
					}catch(NumberFormatException e){
						throw new LoaderMoudleException(e.getMessage());
					}
				}
			}
		}
	}
	
}
