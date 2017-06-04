package org.slsale.pojo;

import java.util.Date;

public class Role extends Base {

	private String roleCode; // 角色编码
	private String roleName; // 角色名称
	private Date createDate; // 创建日期
	private Integer isStart; // 是否启用（0、未启用1、启用）
	private Date createdBy; // 创建者

	public Role(Integer id, String roleCode, String roleName, Date createDate,
			Integer isStart, Date createdBy) {
		super(id);
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.createDate = createDate;
		this.isStart = isStart;
		this.createdBy = createdBy;
	}

	public Role() {
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getIsStart() {
		return isStart;
	}

	public void setIsStart(Integer isStart) {
		this.isStart = isStart;
	}

	public Date getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Date createdBy) {
		this.createdBy = createdBy;
	}

}
