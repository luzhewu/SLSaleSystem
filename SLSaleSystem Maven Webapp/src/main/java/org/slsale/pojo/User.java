package org.slsale.pojo;

import java.util.Date;

public class User extends Base {
	private String userCode; // 用户CODE
	private String userName; // 用户名称
	private String userPassword; // 用户登录密码
	private Date creationTime; // 注册时间
	private Date lastLoginTime; // 最后登录时间
	private String createdBy; // 创建者
	private Date lastUpdateTime; // 最后修改时间
	private Integer isStart; // 是否启动1为启用0为不启用
	private Integer roleId; // 所属角色

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getIsStart() {
		return isStart;
	}

	public void setIsStart(Integer isStart) {
		this.isStart = isStart;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public User() {
		super();
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public User(Integer id, String userCode, String userName,
			String userPassword, Date creationTime, Date lastLoginTime,
			String createdBy, Date lastUpdateTime, Integer isStart,
			Integer roleId) {
		super(id);
		this.userCode = userCode;
		this.userName = userName;
		this.userPassword = userPassword;
		this.creationTime = creationTime;
		this.lastLoginTime = lastLoginTime;
		this.createdBy = createdBy;
		this.lastUpdateTime = lastUpdateTime;
		this.isStart = isStart;
		this.roleId = roleId;
	}

}
