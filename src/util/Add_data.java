/**
 * 
 */
package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Œ­³¿³¿
 *
 */
public class Add_data {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		add();
	}

	private static void add() {
		// String sql = "insert into mywebdb1 (userno,userpw,usernm,userag,sex)
		// values(?,?,?,?,?)";
		String sql = "insert into class_info (classId,className,createTime,remark,status) values(?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			conn = Jdbcutil.getConn();
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql);
			for (int i = 0; i < 100; i++) {
				pstm.setString(1, "3°à");
				pstm.setString(2, "java3°à");
				pstm.setString(3, "2016/12/5");
				pstm.setString(4, "Ñ§Ï°java");
				pstm.setString(5, "1");
				pstm.addBatch();
			}
			pstm.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
	}

/*	private static int cn() {
		int a = 3;
		for (int i = 3; i < 50; i++) {
			a = i;
			System.out.println(a);
		}
		return a;
	}*/
	/*	
		*//**
			 * @return
			 */
	/*
	 * private static String randno() { String userNoVal = ""; for (int i = 0; i
	 * < 10; i++) { int m = (int) (Math.ceil(Math.random() * 97) + 25);
	 * userNoVal += (char)m; } System.out.println(userNoVal); return userNoVal;
	 * }
	 * 
	 *//**
		 * @return
		 */
	/*
	 * private static String randnm() { String userNoVal = ""; for (int i = 0; i
	 * < 3; i++) { int m = (int) (Math.ceil(Math.random() * 97) + 25); userNoVal
	 * += (char)m; } System.out.println(userNoVal); return userNoVal; }
	 * 
	 *//**
		 * @return
		 *//*
		 * private static String randpw() { String userNoVal = ""; for (int i =
		 * 0; i < 7; i++) { int m = (int) (Math.ceil(Math.random() * 97) + 25);
		 * userNoVal += (char)m; } System.out.println(userNoVal); return
		 * userNoVal; }
		 */
}
