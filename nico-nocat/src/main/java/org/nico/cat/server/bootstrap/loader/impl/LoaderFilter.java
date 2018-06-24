package org.nico.cat.server.bootstrap.loader.impl;

import java.util.List;

import org.nico.cat.server.bootstrap.loader.ServerLoader;
import org.nico.cat.server.bootstrap.loader.ServerLoaderConfig;
import org.nico.cat.server.container.Container;
import org.nico.cat.server.container.module.FilterModule;
import org.nico.cat.server.container.moudle.realize.entry.Filter;
import org.nico.cat.server.exception.runtime.LoaderMoudleException;
import org.nico.cat.server.util.TypeUtils;
import org.nico.cat.server.util.UriUtils;
import org.nico.seeker.dom.DomBean;
import org.nico.seeker.searcher.SeekerSearcher;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.reflect.ClassUtils;

/** 
 * Loader api from the configuration
 * @author nico
 * @version createTime：2018年1月8日 下午9:14:39
 */
public class LoaderFilter extends ServerLoaderConfig implements ServerLoader{

	@Override
	public void loader(SeekerSearcher searcher) {
		List<DomBean> results = searcher.searching(CAT_FILTERS).searching(CAT_FILTERS_FILTER).getResults();
		DomBean[] tmpDomBeans = null;
		if(CollectionUtils.isNotBlank(results)){
			for(DomBean result: results){
				tmpDomBeans = new DomBean[]{result};
				boolean singleton = TypeUtils.getBoolean(result.get(CAT_FILTERS_FILTER_SINGLETON));
				DomBean uri = searcher.searching(CAT_FILTERS_FILTER_URI, tmpDomBeans).getSingleResult();
				DomBean cls = searcher.searching(CAT_FILTERS_FILTER_HANDLER, tmpDomBeans).getSingleResult();
				DomBean custom = searcher.searching(CAT_FILTERS_FILTER_PAYLOAD, tmpDomBeans).getSingleResult();
				if(uri == null || cls == null){
					if(cls == null)
						logging.warning("Found a filter config's handler is null, will skip loader this config.");
					if(uri == null)
						logging.warning("Found a filter api config's uri is null, will skip loader this config.");
				}else{
					Class<?> handler = null;
					if((handler = ClassUtils.forName(cls.getBody())) == null){
						throw new LoaderMoudleException("Class " + cls.getBody() + " can not found in the memory ");
					}else{
						if(Filter.class.isAssignableFrom(handler)){
							FilterModule filter = new FilterModule(UriUtils.tidyUri(uri.getBody()), (Class<Filter>)handler, null == custom ? null : custom.getBody(), singleton);
							Container.getInstance().appendFilterModule(filter);	
						}else{
							throw new LoaderMoudleException("Class " + handler.getName() + " do not implements the {@link Filter}");
						}
					}
				}
			}
		}
	}
	
}
