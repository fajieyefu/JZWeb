package com.db;

import java.sql.SQLException;

import com.db.ConnectionPool.PooledConnection;
import com.sql.Config;

public class DBManager2 {
	private static PooledConnection conn;
	private static ConnectionPool connectionPool;
	private static DBManager2 inst;

	public void close() {
		connectionPool.closeConnectionPool();
	}

	public DBManager2() {
		if (inst != null)
			return; // TODO Auto-generated constructor stub
		String connStr = String.format("jdbc:sqlserver://%s:%d;%s",
				Config.getInstance().mysqlHost, Config.getInstance().mysqlPort,
				Config.getInstance().mysqlDB);
		connectionPool = new ConnectionPool("com.microsoft.sqlserver.jdbc.SQLServerDriver", connStr,
				Config.getInstance().mysqlUser,
				Config.getInstance().mysqlPassword);
		try {
			connectionPool.createPool();
			inst = this;

		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static PooledConnection getConnection() {
		if (inst == null)
			new DBManager2();
		conn = connectionPool.getConnection();
		return conn;
	}
}
