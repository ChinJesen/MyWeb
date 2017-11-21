/**
 * 
 */
package Dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;

import Dao.iml.IBaseDAO;
import Dao.iml.IClassMrgDAO;
import entity.ClassInfo;
import util.ConfigUtil;
import util.Jdbcutil;
import util.PageUtil;

/**
 * @author 尛晨晨
 *
 */
public abstract class BaseDAO<E> implements IBaseDAO<E> {
	
	public static void main(String[] args) {
		IClassMrgDAO baseDAO = new ClassMrgDAO();
		
		ClassInfo clsInfo = new ClassInfo();
		//clsInfo.setClassName("测试");
		//clsInfo.setRemark("呵呵");
		clsInfo.setSpecial("JAVA");
		//clsInfo.setStatus("0");
		
		PageUtil<ClassInfo> pageUtil = new PageUtil<ClassInfo>();
		pageUtil.setEntity(clsInfo);
		
		//baseDAO.modifyInfo(clsInfo);
		baseDAO.queryInfo(pageUtil);
		for (ClassInfo tempInfo : pageUtil.getList()) {
			System.out.println(tempInfo.getClassId()+"\t"+tempInfo.getClassName());
		}
		
		System.out.println("");
	}

	private Class<?> cls;

	private static ConfigUtil configUtil;

	public BaseDAO() {
		Class<?> clsTemp = this.getClass();
		// 超类
		Type type = clsTemp.getGenericSuperclass();
		// 判断是否是泛型
		if (type instanceof ParameterizedType) {
			Type[] types = ((ParameterizedType) type).getActualTypeArguments();
			cls = (Class<?>) types[0];
		}
	}

	static {
		configUtil = ConfigUtil.newInstance("/tabORM.proprerties");
	}
	
	/**
	 * 通用查询方法
	 */
	@Override
	public void queryInfo(PageUtil<E> pageUtil){
		E e = pageUtil.getEntity();
		//获取表名
		String tableName = configUtil.getVal(e.getClass().getName());
		Map<String,Object> paramMap = getParamMap(e);
		String sql = getQuerySql(paramMap,tableName);
		sql +=" limit ?,?";
		paramMap.put("pageSize", (pageUtil.getPageSize() - 1) * pageUtil.getPageNum());
		paramMap.put("pageNum", pageUtil.getPageNum());
		excuteQuery(pageUtil,sql,paramMap);
	}

	/**
	 * 执行查询方法
	 * @param pageUtil
	 * @param sql
	 * @param paramMap
	 */
	private void excuteQuery(PageUtil<E> pageUtil, String sql, Map<String, Object> paramMap) {
		E e3 = pageUtil.getEntity();
		String tableName = configUtil.getVal(e3.getClass().getName());
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = Jdbcutil.getConn();
			pstm = conn.prepareStatement(sql);
			int i = 1;
			for (Entry<String,Object> entry : paramMap.entrySet()) {
				Object val = entry.getValue();
				if(val instanceof java.lang.String)
				{
					pstm.setString(i,val.toString());
				}
				else if(val instanceof java.lang.Integer)
				{
					pstm.setInt(i,Integer.parseInt(val.toString()));
				}
				i++;
			}
			rs = pstm.executeQuery();
			
			List<E> list = new ArrayList<E>();
			Field[] fields = pageUtil.getEntity().getClass().getDeclaredFields();
			while(rs.next())
			{
				E e = (E)pageUtil.getEntity().getClass().newInstance();
				for (Field field : fields) 
				{
					try {
						field.setAccessible(true);
						String columName = field.getName();
						String fieldType = field.getType().getSimpleName();
						if("String".equals(fieldType))
						{
							field.set(e, rs.getString(columName));
						}
						else if("Integer".equals(fieldType))
						{
							field.set(e, rs.getInt(columName));
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
				list.add(e);
			}
			pageUtil.setList(list);
			
			pageUtil.setPageNumSum(getPageNumSun(paramMap,tableName));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		finally{
			Jdbcutil.ColseConn(conn);
		}
	}

	/**
	 * 获取总条数
	 * @param paramMap
	 * @param tableName
	 * @return
	 */
	private Integer getPageNumSun(Map<String, Object> paramMap, Object tableName) {
		paramMap.remove("pageSize");
		paramMap.remove("pageNum");
		String sql = getQuerySql(paramMap, tableName);
		sql = "select count(*) from ("+sql+") tempTab";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Integer pageNumSum = 0;
		try {
			conn = Jdbcutil.getConn();
			pstm = conn.prepareStatement(sql);
			int i = 1;
			for (Entry<String,Object> entry : paramMap.entrySet()) {
				Object val = entry.getValue();
				if(val instanceof java.lang.String)
				{
					pstm.setString(i,val.toString());
				}
				else if(val instanceof java.lang.Integer)
				{
					pstm.setInt(i,Integer.parseInt(val.toString()));
				}
				i++;
			}
			rs = pstm.executeQuery();
			while(rs.next())
			{
				pageNumSum = rs.getInt(1);
			}
			} 
		catch (Exception e1)
		{
			e1.printStackTrace();	
		}
		finally
		{
			Jdbcutil.ColseConn(conn);
		}
		return pageNumSum;
	}

	/**
	 * @param paramMap
	 * @param tableName
	 * @return
	 */
	private String getQuerySql(Map<String, Object> paramMap, Object tableName) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ")
		.append(tableName)
		.append(" where 1 = 1 ");
	
		//拼装SQL
		for (Entry<String,Object>  entry: paramMap.entrySet()) {
			String columName = entry.getKey();
			sql.append(" and ").append(columName).append("=?");
		}
		
		return sql.toString();
	}

	/**
	 * @param e
	 * @return
	 */
	private Map<String, Object> getParamMap(E e) {
		//获取不为空的实体类中的查询条件
		//Map<列名，属性值>
		Map<String,Object> tempMap = new LinkedHashMap<String,Object>();
		Field[] fields = e.getClass().getDeclaredFields();
		for (Field field : fields) 
		{
			try {
				field.setAccessible(true);
				Object val = field.get(e);
				if(val != null && !"".equals(val.toString()))
				{
					tempMap.put(field.getName(), val);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}
		return tempMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.iml.IBaseDAO#saveInfo()
	 */
	@Override
	public void saveInfo(E e) {
		Class<?> cls = e.getClass();
		// 获取表名
		String tableName = configUtil.getVal(cls.getName());
		// 获取主键
		String pryKey = getPrimKey(tableName);
		// 记录数据列
		List<String> filedList = new ArrayList<String>();

		String sql = getSavesql(tableName, pryKey, filedList);
		excuteSQL(sql, e, filedList);
		System.out.println(sql);
	}

	/**
	 * @param sql
	 * @param e
	 * @param filedList
	 */
	private void excuteSQL(String sql, E entity, List<String> filedList) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = Jdbcutil.getConn();
			pstm = conn.prepareStatement(sql);
			int i = 1;
			for (String columName : filedList) {
				Object val = getFieldValue(entity, columName);
				pstm.setObject(i, val);
				i++;
			}
			pstm.execute();
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}

	}

	/**
	 * @param entity
	 * @param columName
	 * @return
	 */
	private Object getFieldValue(E entity, String columName) {
		Class<?> cls = entity.getClass();
		Object value = null;
		Field[] fileds = cls.getDeclaredFields();
		for (Field field : fileds) {
			String fieldName = field.getName();
			if (fieldName.equalsIgnoreCase(columName)) {
				String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Method method = cls.getMethod(methodName);
					value = method.invoke(entity);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return value;
	}

	/**
	 * @param tableName
	 * @param pryKey
	 * @return
	 */
	private String getSavesql(String tableName, String pryKey, List<String> filedList) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ").append(tableName).append(" (");
		List<String> columnList = getTableColumns(tableName);
		for (String columnName : columnList) {
			if (!columnName.equalsIgnoreCase(pryKey)) {
				filedList.add(columnName);
				sql.append(columnName).append(",");
			}
		}
		if (sql.toString().endsWith(","))
			sql = new StringBuffer(sql.substring(0, sql.length() - 1));
		sql.append(") values (");
		for (int i = 0; i < columnList.size(); i++) {
			sql.append("?,");
		}
		if (sql.toString().endsWith(","))
			sql = new StringBuffer(sql.substring(0, sql.length() - 1));
		sql.append(")");

		return sql.toString();
	}

	/**
	 * 获取表的列名
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	private List<String> getTableColumns(String tableName) {
		Connection conn = null;
		DatabaseMetaData metaData = null;
		ResultSet rs = null;
		List<String> columnList = new ArrayList<String>();
		conn = Jdbcutil.getConn();
		try {
			metaData = conn.getMetaData();
			rs = metaData.getColumns(conn.getCatalog(), null, tableName.toUpperCase(), null);
			while (rs.next()) {
				String clumnName = rs.getString("COLUMN_NAME");
				columnList.add(clumnName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbcutil.ColseConn(conn);
		}
		return columnList;
	}

	private String getPrimKey(String tableName) {
		Connection conn = null;
		DatabaseMetaData metaData = null;
		ResultSet rs = null;
		String primKeyName = null;
		try {
			conn = Jdbcutil.getConn();
			metaData = conn.getMetaData();
			rs = metaData.getPrimaryKeys(conn.getCatalog(), null, tableName.toUpperCase());
			while (rs.next()) {
				primKeyName = rs.getString("COLUMN_NAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return primKeyName;
	}

	@Override
	public void delete(Object id) {
		System.out.println(cls);
		// 获取表名
		String tableName = configUtil.getVal(cls.getName());
		// 获取主键
		String pryKey = getPrimKey(tableName);
		String sql = "delete from " + tableName + " where " + pryKey + "=?";
		// 记录数据列
		List<String> filedList = new ArrayList<String>();
		// 追加主键信息
		filedList.add(pryKey);
		Object o = null;
		try {
			o = cls.newInstance();
			Field[] flds = cls.getDeclaredFields();
			for (Field field : flds) {
				if (field.getName().equalsIgnoreCase(pryKey)) {
					field.setAccessible(true);
					if (id instanceof java.lang.String) {
						field.set(o, String.valueOf(id));
					} else if (id instanceof java.lang.Integer) {
						field.set(o, Integer.parseInt(String.valueOf(id)));
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 执行sql
		excuteSQL(sql, (E) o, filedList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Dao.iml.IBaseDAO#modify(java.lang.Object)
	 */
	@Override
	public void modify(E e) {
		Class<?> cls = e.getClass();
		// 获取表名
		String tableName = configUtil.getVal(cls.getName());
		// 获取主键
		String pryKey = getPrimKey(tableName);
		// 记录数据列
		List<String> filedList = new ArrayList<String>();
		// 获取sql
		String sql = getmodifysql(tableName, pryKey, filedList);
		// 追加主键信息
		filedList.add(pryKey);
		// 执行sql
		excuteSQL(sql, e, filedList);
		System.out.println(sql);

	}

	/**
	 * @param tableName
	 * @param pryKey
	 * @param filedList
	 * @return
	 */
	private String getmodifysql(String tableName, String pryKey, List<String> filedList) {
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(tableName).append(" set ");
		List<String> columnList = getTableColumns(tableName);
		for (String columnName : columnList) {
			if (!columnName.equalsIgnoreCase(pryKey)) {
				filedList.add(columnName);
				sql.append(columnName).append("=?,");
			}
		}
		if (sql.toString().endsWith(","))
			sql = new StringBuffer(sql.substring(0, sql.length() - 1));
		sql.append(" where ").append(pryKey).append("=?");
		return sql.toString();
	}

}
