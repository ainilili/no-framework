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
import org.nico.fig.center.ConfigCenter;
import org.nico.fig.center.bean.NocatBean;
import org.nico.fig.center.bean.NocatBean.ApiBean;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.reflect.ClassUtils;

/** 
 * Loader api from the configuration
 * @author nico
 * @version createTime：2018年1月8日 下午9:14:39
 */
public class LoaderApi extends ServerLoaderConfig implements ServerLoader{

	@Override
	public void loader(NocatBean nocatCenter) {
		if(CollectionUtils.isNotBlank(nocatCenter.getApis())){
			for(ApiBean apiBean: nocatCenter.getApis()){
				boolean singleton = apiBean.isSingle();
				String cls = apiBean.getHandler();
				String uri = apiBean.getUri();
				String payload = apiBean.getPayload();
				if(cls == null || uri == null){
					if(cls == null)
						logging.warning("Found an api config's handler is null, will skip loader this config.");
					if(uri == null)
						logging.warning("Found an api config's uri is null, will skip loader this config.");
				}else{
					Class<?> handler = null;
					if((handler = ClassUtils.forName(cls)) == null){
						throw new LoaderMoudleException("Class " + cls + " can not found in the memory ");
					}else{
						if(Api.class.isAssignableFrom(handler)){
							ApiModule api = new ApiModule(payload, UriUtils.tidyUri(uri), (Class<Api>)handler, singleton);
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
