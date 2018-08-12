package org.nico.cat.server.bootstrap.loader;

import org.nico.fig.center.ConfigCenter;
import org.nico.fig.center.bean.NocatBean;

/** 
 * Loader config
 * @author nico
 * @version createTime：2018年1月8日 下午9:13:33
 */
public interface ServerLoader {
	
	public void loader(NocatBean nocatCenter);
	
}
