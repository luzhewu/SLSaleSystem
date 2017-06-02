package org.slsale.pojo.user;

import java.sql.Timestamp;

public class User {
	private Integer id; // 主键ID
	private String userCode; // 用户CODE
	private String userName; // 用户名称
	private String userPassword; // 用户登录密码
	private Timestamp creationTime; // 注册时间
	private Timestamp lastLoginTime; // 最后登录时间
	private String createdBy; // 创建者
	private Timestamp lastUpdateTime; // 最后修改时间
	private Integer isStart; // 是否启动1为启用0为不启用
	private Integer roleId; // 所属角色

	public User(Integer id, String userCode, String userName,
			String userPassword, Timestamp creationTime,
			Timestamp lastLoginTime, String createdBy,
			Timestamp lastUpdateTime, Integer isStart, Integer roleId) {
		super();
		this.id = id;
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

	public User() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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

}
