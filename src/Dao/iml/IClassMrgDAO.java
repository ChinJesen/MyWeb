/**
 * 
 */
package Dao.iml;

import java.util.List;
import java.util.Map;

import entity.ClassInfo;
import util.PageUtil;

/**
 * @author 尛晨晨
 *
 */
public interface IClassMrgDAO extends IBaseDAO<ClassInfo>{
	/**
	 * 保存班级信息
	 * 
	 * @param user
	 * @return
	 */
	public Integer saveClassInfo(ClassInfo user);

	/**
	 * 查询所有班级信息
	 * 
	 * @return
	 */
	public void queryAll(PageUtil<ClassInfo> pageUtil);

	/**
	 * 根据主键删除数据
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteClassInfoById(String id);

	/**
	 * 修改班级信息
	 * 
	 * @param ClassInfo
	 * @return
	 */
	public boolean updateClassInfo(ClassInfo ClassInfo);

	/**
	 * 获取班级信息
	 * 
	 * @param id
	 * @return
	 */
	public ClassInfo queryById(String id);

	/**
	 * 按条件查询班级信息
	 * 
	 * @param ClassInfo
	 * @return
	 */
	public List<ClassInfo> queryClassInfos(ClassInfo ClassInfo);

	/**
	 * @param tcherIds
	 * @param classId
	 */
	public void saveClassTeachers(String[] tcherIds, Integer classId);

	/**
	 * @param className
	 * @return
	 */
	public boolean queryByName(String className);

	/**
	 * @return
	 */
	public Map<Integer, String> queryClassMap();
	
	
}
