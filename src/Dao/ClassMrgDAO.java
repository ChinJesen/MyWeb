/**
 * 
 */
package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Dao.iml.IClassMrgDAO;
import entity.ClassInfo;
import util.Jdbcutil;
import util.PageUtil;

/**
 * @author 尛晨晨
 *
 */
public class ClassMrgDAO extends BaseDAO<ClassInfo> implements IClassMrgDAO {

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IClassMrgDAO#saveClassInfo(FirstWeb.ClassInfo)
	 */
	@Override
	public Integer saveClassInfo(ClassInfo user) {
		Integer classId = null;
		String sql = "insert into class_info" + "(className,special,createTime,endTime,remark,status)" + "value(?,?,?,?,?,?)";
		System.out.println(sql);
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			conn = Jdbcutil.getConn();
			stm = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			//stm.setString(1,user.getClassId());
			stm.setString(1,user.getClassName());
			stm.setString(2,user.getSpecial());
			stm.setString(3,user.getCreateTime());
			stm.setString(4,user.getEndTime());
			stm.setString(5,user.getRemark());
			stm.setString(6,user.getStatus());
			stm.execute();
			rs = stm.getGeneratedKeys();
			if(rs != null){
				while(rs.next()){
					classId = rs.getInt(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
		return classId;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IClassMrgDAO#queryAll(util.PageUtil)
	 */
	@Override
	public void queryAll(PageUtil<ClassInfo> pageUtil) {
		List<ClassInfo> classList = new ArrayList<ClassInfo>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
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
				ClassInfo classInfo = new ClassInfo();
				classInfo.setClassId(rs.getString("classId"));
				classInfo.setClassName(rs.getString("className"));
				classInfo.setCreateTime(rs.getString("createTime"));
				classInfo.setRemark(rs.getString("remark"));
				classInfo.setStatus(rs.getString("status"));
				classList.add(classInfo);
			}
			// 获取总条数
			Integer pageSum = getPageSum(pageUtil);
			// 设置总条数
			pageUtil.setPageNumSum(pageSum);
			pageUtil.setList(classList);
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
	private Integer getPageSum(PageUtil<ClassInfo> pageUtil) {
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
	private String getQuerySQL(PageUtil<ClassInfo> pageUtil, List<Object> paramVal) {
		String sql = "select * from class_info where 1=1";// limit ?,?
		ClassInfo classinfo = pageUtil.getEntity();
		if (classinfo != null) {
			if (classinfo.getClassId() != null && !"".equals(classinfo.getClassId())) {
				sql += " and classId like ? ";
				paramVal.add(classinfo.getClassId());
			}
			if (classinfo.getClassName() != null && !"".equals(classinfo.getClassName())) {
				sql += " and className like ? ";
				paramVal.add(classinfo.getClassName());
			}
		}

		return sql;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IClassMrgDAO#deleteClassInfoById(java.lang.String)
	 */
	@Override
	public boolean deleteClassInfoById(String id) {
		Connection conn = null;
		Statement stmt = null;
		String sql = "delete from class_info where classId ='" + id + "'";
		try {
			conn = Jdbcutil.getConn();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
		return false;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IClassMrgDAO#updateClassInfo(FirstWeb.ClassInfo)
	 */
	@Override
	public boolean updateClassInfo(ClassInfo ClassInfo) {
		// TODO 自动生成的方法存根
		return false;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IClassMrgDAO#queryById(java.lang.String)
	 */
	@Override
	public ClassInfo queryById(String id) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from class_info where classId =" + id;
		ClassInfo classInfo = null;
		try {
			conn = Jdbcutil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				classInfo = new ClassInfo();
				classInfo.setClassId(rs.getString("classId"));
				classInfo.setClassName(rs.getString("className"));
				classInfo.setCreateTime(rs.getString("createTime"));
				classInfo.setRemark(rs.getString("remark"));
				classInfo.setStatus(rs.getString("status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
		return classInfo;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see Dao.iml.IClassMrgDAO#queryClassInfos(FirstWeb.ClassInfo)
	 */
	@Override
	public List<ClassInfo> queryClassInfos(ClassInfo ClassInfo) {
		// TODO 自动生成的方法存根
		return null;
	}

	/* (non-Javadoc)
	 * @see Dao.iml.IClassMrgDAO#saveClassTeachers(java.lang.String[], java.lang.Integer)
	 */
	@Override
	public void saveClassTeachers(String[] tcherIds, Integer classId) {
		String sql = "insert into user_class_relaction (classId,userId) values (?,?)";
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = Jdbcutil.getConn();
			psmt = conn.prepareStatement(sql);
			for (String tcherId : tcherIds) {
				psmt.setInt(1,Integer.parseInt(tcherId));
				psmt.setInt(2,classId);
				psmt.addBatch();
			}
			psmt.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
	}

	/* (non-Javadoc)
	 * @see Dao.iml.IClassMrgDAO#queryByName(java.lang.String)
	 */
	@Override
	public boolean queryByName(String className) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean flag = true;
		String sql = "select count(*) from class_info where className ='" + className + "'";
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

	/* (non-Javadoc)
	 * 查询所有班级信息
	 * @see Dao.iml.IClassMrgDAO#queryClassMap()
	 */
	@Override
	public Map<Integer, String> queryClassMap() {
		String sql = "SELECT classId,className FROM class_info WHERE STATUS='0' AND endtime > NOW()";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Map<Integer, String> clsMap = new LinkedHashMap<Integer, String>();
		try 
		{
			conn = Jdbcutil.getConn();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while(rs.next())
			{
				clsMap.put(rs.getInt("classId"), rs.getString("className"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			Jdbcutil.ColseConn(conn);
		}
		return clsMap;
	}
}
