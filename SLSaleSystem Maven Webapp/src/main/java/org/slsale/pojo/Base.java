package org.slsale.pojo;

/**
 * 抽取公共的属性
 * @author Administrator
 *
 */
public class Base {
	private Integer id;// 主键ID
	private Integer starNum;// 分页的起始行
	private Integer pageSize;// 页容量

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStarNum() {
		return starNum;
	}

	public void setStarNum(Integer starNum) {
		this.starNum = starNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Base() {
		super();
	}

}
