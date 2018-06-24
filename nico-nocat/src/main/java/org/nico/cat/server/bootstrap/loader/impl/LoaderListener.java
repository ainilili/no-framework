package org.nico.cat.server.bootstrap.loader.impl;

import java.util.List;

import org.nico.cat.server.bootstrap.loader.ServerLoader;
import org.nico.cat.server.bootstrap.loader.ServerLoaderConfig;
import org.nico.cat.server.container.Container;
import org.nico.cat.server.container.module.ListenerModule;
import org.nico.cat.server.container.moudle.realize.entry.Listener;
import org.nico.cat.server.exception.runtime.LoaderMoudleException;
import org.nico.seeker.dom.DomBean;
import org.nico.seeker.searcher.SeekerSearcher;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.reflect.ClassUtils;

public class LoaderListener extends ServerLoaderConfig implements ServerLoader{

	@Override
	public void loader(SeekerSearcher searcher) {
		List<DomBean> results = searcher.searching(CAT_LISTENERS).searching(CAT_LISTENERS_LISTENER).getResults();
		DomBean[] tmpDomBeans = null;
		if(CollectionUtils.isNotBlank(results)){
			for(DomBean result: results){
				tmpDomBeans = new DomBean[]{result};
				DomBean payload = searcher.searching(CAT_LISTENERS_LISTENER_PAYLOAD, tmpDomBeans).getSingleResult();
				DomBean cls = searcher.searching(CAT_LISTENERS_LISTENER_HANDLER, tmpDomBeans).getSingleResult();
				if(cls == null){
					if(cls == null)
						logging.warning("Found a filter config's handler is null, will skip loader this config.");
				}else{
					Class<?> handler = null;
					if((handler = ClassUtils.forName(cls.getBody())) == null){
						throw new LoaderMoudleException("class " + cls.getBody() + " can not found in the memory ");
					}else{
						if(Listener.class.isAssignableFrom(handler)){
							ListenerModule listener = new ListenerModule(payload == null ? null : payload.getBody(), (Class<Listener>)handler);
							Container.getInstance().appendListenerModule(listener);
						}else{
							throw new LoaderMoudleException("Class " + handler + " do not implements the {@link Listener}");
						}
					}
				}
			}
		}

	}

}
