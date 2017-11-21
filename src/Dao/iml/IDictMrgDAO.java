/**
 * 
 */
package Dao.iml;

import java.util.List;
import java.util.Map;

import entity.DictItem;

/**
 * @author Œ­³¿³¿
 *
 */
public interface IDictMrgDAO {

	/**
	 * @return
	 */
	public Map<String, Map<String, String>> getDictMap();

	public List<DictItem> queryAll();
	
	public Map<String,String> getGroupMap();

	/**
	 * @return
	 */
	public Map<String, List<DictItem>> getDictList();

	/**
	 * @param dictList
	 * @param groupCode
	 */
	public void saveDictItems(List<DictItem> dictList, String groupCode);

	/**
	 * @return
	 */
	public Map<String, Map<String, String>> getSubJectMap();

	/**
	 * @return
	 */
	public Map<String, Map<String, String>> getKPointTypeMap();
}
