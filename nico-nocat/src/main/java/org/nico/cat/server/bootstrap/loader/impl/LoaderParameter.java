package org.nico.cat.server.bootstrap.loader.impl;

import java.util.List;

import org.nico.cat.server.bootstrap.loader.ServerLoader;
import org.nico.cat.server.bootstrap.loader.ServerLoaderConfig;
import org.nico.cat.server.container.Container;
import org.nico.seeker.dom.DomBean;
import org.nico.seeker.searcher.SeekerSearcher;
import org.nico.util.collection.CollectionUtils;

public class LoaderParameter extends ServerLoaderConfig implements ServerLoader{

	@Override
	public void loader(SeekerSearcher searcher) {
		List<DomBean> results = searcher.searching(CAT_PARAMETERS).searching(CAT_PARAMETERS_PARAMETER).getResults();
		if(CollectionUtils.isNotBlank(results)){
			for(DomBean result: results){
				if(result.containsKey(CAT_PARAMETERS_PARAMETER_KEY)){
					Container.getInstance().putParameter(result.get(CAT_PARAMETERS_PARAMETER_KEY), result.getBody());
				}
			}
		}
	}

	
}
