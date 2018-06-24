package org.nico.cat.server.bootstrap.loader.impl;

import java.util.List;

import org.nico.cat.server.bootstrap.loader.ServerLoader;
import org.nico.cat.server.bootstrap.loader.ServerLoaderConfig;
import org.nico.cat.server.container.Container;
import org.nico.seeker.dom.DomBean;
import org.nico.seeker.searcher.SeekerSearcher;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.string.StringUtils;

public class LoaderWelcome extends ServerLoaderConfig implements ServerLoader{

	@Override
	public void loader(SeekerSearcher searcher) {
		List<DomBean> results = searcher.searching(CAT_WELCOMES).searching(CAT_WELCOMES_WELCOME).getResults();
		if(CollectionUtils.isNotBlank(results)){
			for(DomBean result: results){
				if(StringUtils.isNotBlank(result.getBody())){
					Container.getInstance().appendWelcome(result.getBody());
				}
			}
		}
	}

}
