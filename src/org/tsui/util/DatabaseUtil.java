package org.tsui.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据库连接工具类
 * @author TsuiXh
 */
public class DatabaseUtil {
	
	// MySQL database configuration
	private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/wechat_backend?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123456";
	
	//C3P0 database connection pool configuration
	private static final int INIT_POOL_SIZE = 3;
	private static final int MAX_POOL_SIZE = 20;
	private static final int MIN_POOL_SIZE = 0;
	private static final int MAX_IDLE_TIME = 60;
	private static final int ACQUIRE_INCREMENT = 3;
	private static final int ACQUIRE_RETRY_ATTEMPS = 15;
	private static final int CHECKOUT_TIMEOUT = 100;
	private static final int IDLE_CONNECTION_TEST_PERIOD = 60;
	private static Connection conn = null;
	private static ComboPooledDataSource c3p0 = null;
	static {
		try {
			//Initialize(configuration) c3p0 database connection pool
			c3p0 = new ComboPooledDataSource();
			c3p0.setDriverClass(DRIVER_CLASS);
			c3p0.setJdbcUrl(URL);
			c3p0.setUser(USERNAME);
			c3p0.setPassword(PASSWORD);
			//连接关闭时默认将所有未提交的操作回滚
			c3p0.setAutoCommitOnClose(false);
			//初始化时获取三个连接，取值在min和max之间
			c3p0.setInitialPoolSize(INIT_POOL_SIZE);
			//连接池中保留的最大连接数
			c3p0.setMaxPoolSize(MAX_POOL_SIZE);
			//连接池中保留的最小连接数
			c3p0.setMinPoolSize(MIN_POOL_SIZE);
			//最大空闲时间，60秒内未使用则连接被丢弃
			c3p0.setMaxIdleTime(MAX_IDLE_TIME);
			//当连接池中的连接耗尽的时候c3p0一次同时获取的连接数
			c3p0.setAcquireIncrement(ACQUIRE_INCREMENT);
			//定义从数据库获取新连接失败后重复尝试的次数
			c3p0.setAcquireRetryAttempts(ACQUIRE_RETRY_ATTEMPS);
			//当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出SQL Exception
			c3p0.setCheckoutTimeout(CHECKOUT_TIMEOUT);
			//每个TEST_PERIOD检查所有连接池中的空闲连接
			c3p0.setIdleConnectionTestPeriod(IDLE_CONNECTION_TEST_PERIOD);
		} catch (PropertyVetoException e) {
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
