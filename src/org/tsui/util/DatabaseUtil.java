package org.tsui.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据库连接工具类
 * @author TsuiXh
 *
 */
public class DatabaseUtil {
	
	private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/wechat_backend?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123456";
	private static final int POOL_SIZE = 15;
	private static Connection conn = null;
	private static ComboPooledDataSource c3p0 = null;
	static {
		try {
			c3p0 = new ComboPooledDataSource();
			c3p0.setDriverClass(DRIVER_CLASS);
			c3p0.setJdbcUrl(URL);
			c3p0.setUser(USERNAME);
			c3p0.setPassword(PASSWORD);
			c3p0.setInitialPoolSize(POOL_SIZE);
			c3p0.setMaxPoolSize(200);
			c3p0.setMaxIdleTime(30);
		} catch (PropertyVetoException e) {
			// TODO Log the error message
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取数据库连接
	 * @return	数据库连接
	 * @throws SQLException
	 */
	public static Connection getConn() throws SQLException {
		if (conn == null || conn.isClosed()) {
			conn = c3p0.getConnection();
		}
		return conn;
	}
	
}
