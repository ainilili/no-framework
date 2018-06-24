package org.nico.db.session.transaction;

import org.nico.db.session.Session;

/** 
 * 
 * @author nico
 * @version createTime：2018年3月8日 下午9:33:24
 */

public class TransactionManager {

	private Session session;
	
	public TransactionManager(Session session) {
		this.session = session;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	
}
