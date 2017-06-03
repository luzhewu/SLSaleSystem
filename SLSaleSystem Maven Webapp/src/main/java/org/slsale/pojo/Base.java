package org.slsale.pojo;

/**
 * 抽取公共的属性
 * @author Administrator
 *
 */
public class Base {
	private Integer id;// 主键ID

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Base(Integer id) {
		super();
		this.id = id;
	}

	public Base() {
		super();
	}

}
