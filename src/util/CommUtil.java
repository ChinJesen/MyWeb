/**
 * 
 */
package util;

import java.util.List;
import java.util.Map;


import Dao.ClassMrgDAO;
import Dao.DictMrgDAO;
import Dao.RoleMrgDAO;
import Dao.iml.IClassMrgDAO;
import Dao.iml.IDictMrgDAO;
import Dao.iml.IRoleMrgDAO;
import entity.DictItem;

/**
 * @author 尛晨晨
 *
 */
public class CommUtil {
	private static CommUtil commUtil;
	private Map<String, Map<String, String>> dictMap;
	private Map<String, List<DictItem>> dictList;
	private Map<String, String> groupMap;
	private Map<String,String> roleMap;
	private Map<String,List<String>> rolePriveMap;
	private Map<Integer,String> classMap;
	private Map<String,Map<String,String>> subjectMap;
	private Map<String,Map<String,String>> kpotionTypeMap;


	private CommUtil() {
		init();
	}
	

	public CommUtil(String s){
		this();
	}
	
	public void refush() {
		init();
	}
	/**
	 * 
	 */
	private void init() {
		IDictMrgDAO dictDAO = new DictMrgDAO();
		IRoleMrgDAO roleDAO = new RoleMrgDAO();
		IClassMrgDAO clsDAO = new ClassMrgDAO();
		
		dictMap = dictDAO.getDictMap();
		groupMap = dictDAO.getGroupMap();
		dictList = dictDAO.getDictList();
		roleMap = roleDAO.getRoleMap();
		rolePriveMap = roleDAO.getRolePriveMap();
		classMap = clsDAO.queryClassMap();
		subjectMap = dictDAO.getSubJectMap();
		kpotionTypeMap = dictDAO.getKPointTypeMap();

	}

	public static CommUtil newInstance() {
		if (commUtil == null)
			commUtil = new CommUtil();
		return commUtil;
	}
	
	public void refeshClassMap()
	{
		IClassMrgDAO clsDAO = new ClassMrgDAO();
		classMap = clsDAO.queryClassMap();
	}
	// 根据工作组获取code字典组内容
	public Map<String, String> getDictMapVal(String groupCode) {
		System.out.println(dictMap.get(groupCode));
		return dictMap.get(groupCode);
	}

	public Map<String, Map<String, String>> getDictMap() {
		return dictMap;
	}

	public Map<String,String> getGroupMap() {
		return groupMap;
	}

	/**
	 * @param groupCode
	 * @return 
	 */
	public List<DictItem> getDictList(String groupCode) {
		return dictList.get(groupCode);
		
	}
	public Map<String, String> getRoleMap() {
		return roleMap;
	}

	/**
	 * @return
	 */
	public List<String> getPriveList(String roleId) {
		return rolePriveMap.get(roleId);
	}
	
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	
	private void test(String s,Integer i){
		System.out.println(s+"-----"+i);
	}
	
	public static void test01(String... s){
		for (String v : s) {
			System.out.println(v);
		}
	}

	public Map<String, String> getSubjectMap(String spId) {
		return subjectMap.get(spId);
	}
	
	public Map<String, String> getKpotionTypeMap(String subjectId)
	{
		return kpotionTypeMap.get(subjectId);
	}
}
