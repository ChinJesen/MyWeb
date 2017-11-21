/**
 * 
 */
package Dao.iml;

import java.util.List;
import java.util.Map;

import entity.RoleInfo;

/**
 * @author Œ­³¿³¿
 *
 */
public interface IRoleMrgDAO {
	public List<RoleInfo> queryAll();
	
	public Map<String,String> getRoleMap();
	
	public Map<String,List<String>> getRolePriveMap();
}
