package org.nico.cat.server.bootstrap.loader.impl;

import java.util.List;

import org.nico.cat.server.bootstrap.loader.ServerLoader;
import org.nico.cat.server.bootstrap.loader.ServerLoaderConfig;
import org.nico.cat.server.container.Container;
import org.nico.fig.center.bean.NocatBean;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.string.StringUtils;

public class LoaderWelcome extends ServerLoaderConfig implements ServerLoader{

	@Override
	public void loader(NocatBean nocatCenter) {
		if(CollectionUtils.isNotBlank(nocatCenter.getWelcomes())){
			for(String welcome: nocatCenter.getWelcomes()){
				if(StringUtils.isNotBlank(welcome)){
					Container.getInstance().appendWelcome(welcome);
				}
			}
		}
	}

}
