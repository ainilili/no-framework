package com.nico.thread.test;

import java.sql.SQLException;

import org.nico.db.datasource.ConnectionPool;
import org.nico.db.datasource.SimpleDataSource;
import org.nico.db.helper.AbstractDBHelper;
import org.nico.db.helper.impl.MysqlDBHelper;
import org.nico.db.session.Session;
import org.nico.db.session.branch.MysqlSession;

import com.nico.example.entity.Customer;

public class ThreadTest {

	
	public static void main(String[] args) throws SQLException {
		
		SimpleDataSource dataSource = new SimpleDataSource("jdbc:mysql://localhost:3306/nodb?useUnicode=true&characterEncoding=utf-8",
				"root", "root", "com.mysql.jdbc.Driver");
		Session session = new MysqlSession();
		session.setDataSource(dataSource);
		AbstractDBHelper helper = new MysqlDBHelper(session);
		ConnectionPool cp = new ConnectionPool();
		cp.setAutoCommit(true);
		cp.setDataSource(dataSource);
		
		for(int i = 0; i < 10000 ; i ++){
			new SelectThread(helper).start();
		}
		
	}
	
	static class SelectThread extends Thread{

		private AbstractDBHelper helper;
		
		public SelectThread(AbstractDBHelper helper) {
			super();
			this.helper = helper;
		}

		@Override
		public void run() {
			helper.selectSingle(null, Customer.class);
		}
		
		
	}
}
