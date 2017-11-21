/**
 * 
 */
package Dao;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Dao.iml.IUserMsgDAO;
import entity.UserInfo;
import util.Jdbcutil;
import util.PageUtil;

/**
 * @author 尛晨晨
 *
 */
public class UserMrgDAO implements IUserMsgDAO {

	public boolean saveUserInfo(UserInfo user) {
		boolean flag = true;
		String sql = "insert into user_info" + "(userno,usernm,userpw,userag,sex,ah,jg,photo,birthday,createTime,status,roleId,jj) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		System.out.println(sql);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = Jdbcutil.getConn();
			stmt = conn.prepareStatement(sql);
			stmt.execute(sql);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
		return flag;
	}

	public void queryAll(PageUtil<UserInfo> pageUtil) {
		List<UserInfo> userlist = new ArrayList<UserInfo>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		// 设置一个paramval集合装查询的值，传入sql中执行
		List<Object> paramVal = new ArrayList<Object>();
		// sql语句方法
		String sql = getQuerySQL(pageUtil, paramVal);
		sql += " limit ?,? ";
		try {
			conn = Jdbcutil.getConn();
			psmt = conn.prepareStatement(sql);
			// 循环查询的条件
			for (int i = 1; i <= paramVal.size(); i++) {// 传入的值为string类型的则执行此语句进行问号赋值
				if (paramVal.get(i - 1) instanceof java.lang.String) {
					psmt.setString(i, "%" + paramVal.get(i - 1) + "%");
				} else {// 否则执行这句进行问号赋值
					psmt.setObject(i, paramVal.get(i - 1));
				}
			}
			psmt.setInt(paramVal.size() + 1, (pageUtil.getPageSize() - 1) * pageUtil.getPageNum());
			psmt.setInt(paramVal.size() + 2, pageUtil.getPageNum());
			rs = psmt.executeQuery();
			while (rs.next()) {// 循环数据库中的数据
				UserInfo userInfo = new UserInfo();
				userInfo.setUserid(rs.getInt("userid"));
				userInfo.setUserno(rs.getString("userno"));
				userInfo.setUserpw(rs.getString("userpw"));
				userInfo.setUsernm(rs.getString("usernm"));
				userInfo.setUserag(rs.getString("userag"));
				userInfo.setSex(rs.getString("sex"));
				userInfo.setAh(rs.getString("ah"));
				userInfo.setJg(rs.getString("jg"));
				userlist.add(userInfo); // 得到的对象加入集合中
			}
			pageUtil.setList(userlist);
			// 获取总条数
			Integer pageSum = getPageSum(pageUtil);
			// 设置总条数
			pageUtil.setPageNumSum(pageSum);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
	}

	/**
	 * @param pageUtil
	 * @return
	 */
	private Integer getPageSum(PageUtil<UserInfo> pageUtil) {
		List<Object> paramVal = new ArrayList<Object>();
		String sql = getQuerySQL(pageUtil, paramVal);
		sql = "select count(*) from (" + sql + ") temp";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Integer pageNumSum = 0;
		try {
			// 获取数据库连接
			conn = Jdbcutil.getConn();
			// 创建statement对象
			pstm = conn.prepareStatement(sql);
			for (int i = 1; i <= paramVal.size(); i++) {
				if (paramVal.get(i - 1) instanceof java.lang.String) {
					pstm.setString(i, "%" + paramVal.get(i - 1) + "%");
				} else {
					pstm.setObject(i, paramVal.get(i - 1));
				}
			}

			// 执行SQL
			rs = pstm.executeQuery();
			// 循环处理结果集
			while (rs.next()) {
				pageNumSum = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}

		return pageNumSum;
	}

	/**
	 * @param pageUtil
	 * @param paramVal
	 * @return
	 */
	private String getQuerySQL(PageUtil<UserInfo> pageUtil, List<Object> paramVal) {
		String sql = "select * from user_info where 1=1";// limit ?,?
		UserInfo user = pageUtil.getEntity();
		if (user != null) {
			if (user.getUserno() != null && !"".equals(user.getUserno())) {
				sql += " and userno like ? ";
				paramVal.add(user.getUserno());
			}
			if (user.getUsernm() != null && !"".equals(user.getUsernm())) {
				sql += " and usernm like ? ";
				paramVal.add(user.getUsernm());
			}
			if (user.getSex() != null && !"".equals(user.getSex())) {
				sql += " and sex like ? ";
				paramVal.add(user.getSex());
			}
		}

		return sql;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IUserMsgDAO#deleteUserInfoById(java.lang.String)
	 */
	@Override
	public UserInfo deleteUserInfoById(Integer id) {
		Connection conn = null;
		Statement stmt = null;
		String sql = "delete from user_info where userid =" + id;
		UserInfo userInfo = null;
		try {
			conn = Jdbcutil.getConn();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
		return userInfo;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IUserMsgDAO#updateUserInfo(FirstWeb.UserInfo)
	 */
	@Override
	public boolean updateUserInfo(UserInfo userInfo) {
		return false;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IUserMsgDAO#queryById(java.lang.String)
	 */
	@Override
	public UserInfo queryById(Integer id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from user_info where userid =" + id;
		UserInfo userInfo = null;
		try {
			conn = Jdbcutil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				userInfo = new UserInfo();
				userInfo.setUserid(rs.getInt("userid"));
				userInfo.setUserno(rs.getString("userno"));
				userInfo.setUserpw(rs.getString("userpw"));
				userInfo.setUsernm(rs.getString("usernm"));
				userInfo.setUserag(rs.getString("userag"));
				userInfo.setSex(rs.getString("sex"));
				userInfo.setAh(rs.getString("ah"));
				userInfo.setJg(rs.getString("jg"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
		return userInfo;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IUserMsgDAO#queryUserInfos(FirstWeb.UserInfo)
	 */
	@Override
	public List<UserInfo> queryUserInfos(UserInfo userInfo) {
		return null;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IUserMsgDAO#validUserNO(java.lang.String)
	 */
	@Override
	public boolean validUserNO(String userno) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean flag = true;
		String sql = "select count(*) from user_info where userno ='" + userno + "'";
		try {
			conn = Jdbcutil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (rs.getInt(1) > 0)
					flag = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
		return flag;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IUserMsgDAO#login(java.lang.String, java.lang.String)
	 */
	@Override
	public UserInfo login(String userno, String userpw) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from user_info where userno ='" + userno + "' and userpw = '" + userpw + "'";
		UserInfo userInfo = null;
		try {
			conn = Jdbcutil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				userInfo = new UserInfo();
				userInfo.setUserno(rs.getString("userno"));
				userInfo.setUserpw(rs.getString("userpw"));
				userInfo.setRoleId(rs.getString("roleId"));
				userInfo.setUserid(rs.getInt("userid"));
				userInfo.setUsernm(rs.getString("usernm"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
		return userInfo;
	}

	/* (non-Javadoc)
	 * 用户批量入库
	 * @see Dao.iml.IUserMsgDAO#saveBatch(java.util.List)
	 */
	@Override
	public int[] saveBatch(List<UserInfo> userList) {
		String sql = "insert into user_info (userno,userpw,usernm,userag,sex,ah,jg) values(?,'123',?,?,?,?,?)";
			Connection conn = null;
			PreparedStatement pstm = null;
			int [] len = null;
			try {
			conn = Jdbcutil.getConn();
			pstm = conn.prepareStatement(sql);
			for (UserInfo userInfo : userList) {
				pstm.setString(1, userInfo.getUserno());
				pstm.setString(2, userInfo.getUsernm());
				pstm.setString(3, userInfo.getUserag());
				pstm.setString(4, userInfo.getSex());
				pstm.setString(5, userInfo.getAh());
				pstm.setString(6, userInfo.getJg());
				pstm.addBatch();
			}
			len = pstm.executeBatch();
		}catch(BatchUpdateException e){
			len = e.getUpdateCounts();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			Jdbcutil.ColseConn(conn);
		}
			return len;
	}

	/* (non-Javadoc)
	 * @see Dao.iml.IUserMsgDAO#queryTeacherInfos(java.lang.String)
	 */
	@Override
	public List<UserInfo> queryTeacherInfos(String teacherName) {
		PageUtil<UserInfo> pageUtil = new PageUtil<UserInfo>();
		UserInfo usInfo = new UserInfo();
		usInfo.setUsernm(teacherName);
		//System.out.println(teacherName);
		pageUtil.setEntity(usInfo);
		queryAll(pageUtil);
		return pageUtil.getList();
	}
	
	/*public static void testSave(UserInfo userInfo)
	{
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into user_info (userno,userpw,usernm,userag) values(?,'123',?,?)";
		try {
			conn = Jdbcutil.getConn();
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, userInfo.getUserno());
			pstm.setString(2, userInfo.getUsernm());
			pstm.setString(3, userInfo.getUserag());
			pstm.addBatch();
			
			int[] i = pstm.executeBatch();
			System.out.println(i[0]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			Jdbcutil.ColseConn(conn);
		}
	}
	
	public static void main(String[] args) {
		UserInfo user = new UserInfo();
		user.setUsernm("AAA1");
		user.setUserno("ABC1");
		user.setUserag("20");
		UserMrgDAO.testSave(user);
	}*/
}
