/**
 * 
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Œ­³¿³¿
 *
 */
public class Jdbcutil {
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	
	static{
		ConfigUtil configUtil = ConfigUtil.newInstance(null);
		driver = configUtil.getVal("driver");
		url = configUtil.getVal("url");
		user = configUtil.getVal("user");
		password = configUtil.getVal("password");
	}
	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static void ColseConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
