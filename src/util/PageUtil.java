/**
 * 
 */
package util;

import java.util.List;

/**
 * @author ������
 *
 */
public class PageUtil<E> {
	private Integer pageSize;
	private Integer pageNum;
	private Integer pageNumSum;// ������
	private Integer pageSizeSum;// ��ҳ��
	private E entity;
	List<E> list;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}


	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public PageUtil() {
		pageSize = 1;
		pageNum = 10;
	}

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public Integer getPageNumSum() {
		return pageNumSum;
	}

	public void setPageNumSum(Integer pageNumSum) {
		this.pageNumSum = pageNumSum;
		// ������ҳ
		if (pageNumSum % pageNum == 0) {
			pageSizeSum = pageNumSum / pageNum;
		} else {
			pageSizeSum = pageNumSum / pageNum + 1;
		}
	}

	public Integer getPageSizeSum() {
		return pageSizeSum;
	}

	public void setPageSizeSum(Integer pageSizeSum) {
		this.pageSizeSum = pageSizeSum;
	}
}
