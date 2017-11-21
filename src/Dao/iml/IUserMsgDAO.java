/**
 * 
 */
package Dao.iml;

import java.util.List;

import entity.UserInfo;
import util.PageUtil;

/**
 * @author ������
 *
 */
public interface IUserMsgDAO {
	/**
	 * �����û���Ϣ
	 */
	public boolean saveUserInfo(UserInfo user);

	/**
	 * ��ѯ�û���Ϣ
	 */
	public void queryAll(PageUtil<UserInfo> pageUtil);

	/**
	 * ����������ɾ������
	 * 
	 * @return
	 */
	public UserInfo deleteUserInfoById(Integer id);

	/**
	 * �޸��û���Ϣ
	 */
	public boolean updateUserInfo(UserInfo userInfo);

	/**
	 * ��ѯ�û���Ϣ
	 */
	public UserInfo queryById(Integer id);

	/**
	 * ����������ѯ�û���Ϣ
	 */
	public List<UserInfo> queryUserInfos(UserInfo userInfo);

	/**
	 * @param userno
	 * @return
	 */
	public boolean validUserNO(String userno);

	/**
	 * @param userno
	 * @param userpw
	 * @return
	 */
	public UserInfo login(String userno, String userpw);

	/**
	 * @param userList
	 * @return 
	 */
	public int[] saveBatch(List<UserInfo> userList);

	/**
	 * @param teacherName
	 * @return
	 */
	public List<UserInfo> queryTeacherInfos(String teacherName);

	
}
