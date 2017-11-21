/**
 * 
 */
package Dao.iml;

import java.util.List;
import java.util.Map;

import entity.ClassInfo;
import util.PageUtil;

/**
 * @author ������
 *
 */
public interface IClassMrgDAO extends IBaseDAO<ClassInfo>{
	/**
	 * ����༶��Ϣ
	 * 
	 * @param user
	 * @return
	 */
	public Integer saveClassInfo(ClassInfo user);

	/**
	 * ��ѯ���а༶��Ϣ
	 * 
	 * @return
	 */
	public void queryAll(PageUtil<ClassInfo> pageUtil);

	/**
	 * ��������ɾ������
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteClassInfoById(String id);

	/**
	 * �޸İ༶��Ϣ
	 * 
	 * @param ClassInfo
	 * @return
	 */
	public boolean updateClassInfo(ClassInfo ClassInfo);

	/**
	 * ��ȡ�༶��Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public ClassInfo queryById(String id);

	/**
	 * ��������ѯ�༶��Ϣ
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
