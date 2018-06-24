package org.nico.cat.server.bootstrap.loader.impl;

import java.util.List;

import org.nico.cat.server.bootstrap.loader.ServerLoader;
import org.nico.cat.server.bootstrap.loader.ServerLoaderConfig;
import org.nico.cat.server.container.Container;
import org.nico.cat.server.container.module.ApiModule;
import org.nico.cat.server.container.moudle.realize.entry.Api;
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
public class LoaderApi extends ServerLoaderConfig implements ServerLoader{

	@Override
	public void loader(SeekerSearcher searcher) {
		List<DomBean> results = searcher.searching(CAT_APIS).searching(CAT_APIS_API).getResults();
		if(CollectionUtils.isNotBlank(results)){
			DomBean[] tmpDomBeans = null;
			for(DomBean result: results){
				tmpDomBeans = new DomBean[]{result};
				boolean singleton = TypeUtils.getBoolean(result.get(CAT_FILTERS_FILTER_SINGLETON));
				DomBean cls = searcher.searching(CAT_APIS_API_HANDLER, true, tmpDomBeans).getSingleResult();
				DomBean uri = searcher.searching(CAT_APIS_API_URI, true, tmpDomBeans).getSingleResult();
				DomBean payload = searcher.searching(CAT_APIS_API_PAYLOAD, true, tmpDomBeans).getSingleResult();
				if(cls == null || uri == null){
					if(cls == null)
						logging.warning("Found an api config's handler is null, will skip loader this config.");
					if(uri == null)
						logging.warning("Found an api config's uri is null, will skip loader this config.");
				}else{
					Class<?> handler = null;
					if((handler = ClassUtils.forName(cls.getBody())) == null){
						throw new LoaderMoudleException("Class " + cls.getBody() + " can not found in the memory ");
					}else{
						if(Api.class.isAssignableFrom(handler)){
							ApiModule api = new ApiModule(payload == null ? null : payload.getBody(), UriUtils.tidyUri(uri.getBody()), (Class<Api>)handler, singleton);
							Container.getInstance().appendApiModule(api);	
						}else{
							throw new LoaderMoudleException("Class " + handler.getName() + " do not implements the {@link Api}");
						}
					}
					
				}
			}
		}
	}


}
