package cn.javaee.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 数据库连接，采用C3P0连接池
 * @author HCY
 * 
 *
 */
public class C3P0Utils {
	// 通过标识名来创建连接池
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource(
			"mysql");

	public static ComboPooledDataSource getDataSource() {
		return dataSource;
	}

	/*创建一个ThreadLocal对象，以当前线程作为key
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();*/

	public static Connection getConnection() {
			try {
				Connection connection = dataSource.getConnection();
				return connection;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return null;
	}

	public static void close() {
		try {
			System.out.println(getConnection()+"\t"+getConnection().isClosed());
			if (getConnection() != null) {
				getConnection().close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
