package org.nico.aoc.listener;

import java.util.List;
import java.util.Map;

import org.nico.aoc.loader.BookLoader;
import org.nico.cat.server.container.module.ListenerModule;
import org.nico.cat.server.container.moudle.realize.entry.Listener;
import org.nico.noson.Noson;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月21日 上午12:25:55
 */

public class NoaocListener implements Listener{

	@Override
	public void init(Map<String, String> properties, ListenerModule listenerModule) throws Exception {
		AocConfigBean config = Noson.convert(listenerModule.getPayload(), AocConfigBean.class);
		try {
			BookLoader.loaderCompent(config.getCompents());
			BookLoader.loader(config.getPacks(), config.getXmls());
		} catch (Throwable e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * Receive configuration parameters
	 * 
	 * @author nico
	 * @time 2018-05-18 10:16
	 */
	public static class AocConfigBean{
		
		/**
		 * Scan packages
		 */
		private List<String> packs;
		
		/**
		 * Scan xmls
		 */
		private List<String> xmls;
		
		/**
		 * Loader compents
		 */
		private List<String> compents;

		public List<String> getPacks() {
			return packs;
		}

		public void setPacks(List<String> packs) {
			this.packs = packs;
		}

		public List<String> getXmls() {
			return xmls;
		}

		public void setXmls(List<String> xmls) {
			this.xmls = xmls;
		}

		public List<String> getCompents() {
			return compents;
		}

		public void setCompents(List<String> compents) {
			this.compents = compents;
		}
		
	}

}
