/**
 * 
 */
package Dao.iml;

import util.PageUtil;

/**
 * @author 尛晨晨
 *
 */
public interface IBaseDAO<E>{
	/**
	 * 通用保存方法
	 * 
	 * @param e
	 */
	public void saveInfo(E e);

	/**
	 * 通用删除方法
	 * @param e
	 */
	public void delete(Object id);
	
	
	/**
	 * 通用修改方法
	 * @param e
	 */
	public void modify(E e);

	/**
	 * 通用查询方法
	 * @param pageUtil
	 */
	void queryInfo(PageUtil<E> pageUtil);
	

}
