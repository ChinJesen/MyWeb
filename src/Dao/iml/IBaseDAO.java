/**
 * 
 */
package Dao.iml;

import util.PageUtil;

/**
 * @author ������
 *
 */
public interface IBaseDAO<E>{
	/**
	 * ͨ�ñ��淽��
	 * 
	 * @param e
	 */
	public void saveInfo(E e);

	/**
	 * ͨ��ɾ������
	 * @param e
	 */
	public void delete(Object id);
	
	
	/**
	 * ͨ���޸ķ���
	 * @param e
	 */
	public void modify(E e);

	/**
	 * ͨ�ò�ѯ����
	 * @param pageUtil
	 */
	void queryInfo(PageUtil<E> pageUtil);
	

}
