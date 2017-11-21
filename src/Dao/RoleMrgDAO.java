/**
 * 
 */
package Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Dao.iml.IRoleMrgDAO;
import entity.RoleInfo;
import util.Jdbcutil;

/**
 * @author Œ­³¿³¿
 *
 */
public class RoleMrgDAO implements IRoleMrgDAO{
	/* (non-Javadoc)
	 * @see Dao.iml.IRoleMrgDAO#queryAll()
	 */
	@Override
	public List<RoleInfo> queryAll() {
		Connection conn = null;
		Statement stm = null;
		ResultSet rs= null;
		List<RoleInfo> list = new ArrayList<RoleInfo>();
		try {
			conn = Jdbcutil.getConn();
			stm = conn.createStatement();
			rs = stm.executeQuery("select * from role_info where status ='0' and roleID <> 'admin' ");
			while(rs.next()){
				RoleInfo role = new RoleInfo();
				role.setRoleID(rs.getString("roleID"));
				role.setRoleName(rs.getString("roleName"));
				role.setRemark(rs.getString("remark"));
				role.setStatus(rs.getString("status"));
				list.add(role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see Dao.iml.IRoleMrgDAO#getRoleMap()
	 */
	@Override
	public Map<String, String> getRoleMap() {
		List<RoleInfo> roleList = queryAll();
		Map<String,String> map = new HashMap<String,String>();
		for (RoleInfo roleInfo : roleList) {
			map.put(roleInfo.getRoleID(), roleInfo.getRoleName());
		}
		return map;
	}
	/* (non-Javadoc)
	 * @see Dao.iml.IRoleMrgDAO#getRolePriveMap()
	 */
	@Override
	
		
		public Map<String, List<String>> getRolePriveMap() 
		{
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;
			String sql =" SELECT rp.roleId,rp.priveId FROM role_info r,role_prive_relaction rp WHERE r.roleId = rp.roleId AND r.status='0'";
			Map<String, List<String>>  rolePriveMap = new HashMap<String, List<String>>();
			try {
				conn = Jdbcutil.getConn();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				while(rs.next())
				{
					String roleId = rs.getString("roleId");
					String priveId = rs.getString("priveId");
					List<String> list = null;
					if(rolePriveMap.containsKey(roleId))
					{
						list = rolePriveMap.get(roleId);
						list.add(priveId);
					}
					else
					{
						list = new ArrayList<String>();
						list.add(priveId);
						rolePriveMap.put(roleId, list);
					}
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally
			{
				Jdbcutil.ColseConn(conn);
			}
			return rolePriveMap;
	}
}
