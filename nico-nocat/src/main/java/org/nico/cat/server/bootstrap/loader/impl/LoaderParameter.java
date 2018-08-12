package org.nico.cat.server.bootstrap.loader.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nico.cat.server.bootstrap.loader.ServerLoader;
import org.nico.cat.server.bootstrap.loader.ServerLoaderConfig;
import org.nico.cat.server.container.Container;
import org.nico.fig.center.bean.NocatBean;
import org.nico.util.collection.CollectionUtils;

public class LoaderParameter extends ServerLoaderConfig implements ServerLoader{

	@Override
	public void loader(NocatBean nocatCenter) {
		Map<String, Object> properties = nocatCenter.getProperties();
		if(properties != null && ! properties.isEmpty()){
			for(Entry<String, Object> result: properties.entrySet()){
				Container.getInstance().putParameter(result.getKey(), result.getValue());
			}
		}
	}

	
}
