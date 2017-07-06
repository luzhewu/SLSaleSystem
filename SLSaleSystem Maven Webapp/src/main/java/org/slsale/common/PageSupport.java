package org.slsale.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 * @author lzw
 *
 */
public class PageSupport {
	private Integer totalCount = 0;// 总记录数
	private Integer pageCount;// 总页数
	private Integer pageSize = 3;// 页容量
	private Integer page = 1;// 当前页
	private Integer num = 3;// 当前页之前和之后显示的页数个数
	private List items = new ArrayList();// 当前页列表(数据列表)

	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总页数
	 * @param totalCount
	 */
	public void setTotalCount(Integer totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			if (this.totalCount % this.pageSize == 0) {
				this.pageCount = this.totalCount / this.pageSize;
			} else if (this.totalCount % this.pageSize > 0) {
				this.pageCount = this.totalCount / this.pageSize + 1;
			} else {
				this.pageCount = 0;
			}
		}
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * 获取当前页之前或之后显示的页数个数
	 * @return
	 */
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	/**
	 * 获取前一页
	 * @return
	 */
	public Integer getPrev() {
		return page - 1;
	}

	/**
	 * 获取后一页
	 * @return
	 */
	public Integer getNext() {
		return page + 1;
	}

	/**
	 * 获取最后一页
	 */
	public Integer getLast() {
		return this.pageCount;
	}

	/**
	 * 判断是否有前一页
	 * @return
	 */
	public boolean getIsPrev() {
		if (page > 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否有后一页
	 * @return
	 */
	public boolean getIsNext() {
		if (pageCount != null && page < pageCount) {
			return true;
		}
		return false;
	}

	/**
	 * 当前页的前num页
	 * @return
	 */
	public List<Integer> getPrevPages() {
		List<Integer> list = new ArrayList<>();
		Integer _frontStart = 1;
		if (page > num)
			_frontStart = page - num;
		for (int i = _frontStart; i < page; i++) {
			list.add(i);
		}
		return list;
	}

	/**
	 * 当前页的后num页
	 * @return
	 */
	public List<Integer> getNextPages() {
		List<Integer> list = new ArrayList<>();
		Integer _endCount = num;
		if (pageCount != null) {
			if (num < pageCount && (page + num) < pageCount) {
				_endCount += page;
			} else {
				_endCount = pageCount;
			}
		}

		for (int i = page + 1; i <= _endCount; i++) {
			list.add(i);
		}
		return list;
	}
}
