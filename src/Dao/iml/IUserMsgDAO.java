/**
 * 
 */
package Dao.iml;

import java.util.List;

import entity.UserInfo;
import util.PageUtil;

/**
 * @author 尛晨晨
 *
 */
public interface IUserMsgDAO {
	/**
	 * 保存用户信息
	 */
	public boolean saveUserInfo(UserInfo user);

	/**
	 * 查询用户信息
	 */
	public void queryAll(PageUtil<UserInfo> pageUtil);

	/**
	 * 根据主键来删除数据
	 * 
	 * @return
	 */
	public UserInfo deleteUserInfoById(Integer id);

	/**
	 * 修改用户信息
	 */
	public boolean updateUserInfo(UserInfo userInfo);

	/**
	 * 查询用户信息
	 */
	public UserInfo queryById(Integer id);

	/**
	 * 按条件来查询用户信息
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
