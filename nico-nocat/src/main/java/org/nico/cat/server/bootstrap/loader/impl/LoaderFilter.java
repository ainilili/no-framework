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
import org.nico.fig.center.bean.NocatBean;
import org.nico.fig.center.bean.NocatBean.FilterBean;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.reflect.ClassUtils;

/** 
 * Loader api from the configuration
 * @author nico
 * @version createTime：2018年1月8日 下午9:14:39
 */
public class LoaderFilter extends ServerLoaderConfig implements ServerLoader{

	@Override
	public void loader(NocatBean nocatCenter) {
		if(CollectionUtils.isNotBlank(nocatCenter.getFilters())){
			for(FilterBean filterBean: nocatCenter.getFilters()){
				boolean singleton = filterBean.isSingle();
				String uri = filterBean.getUri();
				String cls = filterBean.getHandler();
				String payload = filterBean.getPayload();
				if(uri == null || cls == null){
					if(cls == null)
						logging.warning("Found a filter config's handler is null, will skip loader this config.");
					if(uri == null)
						logging.warning("Found a filter api config's uri is null, will skip loader this config.");
				}else{
					Class<?> handler = null;
					if((handler = ClassUtils.forName(cls)) == null){
						throw new LoaderMoudleException("Class " + cls + " can not found in the memory ");
					}else{
						if(Filter.class.isAssignableFrom(handler)){
							FilterModule filter = new FilterModule(UriUtils.tidyUri(uri), (Class<Filter>)handler, payload, singleton);
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
