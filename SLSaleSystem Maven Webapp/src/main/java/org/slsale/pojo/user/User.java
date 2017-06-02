package org.slsale.pojo.user;


public class User {
	private Integer id; // 主键ID
	private String userCode; // 用户CODE
	private String userName; // 用户名称
	private String userPassword; // 用户登录密码

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

	public User(Integer id, String userCode, String userName,
			String userPassword) {
		super();
		this.id = id;
		this.userCode = userCode;
		this.userName = userName;
		this.userPassword = userPassword;
	}

	public User() {
		super();
	}

	/*private Timestamp creationTime; // 注册时间
	private Timestamp lastLoginTime; // 最后登录时间
	private String createdBy; // 创建者
	private Timestamp lastUpdateTime; // 最后修改时间
	private Integer isStart; // 是否启动1为启用0为不启用
	private Integer roleId; // 所属角色
	*/

}
