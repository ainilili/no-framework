package org.nico.cat.server.bootstrap.loader.impl;

import java.util.List;

import org.nico.cat.server.bootstrap.loader.ServerLoader;
import org.nico.cat.server.bootstrap.loader.ServerLoaderConfig;
import org.nico.cat.server.container.Container;
import org.nico.cat.server.container.module.ListenerModule;
import org.nico.cat.server.container.moudle.realize.entry.Listener;
import org.nico.cat.server.exception.runtime.LoaderMoudleException;
import org.nico.fig.center.bean.NocatBean;
import org.nico.fig.center.bean.NocatBean.ListenerBean;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.reflect.ClassUtils;

public class LoaderListener extends ServerLoaderConfig implements ServerLoader{

	@Override
	public void loader(NocatBean nocatCenter) {
		if(CollectionUtils.isNotBlank(nocatCenter.getListeners())){
			for(ListenerBean listenerBean: nocatCenter.getListeners()){
				String payload = listenerBean.getPayload();
				String cls = listenerBean.getHandler();
				if(cls == null){
					logging.warning("Found a filter config's handler is null, will skip loader this config.");
				}else{
					Class<?> handler = null;
					if((handler = ClassUtils.forName(cls)) == null){
						throw new LoaderMoudleException("class " + cls + " can not found in the memory ");
					}else{
						if(Listener.class.isAssignableFrom(handler)){
							ListenerModule listener = new ListenerModule(payload, (Class<Listener>)handler);
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
